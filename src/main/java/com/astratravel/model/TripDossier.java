package com.astratravel.model;

import java.util.ArrayList;
import java.util.List;

public class TripDossier {
    private String id;
    private String destination;
    private String destinationCountry;
    private String destinationTagline;
    private String heroImageUrl;
    private String origin;
    private String startDate;
    private String endDate;
    private long totalDays;
    private int travelers;
    private String travelStyle;
    private String summary;
    private String weatherForecast;
    private String localCurrencyAdvice;
    private String powerPlugInfo;
    private String emergencyNumber;
    private String orchestratorSigning;

    private FlightOption outboundFlight;
    private FlightOption returnFlight;
    private StayOption stay;
    private List<DayPlan> itinerary = new ArrayList<>();
    private BudgetBreakdown budget;
    private List<String> packingChecklist = new ArrayList<>();
    private List<String> localInsiderHacks = new ArrayList<>();
    private List<AgentMessage> agentDialogueHistory = new ArrayList<>();

    public TripDossier() {
        this.id = "DOSSIER-" + System.currentTimeMillis();
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public String getDestinationCountry() { return destinationCountry; }
    public void setDestinationCountry(String destinationCountry) { this.destinationCountry = destinationCountry; }

    public String getDestinationTagline() { return destinationTagline; }
    public void setDestinationTagline(String destinationTagline) { this.destinationTagline = destinationTagline; }

    public String getHeroImageUrl() { return heroImageUrl; }
    public void setHeroImageUrl(String heroImageUrl) { this.heroImageUrl = heroImageUrl; }

    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }

    public long getTotalDays() { return totalDays; }
    public void setTotalDays(long totalDays) { this.totalDays = totalDays; }

    public int getTravelers() { return travelers; }
    public void setTravelers(int travelers) { this.travelers = travelers; }

    public String getTravelStyle() { return travelStyle; }
    public void setTravelStyle(String travelStyle) { this.travelStyle = travelStyle; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getWeatherForecast() { return weatherForecast; }
    public void setWeatherForecast(String weatherForecast) { this.weatherForecast = weatherForecast; }

    public String getLocalCurrencyAdvice() { return localCurrencyAdvice; }
    public void setLocalCurrencyAdvice(String localCurrencyAdvice) { this.localCurrencyAdvice = localCurrencyAdvice; }

    public String getPowerPlugInfo() { return powerPlugInfo; }
    public void setPowerPlugInfo(String powerPlugInfo) { this.powerPlugInfo = powerPlugInfo; }

    public String getEmergencyNumber() { return emergencyNumber; }
    public void setEmergencyNumber(String emergencyNumber) { this.emergencyNumber = emergencyNumber; }

    public String getOrchestratorSigning() { return orchestratorSigning; }
    public void setOrchestratorSigning(String orchestratorSigning) { this.orchestratorSigning = orchestratorSigning; }

    public FlightOption getOutboundFlight() { return outboundFlight; }
    public void setOutboundFlight(FlightOption outboundFlight) { this.outboundFlight = outboundFlight; }

    public FlightOption getReturnFlight() { return returnFlight; }
    public void setReturnFlight(FlightOption returnFlight) { this.returnFlight = returnFlight; }

    public StayOption getStay() { return stay; }
    public void setStay(StayOption stay) { this.stay = stay; }

    public List<DayPlan> getItinerary() { return itinerary; }
    public void setItinerary(List<DayPlan> itinerary) { this.itinerary = itinerary; }

    public BudgetBreakdown getBudget() { return budget; }
    public void setBudget(BudgetBreakdown budget) { this.budget = budget; }

    public List<String> getPackingChecklist() { return packingChecklist; }
    public void setPackingChecklist(List<String> packingChecklist) { this.packingChecklist = packingChecklist; }

    public List<String> getLocalInsiderHacks() { return localInsiderHacks; }
    public void setLocalInsiderHacks(List<String> localInsiderHacks) { this.localInsiderHacks = localInsiderHacks; }

    public List<AgentMessage> getAgentDialogueHistory() { return agentDialogueHistory; }
    public void setAgentDialogueHistory(List<AgentMessage> agentDialogueHistory) { this.agentDialogueHistory = agentDialogueHistory; }
}
