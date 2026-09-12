package com.astratravel;

import com.astratravel.agent.*;
import com.astratravel.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TravelPlannerApplicationTests {

    @Autowired
    private AtlasOrchestratorAgent orchestratorAgent;

    @Autowired
    private FlightAgent flightAgent;

    @Autowired
    private StayAgent stayAgent;

    @Autowired
    private ActivityAgent activityAgent;

    @Autowired
    private BudgetAuditorAgent auditorAgent;

    @Test
    void contextLoads() {
        assertNotNull(orchestratorAgent);
        assertNotNull(flightAgent);
        assertNotNull(stayAgent);
        assertNotNull(activityAgent);
        assertNotNull(auditorAgent);
    }

    @Test
    void testAgentPipelineEndToEnd() {
        TripRequest request = new TripRequest(
                "New York (JFK)",
                "Tokyo, Japan",
                "2026-10-10",
                "2026-10-15",
                2500.0,
                "USD",
                1,
                "Balanced Explorer",
                "Culture, Cuisine, Panoramic Views"
        );

        assertEquals(5, request.getTripDays());

        // 1. Flight agent
        FlightAgent.FlightProposal flightProposal = flightAgent.evaluateFlights(request, 1000.0);
        assertNotNull(flightProposal.outbound);
        assertNotNull(flightProposal.inbound);
        assertTrue(flightProposal.totalCost > 0);

        // 2. Stay agent
        StayOption stay = stayAgent.evaluateStay(request, 850.0, 5);
        assertNotNull(stay);
        assertEquals(5, stay.getTotalNights());
        assertTrue(stay.getTotalCost() > 0);

        // 3. Activity agent
        List<DayPlan> days = activityAgent.curateItinerary(request, 500.0, 5);
        assertEquals(5, days.size());
        assertTrue(days.get(0).getActivities().size() >= 2);

        // 4. Auditor
        double activitiesCost = days.stream().mapToDouble(DayPlan::getDailyEstimatedSpend).sum();
        BudgetBreakdown budget = auditorAgent.auditAndBalance(request, flightProposal.totalCost, stay.getTotalCost(), activitiesCost);
        assertNotNull(budget);
        assertTrue(budget.getEmergencyBuffer() > 0);

        // 5. Orchestrator dossier assembly
        TripDossier dossier = orchestratorAgent.assembleDossier(
                request, flightProposal, stay, days, budget, new ArrayList<>()
        );
        assertNotNull(dossier);
        assertEquals("Tokyo, Japan", dossier.getDestination());
        assertNotNull(dossier.getOutboundFlight());
        assertNotNull(dossier.getStay());
        assertEquals(5, dossier.getItinerary().size());
        assertFalse(dossier.getPackingChecklist().isEmpty());
    }
}
