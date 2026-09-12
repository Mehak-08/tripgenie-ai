package com.astratravel.model;

public class TweakRequest {
    private String userMessage;
    private TripDossier currentDossier;

    public TweakRequest() {}

    public String getUserMessage() { return userMessage; }
    public void setUserMessage(String userMessage) { this.userMessage = userMessage; }

    public TripDossier getCurrentDossier() { return currentDossier; }
    public void setCurrentDossier(TripDossier currentDossier) { this.currentDossier = currentDossier; }
}
