package com.astratravel.service;

import com.astratravel.model.ModelConfig;
import com.astratravel.model.TripDossier;
import com.astratravel.model.TweakRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
public class AstraLLMService {
    private static final Logger log = LoggerFactory.getLogger(AstraLLMService.class);

    private final ModelConfig config = new ModelConfig();
    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AstraLLMService() {
        String envKey = System.getenv("OPENAI_API_KEY");
        if (envKey == null || envKey.isBlank()) {
            envKey = System.getenv("ASTRA_API_KEY");
        }
        if (envKey != null && !envKey.isBlank()) {
            config.setApiKey(envKey);
            config.setSimulatedEngineFallback(false);
            log.info("Astra LLM Service loaded API key from environment variable.");
        }
    }

    public ModelConfig getConfig() {
        return config;
    }

    public void updateConfig(ModelConfig newConfig) {
        if (newConfig.getActiveModel() != null && !newConfig.getActiveModel().isBlank()) {
            this.config.setActiveModel(newConfig.getActiveModel());
        }
        if (newConfig.getApiKey() != null) {
            this.config.setApiKey(newConfig.getApiKey());
        }
        if (newConfig.getCustomEndpoint() != null && !newConfig.getCustomEndpoint().isBlank()) {
            this.config.setCustomEndpoint(newConfig.getCustomEndpoint());
        }
        this.config.setSimulatedEngineFallback(newConfig.isSimulatedEngineFallback());
        log.info("Updated Astra LLM Config: Model={}, FallbackEnabled={}", config.getActiveModel(), config.isSimulatedEngineFallback());
    }

    /**
     * Executes conversational tweaks requested by user on an existing dossier.
     */
    public TripDossier applyUserTweak(TweakRequest tweak) {
        TripDossier dossier = tweak.getCurrentDossier();
        if (dossier == null) return null;

        String userMsg = tweak.getUserMessage() != null ? tweak.getUserMessage().toLowerCase() : "";

        // If user wants food/culinary focus
        if (userMsg.contains("food") || userMsg.contains("culinary") || userMsg.contains("eat") || userMsg.contains("restaurant") || userMsg.contains("michelin")) {
            dossier.setSummary(dossier.getSummary() + " [Updated: Prioritizing local gastronomic discovery & street food trails]");
            if (dossier.getItinerary() != null && !dossier.getItinerary().isEmpty()) {
                dossier.getItinerary().get(0).setTheme("Gastronomic Immersion & Iconic Street Bites");
            }
        }
        // If user wants relaxation or slower pace
        else if (userMsg.contains("relax") || userMsg.contains("slow") || userMsg.contains("chill") || userMsg.contains("spa")) {
            dossier.setSummary(dossier.getSummary() + " [Updated: Paced with dedicated wellness, rooftop relaxation & scenic pauses]");
            dossier.setTravelStyle("Mindful Wellness & Scenic Relaxation");
        }
        // If user wants adventure or active
        else if (userMsg.contains("adventure") || userMsg.contains("hike") || userMsg.contains("nature")) {
            dossier.setSummary(dossier.getSummary() + " [Updated: Infused with outdoor excursions, panoramic viewpoints & active exploration]");
            dossier.setTravelStyle("High-Vibe Outdoor Adventure");
        }
        // If user mentions boutique hotel upgrade
        else if (userMsg.contains("hotel") || userMsg.contains("stay") || userMsg.contains("room")) {
            if (dossier.getStay() != null) {
                dossier.getStay().setRoomType("Upgraded Executive Suite & Skyline Balcony");
                dossier.getStay().setVibe("Upgraded premium enclave with personalized concierge and skyline views");
            }
        } else {
            dossier.setSummary(dossier.getSummary() + " [Refined with Atlas Orchestrator: " + tweak.getUserMessage() + "]");
        }

        return dossier;
    }

    /**
     * Optional live API call if user configures a real key for gpt-6-astra / OpenAI endpoint
     */
    public String generateWithAstraLLM(String systemPrompt, String userPrompt) {
        if (config.getApiKey() == null || config.getApiKey().isBlank() || config.isSimulatedEngineFallback()) {
            return null; // Signals engine to use native Java autonomous agent synthesis
        }

        try {
            Map<String, Object> body = new HashMap<>();
            body.put("model", config.getActiveModel());
            body.put("temperature", config.getTemperature());
            body.put("max_tokens", config.getMaxTokens());

            body.put("messages", new Object[]{
                    Map.of("role", "system", "content", systemPrompt),
                    Map.of("role", "user", "content", userPrompt)
            });

            String jsonPayload = objectMapper.writeValueAsString(body);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(config.getCustomEndpoint() + "/chat/completions"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + config.getApiKey())
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .timeout(Duration.ofSeconds(20))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                JsonNode root = objectMapper.readTree(response.body());
                return root.path("choices").path(0).path("message").path("content").asText();
            } else {
                log.warn("Astra LLM API returned status {}: {}. Falling back to native autonomous engine.", response.statusCode(), response.body());
            }
        } catch (Exception e) {
            log.warn("Error calling Astra LLM: {}. Falling back to native autonomous engine.", e.getMessage());
        }

        return null;
    }
}
