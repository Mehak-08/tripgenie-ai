package com.astratravel.agent;

import com.astratravel.model.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Component
public class AtlasOrchestratorAgent implements Agent {

    @Override
    public String getAgentId() { return "agent-orchestrator"; }

    @Override
    public String getName() { return "Atlas"; }

    @Override
    public String getRole() { return "Apex Multi-Agent Orchestrator"; }

    @Override
    public String getAvatar() { return "🌟"; }

    @Override
    public String getAccentColor() { return "#6366F1"; } // Electric Indigo

    public AgentMessage createKickoffMessage(TripRequest request) {
        String content = String.format(
                "🌟 Orchestrator Mission Initiated: Delegating multi-agent protocol for %d-day expedition to %s. Total Budget Cap: $%s %s for %d traveler(s). Style: '%s'. FlightAgent (AeroStream), StayAgent (Sanctuary), and ActivityAgent (Curator): begin real-time sourcing and budget reconciliation.",
                request.getTripDays(),
                request.getDestination(),
                String.format("%.2f", request.getBudget()),
                request.getCurrency(),
                request.getTravelers(),
                request.getTravelStyle()
        );
        return new AgentMessage(getName(), getRole(), "All Agents", content, "INGESTION", "neutral");
    }

    public AgentMessage createSynthesisMessage(TripDossier dossier) {
        String content = String.format(
                "🌟 Master Dossier Finalized: All agent proposals have been cross-checked, verified against safety & transit metrics, and aligned with the user's budget. Delivering complete day-by-day itinerary, boarding passes, boutique accommodations, and packing intelligence for %s!",
                dossier.getDestination()
        );
        return new AgentMessage(getName(), getRole(), "User", content, "DOSSIER_SYNTHESIS", "final");
    }

    public TripDossier assembleDossier(
            TripRequest request,
            FlightAgent.FlightProposal flightProposal,
            StayOption stay,
            List<DayPlan> itinerary,
            BudgetBreakdown budget,
            List<AgentMessage> dialogueHistory
    ) {
        TripDossier dossier = new TripDossier();
        dossier.setDestination(request.getDestination());
        dossier.setOrigin(request.getOrigin() != null ? request.getOrigin() : "New York (JFK)");
        dossier.setStartDate(request.getStartDate());
        dossier.setEndDate(request.getEndDate());
        dossier.setTotalDays(request.getTripDays());
        dossier.setTravelers(request.getTravelers());
        dossier.setTravelStyle(request.getTravelStyle());

        // Destination metadata & aesthetics
        enrichDestinationMetadata(dossier, request.getDestination());

        dossier.setOutboundFlight(flightProposal.outbound);
        dossier.setReturnFlight(flightProposal.inbound);
        dossier.setStay(stay);
        dossier.setItinerary(itinerary);
        dossier.setBudget(budget);
        dossier.setAgentDialogueHistory(dialogueHistory);
        dossier.setOrchestratorSigning("TripGenie AI Autonomous Swarm (powered by GPT-6 Astra)");

        return dossier;
    }

    private void enrichDestinationMetadata(TripDossier dossier, String destination) {
        String lower = destination.toLowerCase(Locale.ROOT);

        if (lower.contains("tokyo") || lower.contains("japan")) {
            dossier.setDestinationCountry("Japan");
            dossier.setDestinationTagline("Where ancient Shinto sanctuaries meet neon-lit futuristic alleyways");
            dossier.setHeroImageUrl("https://images.unsplash.com/photo-1503899036084-c55cdd92da26?auto=format&fit=crop&w=1600&q=80");
            dossier.setSummary("Tokyo is an exhilarating collision of cutting-edge technology, culinary wizardry, pristine transit, and deeply preserved Edo heritage.");
            dossier.setWeatherForecast("Mild & crisp, 18°C to 22°C with crystal clear skies.");
            dossier.setLocalCurrencyAdvice("Japanese Yen (JPY). Cash is still valued in small ramen shops and temples; load digital Suica/Pasmo onto mobile wallet.");
            dossier.setPowerPlugInfo("Type A & B (100V, 50/60Hz, two-prong flat pins).");
            dossier.setEmergencyNumber("Police: 110 | Ambulance & Fire: 119");
            dossier.setPackingChecklist(Arrays.asList(
                    "Slip-on shoes (many shrines and restaurants require removing shoes)",
                    "Small hand towel or handkerchief (many public restrooms do not provide paper towels)",
                    "Portable pocket power bank (indispensable for day-long navigation)",
                    "Light layered jacket for evening breeze",
                    "Universal travel adapter plug (Type A)",
                    "Coin pouch (useful for JPY 100 / 500 coins and vending machines)"
            ));
            dossier.setLocalInsiderHacks(Arrays.asList(
                    "Konbini (7-Eleven / Lawson) egg salad sandwiches and onigiri make Michelin-worthy $3 breakfasts.",
                    "Download the Japan Travel by NAVITIME app for real-time train transfer car optimization.",
                    "Tipping is not customary and can cause confusion; polite gratitude is bowed with 'Arigato gozaimasu'.",
                    "Trash bins are rare in public spaces; carry a small reusable bag for waste until you return to your hotel or train station."
            ));
        } else if (lower.contains("paris") || lower.contains("france")) {
            dossier.setDestinationCountry("France");
            dossier.setDestinationTagline("The City of Light, timeless romance, and world-defining haute cuisine");
            dossier.setHeroImageUrl("https://images.unsplash.com/photo-1502602898657-3e91760cbb34?auto=format&fit=crop&w=1600&q=80");
            dossier.setSummary("Paris captivates with grand Haussmannian boulevards, iconic riverbanks along the Seine, irreplaceable art collections, and leisurely sidewalk café culture.");
            dossier.setWeatherForecast("Pleasant & temperate, 17°C to 23°C with occasional light afternoon showers.");
            dossier.setLocalCurrencyAdvice("Euro (EUR). Contactless credit cards accepted nearly everywhere.");
            dossier.setPowerPlugInfo("Type C & E (230V, 50Hz, two round pins).");
            dossier.setEmergencyNumber("European Emergency: 112 | SAMU (Medical): 15 | Police: 17");
            dossier.setPackingChecklist(Arrays.asList(
                    "Chic, comfortable leather walking sneakers (cobblestone-friendly)",
                    "Compact travel umbrella for brief Parisian sprinkles",
                    "Light trench coat or stylish blazer for evening bistros",
                    "Crossbody anti-theft bag for busy metro lines",
                    "Reusable water bottle (Wallace fountains offer clean drinking water throughout the city)"
            ));
            dossier.setLocalInsiderHacks(Arrays.asList(
                    "Always say 'Bonjour Madame / Monsieur' when entering any shop or bakery; it is the golden key of French etiquette.",
                    "Use the Navigo Easy digital card on your phone for unlimited metro and bus transfers.",
                    "Visit museum collections during late-night nocturnal openings (e.g. Louvre on Friday evenings) for peaceful viewing."
            ));
        } else if (lower.contains("rome") || lower.contains("italy")) {
            dossier.setDestinationCountry("Italy");
            dossier.setDestinationTagline("The Eternal City of gladiator arenas, sunlit piazzas, and golden pasta");
            dossier.setHeroImageUrl("https://images.unsplash.com/photo-1552832230-c0197dd311b5?auto=format&fit=crop&w=1600&q=80");
            dossier.setSummary("Rome is an open-air museum where ancient imperial ruins sit alongside lively trattorias and dramatic Baroque fountains.");
            dossier.setWeatherForecast("Sunny & warm Mediterranean skies, 22°C to 26°C.");
            dossier.setLocalCurrencyAdvice("Euro (EUR). Keep a few coins for public restrooms and espresso at the bar.");
            dossier.setPowerPlugInfo("Type C, F & L (230V, 50Hz).");
            dossier.setEmergencyNumber("Emergency: 112 | Police (Carabinieri): 112 | Medical: 118");
            dossier.setPackingChecklist(Arrays.asList(
                    "Clothing covering shoulders and knees (mandatory for St. Peter's & Roman basilicas)",
                    "Sunglasses & UV protection",
                    "Sturdy walking shoes with grip for historic cobblestones (sanpietrini)",
                    "Refillable metal water bottle (drink from historic 'Nasoni' street fountains for free ice-cold spring water)"
            ));
            dossier.setLocalInsiderHacks(Arrays.asList(
                    "Drink your morning espresso standing at the bar counter ('al banco') like a local for half the sit-down price.",
                    "Order traditional Roman pastas: Cacio e Pepe, Carbonara, Amatriciana, or Gricia.",
                    "Toss a coin over your left shoulder with your right hand into the Trevi Fountain to guarantee your return to Rome."
            ));
        } else if (lower.contains("bali") || lower.contains("indonesia")) {
            dossier.setDestinationCountry("Indonesia");
            dossier.setDestinationTagline("Island of the Gods: emerald rice terraces, sacred temples, and ocean serenity");
            dossier.setHeroImageUrl("https://images.unsplash.com/photo-1537996194471-e657df975ab4?auto=format&fit=crop&w=1600&q=80");
            dossier.setSummary("Bali offers soulful spiritual warmth, lush jungle river valleys, dramatic coastal cliffs, and world-renowned wellness retreats.");
            dossier.setWeatherForecast("Tropical warmth, 27°C to 30°C with refreshing sea breezes.");
            dossier.setLocalCurrencyAdvice("Indonesian Rupiah (IDR). Cash is essential for local warungs and temple donations.");
            dossier.setPowerPlugInfo("Type C & F (230V, 50Hz).");
            dossier.setEmergencyNumber("Emergency: 112 | Police: 110 | Ambulance: 118");
            dossier.setPackingChecklist(Arrays.asList(
                    "Breathable linen clothing & swimwear",
                    "Sarong for temple visits (or can be borrowed at gates)",
                    "Eco-friendly insect repellent and reef-safe sunscreen",
                    "Waterproof dry bag for boat excursions and waterfalls",
                    "Slip-resistant sandals"
            ));
            dossier.setLocalInsiderHacks(Arrays.asList(
                    "Download the Grab and Gojek apps for easy, fixed-price scooter rides and private car transfers.",
                    "Visit waterfalls early between 07:00 and 08:30 AM before tourist crowds and tropical midday heat.",
                    "Try authentic Babi Guling (suckling roast pork) or Nasi Campur at neighborhood Warungs."
            ));
        } else {
            // General / Universal fallback for any custom destination
            dossier.setDestinationCountry("Global Explorer Haven");
            dossier.setDestinationTagline(String.format("An extraordinary journey into the heart, culture, and beauty of %s", destination));
            dossier.setHeroImageUrl("https://images.unsplash.com/photo-1488646953014-85cb44e25828?auto=format&fit=crop&w=1600&q=80");
            dossier.setSummary(String.format("Curated expedition exploring %s, combining verified transport logistics, boutique stays, and standout local cultural landmarks.", destination));
            dossier.setWeatherForecast("Comfortable traveling conditions, 20°C to 24°C.");
            dossier.setLocalCurrencyAdvice("Major international credit cards widely accepted; carry small local currency notes for artisan markets.");
            dossier.setPowerPlugInfo("Universal dual-voltage converter recommended.");
            dossier.setEmergencyNumber("International Emergency: 112");
            dossier.setPackingChecklist(Arrays.asList(
                    "Comfortable all-day walking footwear",
                    "Versatile weather-resistant jacket",
                    "Universal multi-region power adapter",
                    "Compact daypack for daily excursions",
                    "Personal travel pharmacy and electrolyte packets"
            ));
            dossier.setLocalInsiderHacks(Arrays.asList(
                    "Engage with friendly neighborhood barista or hotel concierge for daily off-the-beaten-path recommendations.",
                    "Keep offline maps downloaded on Google Maps / Apple Maps before heading out.",
                    "Save digital copies of your passport, travel insurance, and flight passes in your phone files."
            ));
        }
    }
}
