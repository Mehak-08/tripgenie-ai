package com.astratravel.controller;

import com.astratravel.model.ModelConfig;
import com.astratravel.model.TripDossier;
import com.astratravel.model.TripRequest;
import com.astratravel.model.TweakRequest;
import com.astratravel.service.AstraLLMService;
import com.astratravel.service.MultiAgentOrchestrationService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class TravelController {

    private final MultiAgentOrchestrationService orchestrationService;
    private final AstraLLMService astraLLMService;

    public TravelController(MultiAgentOrchestrationService orchestrationService, AstraLLMService astraLLMService) {
        this.orchestrationService = orchestrationService;
        this.astraLLMService = astraLLMService;
    }

    /**
     * Start a new multi-agent planning session.
     */
    @PostMapping("/trip/plan")
    public ResponseEntity<Map<String, String>> startPlan(@RequestBody TripRequest request) {
        String sessionId = "trip-" + UUID.randomUUID().toString().substring(0, 8);
        orchestrationService.startPlanningSession(sessionId, request);
        return ResponseEntity.ok(Map.of(
                "sessionId", sessionId,
                "status", "SESSION_INITIALIZED",
                "message", "Multi-agent swarm has been dispatched"
        ));
    }

    /**
     * Server-Sent Events (SSE) live stream of agent dialogue and final dossier.
     */
    @GetMapping(value = "/trip/stream/{sessionId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamSession(@PathVariable String sessionId) {
        return orchestrationService.registerEmitter(sessionId);
    }

    /**
     * Fetch cached trip dossier by session id.
     */
    @GetMapping("/trip/dossier/{sessionId}")
    public ResponseEntity<?> getDossier(@PathVariable String sessionId) {
        TripDossier dossier = orchestrationService.getCachedDossier(sessionId);
        if (dossier != null) {
            return ResponseEntity.ok(dossier);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Conversational adjustment with Atlas Orchestrator.
     */
    @PostMapping("/trip/tweak")
    public ResponseEntity<TripDossier> tweakDossier(@RequestBody TweakRequest tweakRequest) {
        TripDossier updated = astraLLMService.applyUserTweak(tweakRequest);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * Get active AI model configuration.
     */
    @GetMapping("/config")
    public ResponseEntity<ModelConfig> getConfig() {
        ModelConfig current = astraLLMService.getConfig();
        ModelConfig safeCopy = new ModelConfig();
        safeCopy.setActiveModel(current.getActiveModel());
        safeCopy.setCustomEndpoint(current.getCustomEndpoint());
        safeCopy.setSimulatedEngineFallback(current.isSimulatedEngineFallback());
        safeCopy.setTemperature(current.getTemperature());
        safeCopy.setMaxTokens(current.getMaxTokens());
        // Mask API key if set
        if (current.getApiKey() != null && !current.getApiKey().isBlank()) {
            safeCopy.setApiKey("••••••••" + current.getApiKey().substring(Math.max(0, current.getApiKey().length() - 4)));
        } else {
            safeCopy.setApiKey("");
        }
        return ResponseEntity.ok(safeCopy);
    }

    /**
     * Update AI model configuration.
     */
    @PostMapping("/config")
    public ResponseEntity<Map<String, String>> updateConfig(@RequestBody ModelConfig newConfig) {
        astraLLMService.updateConfig(newConfig);
        return ResponseEntity.ok(Map.of("status", "CONFIG_UPDATED", "model", astraLLMService.getConfig().getActiveModel()));
    }
}
