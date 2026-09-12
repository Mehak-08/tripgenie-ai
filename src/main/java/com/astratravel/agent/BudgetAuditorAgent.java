package com.astratravel.agent;

import com.astratravel.model.AgentMessage;
import com.astratravel.model.BudgetBreakdown;
import com.astratravel.model.TripRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BudgetAuditorAgent implements Agent {

    @Override
    public String getAgentId() { return "agent-budget"; }

    @Override
    public String getName() { return "Auditor"; }

    @Override
    public String getRole() { return "Fiscal Integrity & Tradeoff Analyst"; }

    @Override
    public String getAvatar() { return "⚖️"; }

    @Override
    public String getAccentColor() { return "#EC4899"; } // Neon Rose

    public BudgetBreakdown auditAndBalance(TripRequest request, double flightsCost, double staysCost, double activitiesCost) {
        double totalBudget = request.getBudget();
        long days = request.getTripDays();

        // Estimate meals and local ground transit (e.g. $40-$60/day depending on budget)
        double estimatedDailyDining = Math.max(totalBudget * 0.18 / days, 35.0);
        double diningAndTransitTotal = Math.round(estimatedDailyDining * days * 100.0) / 100.0;

        BudgetBreakdown breakdown = new BudgetBreakdown(
                totalBudget,
                request.getCurrency(),
                flightsCost,
                staysCost,
                activitiesCost,
                diningAndTransitTotal
        );

        List<String> notes = new ArrayList<>();
        notes.add(String.format("Flights account for %.1f%% of the total allocation.", breakdown.getFlightsPercentage()));
        notes.add(String.format("Accommodations represent %.1f%% with prime neighborhood access.", breakdown.getStaysPercentage()));
        notes.add(String.format("Activities & experiences consume %.1f%% with zero compromise on highlights.", breakdown.getActivitiesPercentage()));
        notes.add(String.format("Allocated $%.2f as an untouchable Emergency & Contingency reserve fund (%.1f%%).", breakdown.getEmergencyBuffer(), breakdown.getBufferPercentage()));

        if (breakdown.getRemainingSavings() > 0) {
            notes.add(String.format("Net surplus savings achieved: $%.2f under budget!", breakdown.getRemainingSavings()));
        }

        breakdown.setOptimizationNotes(notes);
        return breakdown;
    }

    public AgentMessage createAuditMessage(BudgetBreakdown breakdown, TripRequest request) {
        String statusText;
        String sentiment;
        if (breakdown.getTotalCost() <= breakdown.getTotalBudget()) {
            statusText = String.format(
                    "⚖️ Fiscal Audit PASSED: Total trip package calculated at $%s against $%s budget. Retained $%s (%.1f%%) in emergency buffer with $%s in surplus savings. All agent proposals green-lit!",
                    String.format("%.2f", breakdown.getTotalCost()),
                    String.format("%.2f", breakdown.getTotalBudget()),
                    String.format("%.2f", breakdown.getEmergencyBuffer()),
                    breakdown.getBufferPercentage(),
                    String.format("%.2f", breakdown.getRemainingSavings())
            );
            sentiment = "success";
        } else {
            statusText = String.format(
                    "⚖️ Fiscal Alert: Combined requests ($%s) exceed target budget cap of $%s. Triggering automatic inter-agent negotiation round to trim costs.",
                    String.format("%.2f", breakdown.getTotalCost()),
                    String.format("%.2f", breakdown.getTotalBudget())
            );
            sentiment = "alert";
        }
        return new AgentMessage(getName(), getRole(), "Atlas", statusText, "BUDGET_NEGOTIATION", sentiment);
    }
}
