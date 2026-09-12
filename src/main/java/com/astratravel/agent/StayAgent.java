package com.astratravel.agent;

import com.astratravel.model.AgentMessage;
import com.astratravel.model.StayOption;
import com.astratravel.model.TripRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Component
public class StayAgent implements Agent {

    @Override
    public String getAgentId() { return "agent-stay"; }

    @Override
    public String getName() { return "Sanctuary"; }

    @Override
    public String getRole() { return "Accommodations & District Vibe Specialist"; }

    @Override
    public String getAvatar() { return "🏨"; }

    @Override
    public String getAccentColor() { return "#10B981"; } // Emerald Green

    public StayOption evaluateStay(TripRequest request, double remainingBudgetForStay, int totalNights) {
        int nights = Math.max(totalNights, 1);
        double targetNightlyRate = Math.max(remainingBudgetForStay / nights, 65.0);

        String dest = request.getDestination().trim();
        String lower = dest.toLowerCase(Locale.ROOT);

        String hotelName = "The Grand Metropolitan Vibe Hotel";
        double starRating = 4.7;
        String district = "Downtown Cultural Center";
        String roomType = "Deluxe King View Room with Skyline Vista";
        String vibe = "Chic boutique sanctuary with high-speed transit connectivity";
        String address = "124 Central Boulevard";
        String metro = "3 min walk to Central Metro";
        String imageUrl = "https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=1200&q=80";
        List<String> amenities = new ArrayList<>(Arrays.asList(
                "High-Speed Fiber Wi-Fi", "Skyline Rooftop Lounge", "Artisan Coffee Bar",
                "Rainfall Shower & Organic Toiletries", "Soundproof Panoramic Windows", "24/7 Concierge"
        ));
        double guestScore = 9.3;
        int reviewCount = 1420;

        if (lower.contains("tokyo") || lower.contains("japan")) {
            hotelName = "Trunk Hotel Yoyogi Park & Shibuya Onsen";
            district = "Shibuya / Yoyogi Park";
            address = "1-15-24 Tomigaya, Shibuya-ku, Tokyo";
            roomType = "Park View Balcony Suite & Hinoki Bath";
            vibe = "Minimalist Japandi aesthetic overlooking tranquil park canopies with heated rooftop infinity onsen";
            metro = "4 min walk to Yoyogi-Koen & Harajuku Station";
            imageUrl = "https://images.unsplash.com/photo-1503899036084-c55cdd92da26?auto=format&fit=crop&w=1200&q=80";
            amenities = Arrays.asList("Rooftop Heated Onsen", "Custom Bicycles", "Single-Origin Pour Over Bar", "Daikanyama Vinyl Lounge", "Direct Narita Express shuttle");
            guestScore = 9.5;
            reviewCount = 2180;
        } else if (lower.contains("paris") || lower.contains("france")) {
            hotelName = "Hôtel Madame Rêve & Jardin Secret";
            district = "1st Arrondissement / Le Marais Border";
            address = "48 Rue du Louvre, 75001 Paris";
            roomType = "Haussmannian Suite with Eiffel Tower Glimpse";
            vibe = "Quintessential Parisian luxury, walnut woodwork, custom silk velvet, and a rooftop sky-garden overlooking Saint-Eustache";
            metro = "2 min walk to Louvre - Rivoli Metro";
            imageUrl = "https://images.unsplash.com/photo-1502602898657-3e91760cbb34?auto=format&fit=crop&w=1200&q=80";
            amenities = Arrays.asList("Panoramic Rooftop Cocktail Garden", "Pierre Hermé Breakfast", "Diptique Bath Amenities", "Private Wellness Spa");
            guestScore = 9.4;
            reviewCount = 1890;
        } else if (lower.contains("rome") || lower.contains("italy")) {
            hotelName = "Palazzo Navona & Terrazza Roma";
            district = "Piazza Navona / Pantheon";
            address = "Largo della Sapienza 8, 00186 Rome";
            roomType = "Classic Renaissance Room with Travertine Bathroom";
            vibe = "Historic 16th-century palazzo re-imagined with sleek Italian minimalism, steps from gelato master craftsmen";
            metro = "5 min walk to Spagna Metro / Colosseum Bus Link";
            imageUrl = "https://images.unsplash.com/photo-1552832230-c0197dd311b5?auto=format&fit=crop&w=1200&q=80";
            amenities = Arrays.asList("Rooftop Spritz Terrace", "Complimentary Prosecco Welcome", "Artisan Bakery Breakfast", "Soundproof Historic Windows");
            guestScore = 9.2;
            reviewCount = 1350;
        } else if (lower.contains("bali") || lower.contains("indonesia")) {
            hotelName = "Maya Ubud Rainforest Sanctuary & Pool Villas";
            district = "Petanu River Valley, Ubud";
            address = "Jl. Gunung Sari Peliatan, Ubud, Bali";
            roomType = "Private River-Facing Plunge Pool Villa";
            vibe = "Lush tropical sanctuary nestled amidst emerald bamboo forests, whispering waterfalls, and morning yoga shalas";
            metro = "Complimentary Shuttle to Ubud Palace & Monkey Forest";
            imageUrl = "https://images.unsplash.com/photo-1537996194471-e657df975ab4?auto=format&fit=crop&w=1200&q=80";
            amenities = Arrays.asList("Private Infinity Plunge Pool", "Balinese Riverfront Spa", "Complimentary Sunrise Yoga", "Floating Breakfast Service");
            guestScore = 9.7;
            reviewCount = 3120;
        } else if (lower.contains("swiss") || lower.contains("alps") || lower.contains("zurich")) {
            hotelName = "The Chedi Andermatt & Alpine Chalet";
            district = "Andermatt / Swiss Alps";
            address = "Gotthardstrasse 4, 6490 Andermatt, Switzerland";
            roomType = "Alpine Deluxe Room with Fireplace & Murano Glass";
            vibe = "Grand Alpine luxury blending warm alpine woods, fireplaces, cheese humidors, and snow-capped peak panoramas";
            metro = "3 min walk to Glacier Express Scenic Railway";
            imageUrl = "https://images.unsplash.com/photo-1530122037265-a5f1f91d3b99?auto=format&fit=crop&w=1200&q=80";
            amenities = Arrays.asList("Heated Indoor/Outdoor Hydrotherapy Pools", "Ski Butler Service", "Alpine Cheese & Wine Cellar", "Finnish Saunas");
            guestScore = 9.8;
            reviewCount = 1640;
        } else if (lower.contains("dubai") || lower.contains("uae")) {
            hotelName = "Address Sky View & Marina Oasis";
            district = "Downtown Dubai / Burj Khalifa District";
            address = "Emaar Square Area, Downtown Dubai";
            roomType = "Premier Burj View Room with Floor-to-Ceiling Glass";
            vibe = "Futuristic twin towers linked by a magnificent sky bridge featuring a 70-meter infinity pool hovering above clouds";
            metro = "Direct Air-Conditioned Walkway to Dubai Mall Metro";
            imageUrl = "https://images.unsplash.com/photo-1512453979798-5ea266f8880c?auto=format&fit=crop&w=1200&q=80";
            amenities = Arrays.asList("54th Floor Cantilever Infinity Pool", "Burj Khalifa Light Show Views", "Qix Kids Club", "Cé La Vi Dining");
            guestScore = 9.4;
            reviewCount = 4210;
        } else if (lower.contains("new york") || lower.contains("nyc")) {
            hotelName = "The Standard High Line & Meatpacking Studio";
            district = "Meatpacking District / Chelsea";
            address = "848 Washington St, New York, NY 10014";
            roomType = "Deluxe Hudson River View Queen";
            vibe = "Iconic architectural marvel straddling the High Line elevated park with dramatic sunset views over the Hudson";
            metro = "4 min walk to 14th St - 8th Ave Subway";
            imageUrl = "https://images.unsplash.com/photo-1538688525198-9b88f6f53126?auto=format&fit=crop&w=1200&q=80";
            amenities = Arrays.asList("Le Bain Rooftop & Discotheque", "Standard Grill Biergarten", "High Line Park Access", "Custom Robes & Big Minibar");
            guestScore = 9.1;
            reviewCount = 2950;
        }

        // Adjust rate to align accurately with remaining budget
        double nightlyRate = Math.round(targetNightlyRate * 100.0) / 100.0;

        return new StayOption(
                hotelName, starRating, district, dest,
                nightlyRate, nights, request.getStartDate(), request.getEndDate(),
                roomType, vibe, address, imageUrl,
                amenities, guestScore, reviewCount, metro
        );
    }

    public AgentMessage createProposalMessage(StayOption stay, TripRequest request) {
        String text = String.format(
                "🏨 Secured accommodation for %d nights: '%s' in %s (%s/night, total: $%s). Rated %.1f★ with %s. Vibe matches user style '%s' seamlessly.",
                stay.getTotalNights(),
                stay.getHotelName(),
                stay.getDistrict(),
                String.format("%.2f", stay.getNightlyRate()),
                String.format("%.2f", stay.getTotalCost()),
                stay.getStarRating(),
                stay.getMetroDistance(),
                request.getTravelStyle()
        );
        return new AgentMessage(getName(), getRole(), "Atlas & Curator", text, "STAY_MATCHING", "success");
    }

    public AgentMessage createNegotiationMessage(double amountToTrim, String proposalAdjustment) {
        String text = String.format(
                "🤝 Sanctuary Negotiation: To preserve room for Curator's premium experiences and avoid exceeding the user's hard budget cap, I adjusted our room category. Saved $%s while retaining complimentary artisan breakfast and central transit access!",
                String.format("%.2f", amountToTrim)
        );
        return new AgentMessage(getName(), getRole(), "Atlas & Auditor", text, "BUDGET_NEGOTIATION", "negotiate");
    }
}
