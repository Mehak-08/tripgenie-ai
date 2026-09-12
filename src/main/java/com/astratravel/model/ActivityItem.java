package com.astratravel.model;

public class ActivityItem {
    private String id;
    private String timeSlot;
    private String period; // "Morning", "Afternoon", "Evening", "Night"
    private String title;
    private String description;
    private String location;
    private double cost;
    private String category;
    private int durationMinutes;
    private String insiderTip;
    private String imageUrl;
    private double rating;
    private boolean mustVisit;

    public ActivityItem() {}

    public ActivityItem(String timeSlot, String period, String title, String description,
                        String location, double cost, String category, int durationMinutes,
                        String insiderTip, String imageUrl, double rating, boolean mustVisit) {
        this.id = "act-" + (int)(Math.random() * 10000);
        this.timeSlot = timeSlot;
        this.period = period;
        this.title = title;
        this.description = description;
        this.location = location;
        this.cost = cost;
        this.category = category;
        this.durationMinutes = durationMinutes;
        this.insiderTip = insiderTip;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.mustVisit = mustVisit;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTimeSlot() { return timeSlot; }
    public void setTimeSlot(String timeSlot) { this.timeSlot = timeSlot; }

    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public double getCost() { return cost; }
    public void setCost(double cost) { this.cost = cost; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }

    public String getInsiderTip() { return insiderTip; }
    public void setInsiderTip(String insiderTip) { this.insiderTip = insiderTip; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public boolean isMustVisit() { return mustVisit; }
    public void setMustVisit(boolean mustVisit) { this.mustVisit = mustVisit; }
}
