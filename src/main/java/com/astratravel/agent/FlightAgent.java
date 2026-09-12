package com.astratravel.agent;

import com.astratravel.model.AgentMessage;
import com.astratravel.model.FlightOption;
import com.astratravel.model.TripRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Locale;

@Component
public class FlightAgent implements Agent {

    @Override
    public String getAgentId() { return "agent-flight"; }

    @Override
    public String getName() { return "AeroStream"; }

    @Override
    public String getRole() { return "Flight Route & Fare Optimization Specialist"; }

    @Override
    public String getAvatar() { return "✈️"; }

    @Override
    public String getAccentColor() { return "#06B6D4"; } // Neon Cyan

    public FlightProposal evaluateFlights(TripRequest request, double allocatedBudget) {
        String dest = request.getDestination().trim();
        String origin = (request.getOrigin() != null && !request.getOrigin().isBlank()) ? request.getOrigin().trim() : "New York (JFK)";
        String lower = dest.toLowerCase(Locale.ROOT);

        // Calculate a balanced flight price within ~30-40% of budget
        double maxFlightSpend = Math.max(allocatedBudget * 0.40, 250);
        double targetPrice = Math.min(maxFlightSpend, allocatedBudget * 0.35);

        String outAirline = "Delta Air Lines";
        String outCode = "DL";
        String outFlight = "DL-284";
        String retAirline = "United Airlines";
        String retCode = "UA";
        String retFlight = "UA-791";
        String arrAirport = "HND (Haneda)";
        String depAirport = "JFK (John F. Kennedy)";
        String duration = "13h 45m";
        String stops = "Non-stop";
        String aircraft = "Airbus A350-900";
        double basePrice = Math.round(targetPrice * 0.9);

        if (lower.contains("tokyo") || lower.contains("japan")) {
            outAirline = "All Nippon Airways (ANA)";
            outCode = "NH";
            outFlight = "NH-109";
            retAirline = "Japan Airlines (JAL)";
            retCode = "JL";
            retFlight = "JL-004";
            arrAirport = "HND (Tokyo Haneda)";
            duration = "14h 10m";
            aircraft = "Boeing 787-9 Dreamliner";
        } else if (lower.contains("paris") || lower.contains("france")) {
            outAirline = "Air France";
            outCode = "AF";
            outFlight = "AF-023";
            retAirline = "Air France";
            retCode = "AF";
            retFlight = "AF-022";
            arrAirport = "CDG (Paris Charles de Gaulle)";
            duration = "7h 35m";
            aircraft = "Airbus A350-900";
        } else if (lower.contains("rome") || lower.contains("italy")) {
            outAirline = "ITA Airways";
            outCode = "AZ";
            outFlight = "AZ-609";
            retAirline = "Delta Air Lines";
            retCode = "DL";
            retFlight = "DL-153";
            arrAirport = "FCO (Rome Fiumicino)";
            duration = "8h 15m";
            aircraft = "Airbus A330neo";
        } else if (lower.contains("bali") || lower.contains("indonesia")) {
            outAirline = "Singapore Airlines";
            outCode = "SQ";
            outFlight = "SQ-025";
            retAirline = "Singapore Airlines";
            retCode = "SQ";
            retFlight = "SQ-938";
            arrAirport = "DPS (Ngurah Rai Bali)";
            duration = "19h 20m";
            stops = "1 Stop (SIN Changi - 1h 40m)";
            aircraft = "Boeing 777-300ER";
        } else if (lower.contains("swiss") || lower.contains("zurich") || lower.contains("alps")) {
            outAirline = "Swiss International Air Lines";
            outCode = "LX";
            outFlight = "LX-017";
            retAirline = "Swiss International Air Lines";
            retCode = "LX";
            retFlight = "LX-016";
            arrAirport = "ZRH (Zurich Airport)";
            duration = "7h 50m";
            aircraft = "Boeing 777-300ER";
        } else if (lower.contains("dubai") || lower.contains("uae")) {
            outAirline = "Emirates";
            outCode = "EK";
            outFlight = "EK-202";
            retAirline = "Emirates";
            retCode = "EK";
            retFlight = "EK-201";
            arrAirport = "DXB (Dubai International)";
            duration = "12h 30m";
            aircraft = "Airbus A380-800 Superjumbo";
        } else if (lower.contains("london") || lower.contains("uk") || lower.contains("england")) {
            outAirline = "British Airways";
            outCode = "BA";
            outFlight = "BA-112";
            retAirline = "Virgin Atlantic";
            retCode = "VS";
            retFlight = "VS-003";
            arrAirport = "LHR (London Heathrow)";
            duration = "7h 00m";
            aircraft = "Boeing 787-9";
        } else if (lower.contains("new york") || lower.contains("nyc")) {
            outAirline = "JetBlue Airways";
            outCode = "B6";
            outFlight = "B6-412";
            retAirline = "Delta Air Lines";
            retCode = "DL";
            retFlight = "DL-2104";
            arrAirport = "JFK (John F. Kennedy)";
            duration = "5h 40m";
            aircraft = "Airbus A321neo";
        } else if (lower.contains("cairo") || lower.contains("egypt")) {
            outAirline = "EgyptAir";
            outCode = "MS";
            outFlight = "MS-986";
            retAirline = "EgyptAir";
            retCode = "MS";
            retFlight = "MS-985";
            arrAirport = "CAI (Cairo International)";
            duration = "10h 30m";
            aircraft = "Boeing 787-9";
        } else if (lower.contains("barcelona") || lower.contains("spain")) {
            outAirline = "Iberia";
            outCode = "IB";
            outFlight = "IB-6250";
            retAirline = "Iberia";
            retCode = "IB";
            retFlight = "IB-6251";
            arrAirport = "BCN (Barcelona El Prat)";
            duration = "8h 00m";
            aircraft = "Airbus A330-200";
        }

        // Adjust cabin class based on travel style or budget
        String cabinClass = "Economy Choice";
        if (request.getTravelStyle().toLowerCase().contains("luxury") && allocatedBudget > 4000) {
            cabinClass = "Business Suite (Lie-Flat)";
            basePrice = Math.min(allocatedBudget * 0.45, 1800);
        } else if (allocatedBudget > 2500) {
            cabinClass = "Premium Economy (Extra Legroom & Priority)";
        }

        String depTime = "09:30 AM";
        String arrTime = "02:45 PM (+1 day)";
        String retDepTime = "06:15 PM";
        String retArrTime = "09:50 PM";

        double outPrice = Math.round(basePrice * 0.52 * 100.0) / 100.0;
        double retPrice = Math.round(basePrice * 0.48 * 100.0) / 100.0;

        FlightOption out = new FlightOption(
                "Outbound", outAirline, outCode, outFlight, depAirport, arrAirport,
                origin, dest, depTime, arrTime, duration, stops,
                cabinClass, "1x 23kg Checked + 10kg Carry-on", outPrice, aircraft
        );

        FlightOption ret = new FlightOption(
                "Return", retAirline, retCode, retFlight, arrAirport, depAirport,
                dest, origin, retDepTime, retArrTime, duration, stops,
                cabinClass, "1x 23kg Checked + 10kg Carry-on", retPrice, aircraft
        );

        return new FlightProposal(out, ret, outPrice + retPrice);
    }

    public AgentMessage createProposalMessage(FlightProposal proposal, TripRequest request) {
        String text = String.format(
                "✈️ Sourced optimal flight pairing for %s: Outbound on %s (%s) & Return on %s (%s). Class: %s. Total Airfare: $%s (Leaves plenty of room for Sanctuary & Curator).",
                request.getDestination(),
                proposal.outbound.getAirline(), proposal.outbound.getFlightNumber(),
                proposal.inbound.getAirline(), proposal.inbound.getFlightNumber(),
                proposal.outbound.getCabinClass(),
                String.format("%.2f", proposal.totalCost)
        );
        return new AgentMessage(getName(), getRole(), "Atlas & Sanctuary", text, "FLIGHT_ANALYSIS", "success");
    }

    public static class FlightProposal {
        public FlightOption outbound;
        public FlightOption inbound;
        public double totalCost;

        public FlightProposal(FlightOption outbound, FlightOption inbound, double totalCost) {
            this.outbound = outbound;
            this.inbound = inbound;
            this.totalCost = totalCost;
        }
    }
}
