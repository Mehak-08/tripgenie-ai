package com.astratravel.model;

import java.util.ArrayList;
import java.util.List;

public class BudgetBreakdown {
    private double totalBudget;
    private String currency = "USD";
    private double totalCost;
    private double flightsCost;
    private double staysCost;
    private double activitiesCost;
    private double diningAndTransitCost;
    private double emergencyBuffer;
    private double remainingSavings;
    private double flightsPercentage;
    private double staysPercentage;
    private double activitiesPercentage;
    private double diningPercentage;
    private double bufferPercentage;
    private String status;
    private List<String> optimizationNotes = new ArrayList<>();

    public BudgetBreakdown() {}

    public BudgetBreakdown(double totalBudget, String currency, double flightsCost, double staysCost, double activitiesCost, double diningAndTransitCost) {
        this.totalBudget = totalBudget;
        this.currency = currency;
        this.flightsCost = Math.round(flightsCost * 100.0) / 100.0;
        this.staysCost = Math.round(staysCost * 100.0) / 100.0;
        this.activitiesCost = Math.round(activitiesCost * 100.0) / 100.0;
        this.diningAndTransitCost = Math.round(diningAndTransitCost * 100.0) / 100.0;

        double subtotal = this.flightsCost + this.staysCost + this.activitiesCost + this.diningAndTransitCost;
        this.remainingSavings = Math.max(0, Math.round((totalBudget - subtotal) * 100.0) / 100.0);
        this.emergencyBuffer = Math.min(totalBudget * 0.10, this.remainingSavings);
        this.totalCost = Math.min(subtotal + this.emergencyBuffer, totalBudget);

        if (totalBudget > 0) {
            this.flightsPercentage = Math.round((this.flightsCost / totalBudget) * 1000.0) / 10.0;
            this.staysPercentage = Math.round((this.staysCost / totalBudget) * 1000.0) / 10.0;
            this.activitiesPercentage = Math.round((this.activitiesCost / totalBudget) * 1000.0) / 10.0;
            this.diningPercentage = Math.round((this.diningAndTransitCost / totalBudget) * 1000.0) / 10.0;
            this.bufferPercentage = Math.round((this.emergencyBuffer / totalBudget) * 1000.0) / 10.0;
        }

        if (subtotal <= totalBudget) {
            this.status = "OPTIMIZED_WITH_SURPLUS";
        } else {
            this.status = "BUDGET_REBALANCED";
        }
    }

    // Getters and Setters
    public double getTotalBudget() { return totalBudget; }
    public void setTotalBudget(double totalBudget) { this.totalBudget = totalBudget; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public double getTotalCost() { return totalCost; }
    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }

    public double getFlightsCost() { return flightsCost; }
    public void setFlightsCost(double flightsCost) { this.flightsCost = flightsCost; }

    public double getStaysCost() { return staysCost; }
    public void setStaysCost(double staysCost) { this.staysCost = staysCost; }

    public double getActivitiesCost() { return activitiesCost; }
    public void setActivitiesCost(double activitiesCost) { this.activitiesCost = activitiesCost; }

    public double getDiningAndTransitCost() { return diningAndTransitCost; }
    public void setDiningAndTransitCost(double diningAndTransitCost) { this.diningAndTransitCost = diningAndTransitCost; }

    public double getEmergencyBuffer() { return emergencyBuffer; }
    public void setEmergencyBuffer(double emergencyBuffer) { this.emergencyBuffer = emergencyBuffer; }

    public double getRemainingSavings() { return remainingSavings; }
    public void setRemainingSavings(double remainingSavings) { this.remainingSavings = remainingSavings; }

    public double getFlightsPercentage() { return flightsPercentage; }
    public void setFlightsPercentage(double flightsPercentage) { this.flightsPercentage = flightsPercentage; }

    public double getStaysPercentage() { return staysPercentage; }
    public void setStaysPercentage(double staysPercentage) { this.staysPercentage = staysPercentage; }

    public double getActivitiesPercentage() { return activitiesPercentage; }
    public void setActivitiesPercentage(double activitiesPercentage) { this.activitiesPercentage = activitiesPercentage; }

    public double getDiningPercentage() { return diningPercentage; }
    public void setDiningPercentage(double diningPercentage) { this.diningPercentage = diningPercentage; }

    public double getBufferPercentage() { return bufferPercentage; }
    public void setBufferPercentage(double bufferPercentage) { this.bufferPercentage = bufferPercentage; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<String> getOptimizationNotes() { return optimizationNotes; }
    public void setOptimizationNotes(List<String> optimizationNotes) { this.optimizationNotes = optimizationNotes; }
}
