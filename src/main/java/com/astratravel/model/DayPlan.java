package com.astratravel.model;

import java.util.ArrayList;
import java.util.List;

public class DayPlan {
    private int dayNumber;
    private String dateStr;
    private String title;
    private String theme;
    private String weatherSummary;
    private String dayHighlight;
    private int estimatedSteps;
    private double dailyEstimatedSpend;
    private List<ActivityItem> activities = new ArrayList<>();

    public DayPlan() {}

    public DayPlan(int dayNumber, String dateStr, String title, String theme, String weatherSummary, String dayHighlight, int estimatedSteps) {
        this.dayNumber = dayNumber;
        this.dateStr = dateStr;
        this.title = title;
        this.theme = theme;
        this.weatherSummary = weatherSummary;
        this.dayHighlight = dayHighlight;
        this.estimatedSteps = estimatedSteps;
    }

    public void addActivity(ActivityItem item) {
        this.activities.add(item);
        recalcDailySpend();
    }

    public void recalcDailySpend() {
        this.dailyEstimatedSpend = activities.stream().mapToDouble(ActivityItem::getCost).sum();
    }

    // Getters and Setters
    public int getDayNumber() { return dayNumber; }
    public void setDayNumber(int dayNumber) { this.dayNumber = dayNumber; }

    public String getDateStr() { return dateStr; }
    public void setDateStr(String dateStr) { this.dateStr = dateStr; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }

    public String getWeatherSummary() { return weatherSummary; }
    public void setWeatherSummary(String weatherSummary) { this.weatherSummary = weatherSummary; }

    public String getDayHighlight() { return dayHighlight; }
    public void setDayHighlight(String dayHighlight) { this.dayHighlight = dayHighlight; }

    public int getEstimatedSteps() { return estimatedSteps; }
    public void setEstimatedSteps(int estimatedSteps) { this.estimatedSteps = estimatedSteps; }

    public double getDailyEstimatedSpend() { return dailyEstimatedSpend; }
    public void setDailyEstimatedSpend(double dailyEstimatedSpend) { this.dailyEstimatedSpend = dailyEstimatedSpend; }

    public List<ActivityItem> getActivities() { return activities; }
    public void setActivities(List<ActivityItem> activities) {
        this.activities = activities;
        recalcDailySpend();
    }
}
