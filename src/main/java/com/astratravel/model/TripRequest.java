package com.astratravel.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class TripRequest {
    private String origin;
    private String destination;
    private String startDate;
    private String endDate;
    private double budget;
    private String currency = "USD";
    private int travelers = 1;
    private String travelStyle = "Balanced Explorer"; // "Budget Backpacker", "Balanced Explorer", "Luxury Nomad", "Cultural Immersion", "Family Friendly"
    private String interests = "Culture, Cuisine, Sightseeing";
    private String customNotes = "";

    public TripRequest() {}

    public TripRequest(String origin, String destination, String startDate, String endDate, double budget, String currency, int travelers, String travelStyle, String interests) {
        this.origin = origin;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;
        this.currency = currency;
        this.travelers = travelers;
        this.travelStyle = travelStyle;
        this.interests = interests;
    }

    public long getTripDays() {
        try {
            if (startDate != null && endDate != null) {
                LocalDate start = LocalDate.parse(startDate);
                LocalDate end = LocalDate.parse(endDate);
                long days = ChronoUnit.DAYS.between(start, end);
                return Math.max(days, 1);
            }
        } catch (Exception e) {
            // fallback
        }
        return 5;
    }

    // Getters and Setters
    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }

    public double getBudget() { return budget; }
    public void setBudget(double budget) { this.budget = budget; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public int getTravelers() { return travelers; }
    public void setTravelers(int travelers) { this.travelers = travelers; }

    public String getTravelStyle() { return travelStyle; }
    public void setTravelStyle(String travelStyle) { this.travelStyle = travelStyle; }

    public String getInterests() { return interests; }
    public void setInterests(String interests) { this.interests = interests; }

    public String getCustomNotes() { return customNotes; }
    public void setCustomNotes(String customNotes) { this.customNotes = customNotes; }
}
