package com.astratravel.service;

import com.astratravel.agent.*;
import com.astratravel.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

@Service
public class MultiAgentOrchestrationService {
    private static final Logger log = LoggerFactory.getLogger(MultiAgentOrchestrationService.class);

    private final AtlasOrchestratorAgent orchestratorAgent;
    private final FlightAgent flightAgent;
    private final StayAgent stayAgent;
    private final ActivityAgent activityAgent;
    private final BudgetAuditorAgent auditorAgent;
    private final AstraLLMService astraLLMService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ConcurrentHashMap<String, SseEmitter> activeEmitters = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, TripDossier> cachedDossiers = new ConcurrentHashMap<>();
    private final ExecutorService agentThreadPool = Executors.newCachedThreadPool();

    public MultiAgentOrchestrationService(
            AtlasOrchestratorAgent orchestratorAgent,
            FlightAgent flightAgent,
            StayAgent stayAgent,
            ActivityAgent activityAgent,
            BudgetAuditorAgent auditorAgent,
            AstraLLMService astraLLMService
    ) {
        this.orchestratorAgent = orchestratorAgent;
        this.flightAgent = flightAgent;
        this.stayAgent = stayAgent;
        this.activityAgent = activityAgent;
        this.auditorAgent = auditorAgent;
        this.astraLLMService = astraLLMService;
    }

    public SseEmitter registerEmitter(String sessionId) {
        // 5 minute timeout for SSE stream
        SseEmitter emitter = new SseEmitter(300_000L);
        activeEmitters.put(sessionId, emitter);

        emitter.onCompletion(() -> activeEmitters.remove(sessionId));
        emitter.onTimeout(() -> activeEmitters.remove(sessionId));
        emitter.onError(e -> activeEmitters.remove(sessionId));

        return emitter;
    }

    public TripDossier getCachedDossier(String sessionId) {
        return cachedDossiers.get(sessionId);
    }

    public void startPlanningSession(String sessionId, TripRequest request) {
        agentThreadPool.submit(() -> {
            List<AgentMessage> dialogueHistory = new ArrayList<>();
            try {
                // Short initial delay so SSE client has connected
                sleep(600);

                // --- ROUND 1: ORCHESTRATOR MISSION KICKOFF ---
                sendStatus(sessionId, 10, "MISSION_INIT", "Atlas Orchestrator is formulating multi-agent mission protocol...");
                AgentMessage kickoffMsg = orchestratorAgent.createKickoffMessage(request);
                String llmKickoff = astraLLMService.generateWithAstraLLM(
                        "You are Atlas, the Apex Multi-Agent Travel Orchestrator powered by GPT-6 Astra. Speak with strategic confidence and authority in 1-2 punchy sentences.",
                        "Formulate a mission kickoff for a " + request.getTripDays() + "-day trip to " + request.getDestination() + " with a budget of " + request.getBudget() + " " + request.getCurrency() + "."
                );
                if (llmKickoff != null && !llmKickoff.isBlank()) {
                    kickoffMsg.setContent("🌟 [GPT-6 Astra Live] " + llmKickoff);
                }
                dialogueHistory.add(kickoffMsg);
                sendAgentMessage(sessionId, kickoffMsg);
                sleep(900);

                // --- ROUND 2: FLIGHT AGENT (AEROSTREAM) ---
                sendStatus(sessionId, 28, "FLIGHT_SOURCING", "AeroStream is scanning global flight corridors and fare classes...");
                double flightBudgetEnvelope = request.getBudget() * 0.38;
                FlightAgent.FlightProposal flightProposal = flightAgent.evaluateFlights(request, flightBudgetEnvelope);
                AgentMessage flightMsg = flightAgent.createProposalMessage(flightProposal, request);
                String llmFlight = astraLLMService.generateWithAstraLLM(
                        "You are AeroStream, an elite AI flight routing specialist agent. Speak like a flight operations officer in 1-2 concise sentences.",
                        "Report flight options to " + request.getDestination() + ". Selected: " + flightProposal.outbound.getAirline() + " for $" + flightProposal.totalCost + "."
                );
                if (llmFlight != null && !llmFlight.isBlank()) {
                    flightMsg.setContent("✈️ [GPT-6 Astra Live] " + llmFlight);
                }
                dialogueHistory.add(flightMsg);
                sendAgentMessage(sessionId, flightMsg);
                sleep(1000);

                // --- ROUND 3: STAY AGENT (SANCTUARY) ---
                sendStatus(sessionId, 48, "STAY_MATCHING", "Sanctuary is evaluating boutique enclaves, districts, and transit hubs...");
                double remainingForStay = Math.max(request.getBudget() * 0.35, request.getBudget() - flightProposal.totalCost - (request.getBudget() * 0.25));
                int totalNights = (int) request.getTripDays();
                StayOption stayOption = stayAgent.evaluateStay(request, remainingForStay, totalNights);
                AgentMessage stayMsg = stayAgent.createProposalMessage(stayOption, request);
                String llmStay = astraLLMService.generateWithAstraLLM(
                        "You are Sanctuary, a discerning boutique hotel and district curator AI agent. Speak in 1-2 sophisticated sentences.",
                        "Report accommodation choice in " + request.getDestination() + ": " + stayOption.getHotelName() + " (" + stayOption.getDistrict() + ") at $" + stayOption.getNightlyRate() + "/night."
                );
                if (llmStay != null && !llmStay.isBlank()) {
                    stayMsg.setContent("🏨 [GPT-6 Astra Live] " + llmStay);
                }
                dialogueHistory.add(stayMsg);
                sendAgentMessage(sessionId, stayMsg);
                sleep(1000);

                // --- ROUND 4: INTER-AGENT BUDGET RECONCILIATION ---
                double spentSoFar = flightProposal.totalCost + stayOption.getTotalCost();
                double remainingForActivities = Math.max(request.getBudget() - spentSoFar - (request.getBudget() * 0.10), 150.0);

                if (spentSoFar > request.getBudget() * 0.75) {
                    // Trigger inter-agent negotiation
                    sendStatus(sessionId, 60, "INTER_AGENT_NEGOTIATION", "Sanctuary & AeroStream negotiating room tier to protect experiences...");
                    AgentMessage negMsg = stayAgent.createNegotiationMessage(75.0, "Optimized room tier to Executive Balcony");
                    dialogueHistory.add(negMsg);
                    sendAgentMessage(sessionId, negMsg);
                    sleep(800);
                }

                // --- ROUND 5: ACTIVITY AGENT (CURATOR) ---
                sendStatus(sessionId, 75, "ACTIVITY_CURATION", "Curator is crafting timed daily experiences, culinary stops & hidden gems...");
                List<DayPlan> itinerary = activityAgent.curateItinerary(request, remainingForActivities, (int) request.getTripDays());
                AgentMessage activityMsg = activityAgent.createProposalMessage(itinerary, request);
                String llmActivity = astraLLMService.generateWithAstraLLM(
                        "You are Curator, an experiential cultural curator and gastronomy scout AI agent. Speak in 1-2 vibrant sentences.",
                        "Report daily curated itinerary highlights for " + request.getDestination() + " for " + itinerary.size() + " days."
                );
                if (llmActivity != null && !llmActivity.isBlank()) {
                    activityMsg.setContent("🎭 [GPT-6 Astra Live] " + llmActivity);
                }
                dialogueHistory.add(activityMsg);
                sendAgentMessage(sessionId, activityMsg);
                sleep(1000);

                // --- ROUND 6: FISCAL AUDIT (AUDITOR) ---
                sendStatus(sessionId, 88, "BUDGET_AUDIT", "Auditor is auditing total expenditures, reserves, and contingency buffers...");
                double totalActivityCost = itinerary.stream().mapToDouble(DayPlan::getDailyEstimatedSpend).sum();
                BudgetBreakdown budgetBreakdown = auditorAgent.auditAndBalance(request, flightProposal.totalCost, stayOption.getTotalCost(), totalActivityCost);
                AgentMessage auditMsg = auditorAgent.createAuditMessage(budgetBreakdown, request);
                String llmAudit = astraLLMService.generateWithAstraLLM(
                        "You are Auditor, a rigorous AI budget compliance officer. Speak with crisp fiscal precision in 1-2 sentences.",
                        "Audit result: Total spend $" + budgetBreakdown.getTotalCost() + " against budget $" + budgetBreakdown.getTotalBudget() + " with $" + budgetBreakdown.getEmergencyBuffer() + " reserve."
                );
                if (llmAudit != null && !llmAudit.isBlank()) {
                    auditMsg.setContent("⚖️ [GPT-6 Astra Live] " + llmAudit);
                }
                dialogueHistory.add(auditMsg);
                sendAgentMessage(sessionId, auditMsg);
                sleep(900);

                // --- ROUND 7: FINAL DOSSIER SYNTHESIS (ATLAS) ---
                sendStatus(sessionId, 96, "DOSSIER_SYNTHESIS", "Atlas is assembling the verified master dossier, boarding passes & travel toolkit...");
                TripDossier finalDossier = orchestratorAgent.assembleDossier(
                        request, flightProposal, stayOption, itinerary, budgetBreakdown, dialogueHistory
                );
                finalDossier.setId(sessionId);
                cachedDossiers.put(sessionId, finalDossier);

                AgentMessage finalMsg = orchestratorAgent.createSynthesisMessage(finalDossier);
                String llmFinal = astraLLMService.generateWithAstraLLM(
                        "You are Atlas, Apex Orchestrator. Issue a triumphant, elegant closing handover in 1-2 inspiring sentences.",
                        "Trip dossier for " + request.getDestination() + " finalized and signed."
                );
                if (llmFinal != null && !llmFinal.isBlank()) {
                    finalMsg.setContent("🌟 [GPT-6 Astra Live] " + llmFinal);
                }
                dialogueHistory.add(finalMsg);
                sendAgentMessage(sessionId, finalMsg);
                sleep(700);

                // Complete event with complete dossier JSON
                sendComplete(sessionId, finalDossier);
                log.info("Successfully planned and synthesized trip dossier for destination: {}", request.getDestination());

            } catch (Exception e) {
                log.error("Error during multi-agent planning session {}: {}", sessionId, e.getMessage(), e);
                sendError(sessionId, "Multi-agent planning encountered an issue: " + e.getMessage());
            }
        });
    }

    private void sendStatus(String sessionId, int progress, String phase, String message) {
        SseEmitter emitter = activeEmitters.get(sessionId);
        if (emitter != null) {
            try {
                Map<String, Object> data = Map.of(
                        "type", "status",
                        "progress", progress,
                        "phase", phase,
                        "message", message
                );
                emitter.send(SseEmitter.event().name("status").data(objectMapper.writeValueAsString(data)));
            } catch (IOException e) {
                activeEmitters.remove(sessionId);
            }
        }
    }

    private void sendAgentMessage(String sessionId, AgentMessage msg) {
        SseEmitter emitter = activeEmitters.get(sessionId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().name("agent_message").data(objectMapper.writeValueAsString(msg)));
            } catch (IOException e) {
                activeEmitters.remove(sessionId);
            }
        }
    }

    private void sendComplete(String sessionId, TripDossier dossier) {
        SseEmitter emitter = activeEmitters.get(sessionId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().name("dossier_complete").data(objectMapper.writeValueAsString(dossier)));
                emitter.complete();
            } catch (IOException e) {
                activeEmitters.remove(sessionId);
            }
        }
    }

    private void sendError(String sessionId, String errorMessage) {
        SseEmitter emitter = activeEmitters.get(sessionId);
        if (emitter != null) {
            try {
                Map<String, String> err = Map.of("type", "error", "error", errorMessage);
                emitter.send(SseEmitter.event().name("error").data(objectMapper.writeValueAsString(err)));
                emitter.completeWithError(new RuntimeException(errorMessage));
            } catch (IOException ignored) {}
        }
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
