package com.astratravel.agent;

import com.astratravel.model.ActivityItem;
import com.astratravel.model.AgentMessage;
import com.astratravel.model.DayPlan;
import com.astratravel.model.TripRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class ActivityAgent implements Agent {

    @Override
    public String getAgentId() { return "agent-activity"; }

    @Override
    public String getName() { return "Curator"; }

    @Override
    public String getRole() { return "Experiences, Culture & Hidden Gems Curator"; }

    @Override
    public String getAvatar() { return "🎭"; }

    @Override
    public String getAccentColor() { return "#F59E0B"; } // Vibrant Amber

    public List<DayPlan> curateItinerary(TripRequest request, double remainingBudgetForActivities, int daysCount) {
        String dest = request.getDestination().trim();
        String lower = dest.toLowerCase(Locale.ROOT);
        int totalDays = Math.max(daysCount, 1);
        double budgetPerDay = Math.max(remainingBudgetForActivities / totalDays, 30.0);

        List<DayPlan> itinerary = new ArrayList<>();
        LocalDate startDate;
        try {
            startDate = LocalDate.parse(request.getStartDate());
        } catch (Exception e) {
            startDate = LocalDate.now().plusDays(14);
        }

        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("EEE, MMM d");

        if (lower.contains("tokyo") || lower.contains("japan")) {
            itinerary = buildTokyoItinerary(startDate, totalDays, budgetPerDay);
        } else if (lower.contains("paris") || lower.contains("france")) {
            itinerary = buildParisItinerary(startDate, totalDays, budgetPerDay);
        } else if (lower.contains("rome") || lower.contains("italy")) {
            itinerary = buildRomeItinerary(startDate, totalDays, budgetPerDay);
        } else if (lower.contains("bali") || lower.contains("indonesia")) {
            itinerary = buildBaliItinerary(startDate, totalDays, budgetPerDay);
        } else if (lower.contains("swiss") || lower.contains("alps") || lower.contains("zurich")) {
            itinerary = buildSwissItinerary(startDate, totalDays, budgetPerDay);
        } else if (lower.contains("dubai") || lower.contains("uae")) {
            itinerary = buildDubaiItinerary(startDate, totalDays, budgetPerDay);
        } else if (lower.contains("new york") || lower.contains("nyc")) {
            itinerary = buildNewYorkItinerary(startDate, totalDays, budgetPerDay);
        } else {
            itinerary = buildDynamicItinerary(dest, startDate, totalDays, budgetPerDay, request.getTravelStyle());
        }

        return itinerary;
    }

    private List<DayPlan> buildTokyoItinerary(LocalDate start, int days, double dailyBudget) {
        List<DayPlan> plans = new ArrayList<>();

        // Day 1
        DayPlan d1 = new DayPlan(1, start.format(DateTimeFormatter.ofPattern("EEE, MMM d")), "Tokyo Arrival, Neon Alleys of Shinjuku & Omoide Yokocho", "Arrival & Cyberpunk Tokyo Nightfall", "Clear skies, 20°C", "Golden Gai bar hopping", 11200);
        d1.addActivity(new ActivityItem("14:30 - 16:30", "Afternoon", "Narita/Haneda Express & Check-in", "Board the high-speed airport express to Central Tokyo and settle into your room at Trunk Hotel.", "Shibuya/Yoyogi", 24.0, "Transit", 120, "Get a Welcome Suica card or Apple Wallet Pasmo on your phone for seamless tap-and-go travel.", "https://images.unsplash.com/photo-1540959733332-eab4deabeeaf?auto=format&fit=crop&w=800&q=80", 4.8, false));
        d1.addActivity(new ActivityItem("17:30 - 19:30", "Evening", "Tokyo Metropolitan Gov Observatory & Shinjuku Neon", "Ascend 202 meters above Tokyo for panoramic sunset vistas overlooking Mount Fuji on a clear twilight.", "Nishi-Shinjuku", 0.0, "Scenic Vista", 90, "Observation deck is free! Visit South Tower at golden hour for the best sunset light.", "https://images.unsplash.com/photo-1503899036084-c55cdd92da26?auto=format&fit=crop&w=800&q=80", 4.9, true));
        d1.addActivity(new ActivityItem("20:00 - 22:30", "Night", "Yakitori & Sake Crawl in Omoide Yokocho (Memory Lane)", "Wind through historic post-war lantern-lit alleys sampling charcoal-grilled tsukune yakitori with chilled draft beer.", "Shinjuku West", 35.0, "Culinary", 150, "Sit at the counter of small 6-seat stalls; order chef's omakase skewers.", "https://images.unsplash.com/photo-1555396273-367ea4eb4db5?auto=format&fit=crop&w=800&q=80", 4.9, true));
        plans.add(d1);

        // Day 2
        if (days >= 2) {
            DayPlan d2 = new DayPlan(2, start.plusDays(1).format(DateTimeFormatter.ofPattern("EEE, MMM d")), "Senso-ji Ancient Heritage, Sumida River & Akihabara Tech Culture", "Old Edo Traditions to Modern Subculture", "Sunny, 22°C", "Senso-ji incense ritual at dawn", 14500);
            d2.addActivity(new ActivityItem("08:30 - 11:00", "Morning", "Senso-ji Temple & Nakamise-dori Street Snacks", "Tokyo's oldest temple, founded in 645 AD. Cleanse at the dragon fountain and taste warm ningyo-yaki cakes.", "Asakusa", 8.0, "Culture", 150, "Arrive before 9:00 AM before tour crowds arrive for serene zen temple photography.", "https://images.unsplash.com/photo-1583084336829-56447d32c5f9?auto=format&fit=crop&w=800&q=80", 4.9, true));
            d2.addActivity(new ActivityItem("12:00 - 14:30", "Afternoon", "Tokyo Skytree Panoramic Lunch & Sumida River Cruise", "Panoramic dining over the Kanto Plain, followed by a water bus cruise down the Sumida River to Odaiba.", "Oshiage / Sumida", 45.0, "Culinary & Scenic", 150, "Book combo ticket online for fast-track elevators to 450m Tembo Galleria.", "https://images.unsplash.com/photo-1536098561742-ca998e48cbcc?auto=format&fit=crop&w=800&q=80", 4.8, false));
            d2.addActivity(new ActivityItem("16:00 - 19:00", "Evening", "Akihabara Electric Town & Retro Gaming Arcades", "Explore multi-level retro arcades like Super Potato and discover the vibrant heart of Japanese anime & tech culture.", "Akihabara", 20.0, "Culture & Fun", 180, "Head to the top floor of Super Potato to play original 1980s Street Fighter arcade cabinets.", "https://images.unsplash.com/photo-1528164344705-475426879c0d?auto=format&fit=crop&w=800&q=80", 4.7, false));
            plans.add(d2);
        }

        // Day 3
        if (days >= 3) {
            DayPlan d3 = new DayPlan(3, start.plusDays(2).format(DateTimeFormatter.ofPattern("EEE, MMM d")), "Meiji Shrine Forest, Harajuku Takeshita & Shibuya Crossing VIP", "Spiritual Canopies & Iconic Urban Energy", "Breezy, 21°C", "Shibuya Sky 360° roof sunset", 16200);
            d3.addActivity(new ActivityItem("09:00 - 11:30", "Morning", "Meiji Jingu Forest Shrine & Ema Wooden Wishes", "Walk under massive cedar torii gates into a tranquil 170-acre evergreen forest in the center of Tokyo.", "Harajuku / Yoyogi", 5.0, "Spiritual & Nature", 150, "Write a wish on an Ema wooden votive tablet under the sacred camphor trees.", "https://images.unsplash.com/photo-1493976040374-85c8e12f0c0e?auto=format&fit=crop&w=800&q=80", 4.9, true));
            d3.addActivity(new ActivityItem("12:00 - 14:30", "Afternoon", "Takeshita Street Crêpes & Omotesando Architecture Walk", "Contrast Harajuku's vibrant youth street fashion with Pritzker-prize winning boutique architecture on Omotesando.", "Harajuku / Omotesando", 25.0, "Fashion & Food", 150, "Stop at Marion Crêpes for their famous strawberry cheesecake rolled crêpe.", "https://images.unsplash.com/photo-1503899036084-c55cdd92da26?auto=format&fit=crop&w=800&q=80", 4.7, false));
            d3.addActivity(new ActivityItem("17:00 - 20:00", "Evening", "Shibuya Sky Observation Deck & Shibuya Crossing", "Ascend to the open-air rooftop deck above Shibuya Scramble Square for the world's most dramatic city view.", "Shibuya Scramble", 22.0, "Scenic Vista", 120, "Book the 17:20 sunset slot 4 weeks in advance for breathtaking day-to-night transformation.", "https://images.unsplash.com/photo-1542051841857-5f90071e7989?auto=format&fit=crop&w=800&q=80", 5.0, true));
            plans.add(d3);
        }

        // Day 4
        if (days >= 4) {
            DayPlan d4 = new DayPlan(4, start.plusDays(3).format(DateTimeFormatter.ofPattern("EEE, MMM d")), "Tsukiji Outer Market Tasting, Ginza Luxury & teamLab Planets", "Sensory Immersion & Culinary Grandeur", "Partly cloudy, 19°C", "teamLab Planets infinite crystal room", 13800);
            d4.addActivity(new ActivityItem("08:00 - 11:00", "Morning", "Tsukiji Outer Market Fresh Seafood Breakfast", "Wander vibrant stalls tasting grilled king crab legs, tamagoyaki sweet rolled omelet, and fresh otoro tuna nigiri.", "Tsukiji Outer Market", 38.0, "Culinary Experience", 180, "Yamacho stall serves piping hot tamagoyaki for just 150 JPY; go hungry!", "https://images.unsplash.com/photo-1534447677768-be436bb09401?auto=format&fit=crop&w=800&q=80", 4.9, true));
            d4.addActivity(new ActivityItem("13:30 - 16:30", "Afternoon", "teamLab Planets Tokyo Immersive Sensory Art", "Wade barefoot through water projected with swimming koi fish and wander through an infinite crystal cosmos.", "Toyosu", 32.0, "Digital Art", 180, "Wear shorts or pants that easily roll up above the knees as you wade in knee-deep water.", "https://images.unsplash.com/photo-1578632767115-351597cf2477?auto=format&fit=crop&w=800&q=80", 5.0, true));
            d4.addActivity(new ActivityItem("18:00 - 21:00", "Evening", "Ginza Highball Bars & Depachika Gourmet Food Basements", "Stroll Tokyo's most elegant boulevard and explore the legendary subterranean food hall at Mitsukoshi Ginza.", "Ginza", 40.0, "Nightlife & Food", 150, "Visit Bar Lupin, founded in 1928, frequented by literary icons Osamu Dazai.", "https://images.unsplash.com/photo-1555396273-367ea4eb4db5?auto=format&fit=crop&w=800&q=80", 4.8, false));
            plans.add(d4);
        }

        // Day 5
        if (days >= 5) {
            DayPlan d5 = new DayPlan(5, start.plusDays(4).format(DateTimeFormatter.ofPattern("EEE, MMM d")), "Day Trip to Mount Fuji & Lake Kawaguchiko Scenic Vista", "Sacred Volcano Landscapes & Traditional Onsen", "Crisp mountain air, 16°C", "Chureito Pagoda postcard view with Mt Fuji", 9800);
            d5.addActivity(new ActivityItem("08:00 - 12:00", "Morning", "Fuji Excursion Scenic Train & Chureito Pagoda", "Board the direct limited express train from Shinjuku to Shimoyoshida and climb 398 steps to the iconic red pagoda.", "Fujiyoshida", 45.0, "Scenic Adventure", 240, "Reserve seat A/D on right side of train for early Fuji views out of Tokyo.", "https://images.unsplash.com/photo-1491555103944-7c647fd857e6?auto=format&fit=crop&w=800&q=80", 5.0, true));
            d5.addActivity(new ActivityItem("13:00 - 16:00", "Afternoon", "Lake Kawaguchiko Boat Cruise & Hoto Noodle Lunch", "Savor Yamanashi's soul food—steaming flat noodles in rich pumpkin miso broth—and cruise Lake Kawaguchiko.", "Lake Kawaguchiko", 30.0, "Culinary & Nature", 180, "Dine at Kosaku Hoto across from Kawaguchiko Station in an authentic tatami hall.", "https://images.unsplash.com/photo-1503899036084-c55cdd92da26?auto=format&fit=crop&w=800&q=80", 4.9, false));
            d5.addActivity(new ActivityItem("18:30 - 21:30", "Evening", "Roppongi Hills Mori Art Museum & Tokyo Tower Night Illumination", "Return to Tokyo and conclude your day admiring modern art and the glittering orange glow of Tokyo Tower.", "Roppongi", 25.0, "Art & Skyline", 180, "The Mori Art Museum stays open until 22:00 most nights—perfect for after-dinner culture.", "https://images.unsplash.com/photo-1540959733332-eab4deabeeaf?auto=format&fit=crop&w=800&q=80", 4.8, false));
            plans.add(d5);
        }

        // For more days, extrapolate cleanly
        for (int d = 6; d <= days; d++) {
            plans.add(buildGenericDay(d, start.plusDays(d - 1), "Tokyo", dailyBudget));
        }

        return plans;
    }

    private List<DayPlan> buildParisItinerary(LocalDate start, int days, double dailyBudget) {
        List<DayPlan> plans = new ArrayList<>();
        DayPlan d1 = new DayPlan(1, start.format(DateTimeFormatter.ofPattern("EEE, MMM d")), "Parisian Welcome: Seine River Sunset Cruise & Latin Quarter Bistro", "Romance, Cobblestone History & Wine", "Pleasant, 19°C", "Eiffel Tower glittering at the top of the hour", 12500);
        d1.addActivity(new ActivityItem("15:00 - 17:30", "Afternoon", "Check-in at Hôtel Madame Rêve & Jardin du Palais-Royal", "Settle in, then stroll the striped Buren columns and peaceful lime-tree allées of Palais-Royal.", "1st Arrondissement", 0.0, "Culture & Stroll", 150, "Grab a café crème at Café Kitsuné inside the garden colonnade.", "https://images.unsplash.com/photo-1502602898657-3e91760cbb34?auto=format&fit=crop&w=800&q=80", 4.9, false));
        d1.addActivity(new ActivityItem("18:30 - 20:00", "Evening", "Vedettes du Pont Neuf Sunset Seine Cruise", "Gliding past Notre-Dame, the Musée d'Orsay, and the Eiffel Tower as golden hour illuminates the riverbanks.", "Pont Neuf", 18.0, "Scenic Boat", 90, "Board 30 minutes before departure to secure open-air upper deck bow seats.", "https://images.unsplash.com/photo-1499856871958-5b9627545d1a?auto=format&fit=crop&w=800&q=80", 4.8, true));
        d1.addActivity(new ActivityItem("20:30 - 23:00", "Night", "Classic French Bistro Dinner in Saint-Germain-des-Prés", "Indulge in French onion soup, steak frites, duck confit, and chocolate mousse with Burgundy Pinot Noir.", "Saint-Germain", 48.0, "Gastronomy", 150, "Reserve at Le Comptoir du Relais or Bistro Paul Bert well in advance.", "https://images.unsplash.com/photo-1555396273-367ea4eb4db5?auto=format&fit=crop&w=800&q=80", 4.9, true));
        plans.add(d1);

        if (days >= 2) {
            DayPlan d2 = new DayPlan(2, start.plusDays(1).format(DateTimeFormatter.ofPattern("EEE, MMM d")), "Louvre Masterpieces, Tuileries Gardens & Montmartre Artists", "High Art & Bohemian Hilltop Charm", "Mild, 21°C", "Sacré-Cœur sunset overlooking all of Paris", 15200);
            d2.addActivity(new ActivityItem("09:00 - 12:30", "Morning", "Musée du Louvre Priority Entry & Masterpieces Tour", "Mona Lisa, Winged Victory of Samothrace, Venus de Milo, and Napoleon's Apartments.", "1st Arrondissement", 22.0, "Art & Heritage", 210, "Enter through the Porte des Lions entrance to bypass the long pyramid courtyard lines.", "https://images.unsplash.com/photo-1499856871958-5b9627545d1a?auto=format&fit=crop&w=800&q=80", 4.9, true));
            d2.addActivity(new ActivityItem("13:00 - 15:30", "Afternoon", "Tuileries Garden Stroll & Angelina Hot Chocolate", "Walk past marble statues and water fountains, stopping for decadent thick African hot chocolate and Mont-Blanc.", "Tuileries / Rue de Rivoli", 28.0, "Culinary Indulgence", 150, "Order takeaway from Angelina's boutique to skip the 45-minute salon queue.", "https://images.unsplash.com/photo-1509042239860-f550ce710b93?auto=format&fit=crop&w=800&q=80", 4.7, false));
            d2.addActivity(new ActivityItem("17:00 - 20:30", "Evening", "Montmartre Cobblestones, Place du Tertre & Sacré-Cœur Sunset", "Climb the hill of artists to Sacré-Cœur Basilica to watch street musicians as Paris twinkles below.", "Montmartre", 0.0, "Bohemian Culture", 210, "Explore Rue de l'Abreuvoir—considered the prettiest street in Paris—past La Maison Rose.", "https://images.unsplash.com/photo-1502602898657-3e91760cbb34?auto=format&fit=crop&w=800&q=80", 5.0, true));
            plans.add(d2);
        }

        for (int d = 3; d <= days; d++) {
            plans.add(buildGenericDay(d, start.plusDays(d - 1), "Paris", dailyBudget));
        }
        return plans;
    }

    private List<DayPlan> buildRomeItinerary(LocalDate start, int days, double dailyBudget) {
        List<DayPlan> plans = new ArrayList<>();
        DayPlan d1 = new DayPlan(1, start.format(DateTimeFormatter.ofPattern("EEE, MMM d")), "The Colosseum, Roman Forum & Trastevere Carbonara", "Imperial Glory & Evening Piazza Life", "Warm Mediterranean sun, 24°C", "Colosseum gladiator arena floor walk", 15600);
        d1.addActivity(new ActivityItem("09:00 - 12:30", "Morning", "Colosseum & Roman Forum VIP Underground Access", "Walk where gladiators clashed and walk the Via Sacra where Julius Caesar's triumphs were celebrated.", "Piazza del Colosseo", 34.0, "Ancient History", 210, "Book the arena floor ticket for an unobstructed 360-degree gladiator perspective.", "https://images.unsplash.com/photo-1552832230-c0197dd311b5?auto=format&fit=crop&w=800&q=80", 5.0, true));
        d1.addActivity(new ActivityItem("13:30 - 15:30", "Afternoon", "Palatine Hill Gardens & Piazza Venezia", "Admire emperor palaces overlooking the Circus Maximus, followed by espresso by the Capitoline Hill.", "Palatine Hill", 0.0, "Panoramic History", 120, "Look through the Keyhole of the Knights of Malta on the Aventine Hill nearby for a secret vista.", "https://images.unsplash.com/photo-1515542622106-78bda8ba0e5b?auto=format&fit=crop&w=800&q=80", 4.8, false));
        d1.addActivity(new ActivityItem("18:30 - 22:00", "Evening", "Trastevere Cobblestone Walk & Authentic Cacio e Pepe", "Cross the Tiber river into Rome's most charming quarter for hand-rolled tonnarelli and chilled Frascati wine.", "Trastevere", 36.0, "Food & Wine", 210, "Dine at Da Enzo al 29; arrive 20 minutes before opening time.", "https://images.unsplash.com/photo-1555396273-367ea4eb4db5?auto=format&fit=crop&w=800&q=80", 4.9, true));
        plans.add(d1);

        for (int d = 2; d <= days; d++) {
            plans.add(buildGenericDay(d, start.plusDays(d - 1), "Rome", dailyBudget));
        }
        return plans;
    }

    private List<DayPlan> buildBaliItinerary(LocalDate start, int days, double dailyBudget) {
        List<DayPlan> plans = new ArrayList<>();
        DayPlan d1 = new DayPlan(1, start.format(DateTimeFormatter.ofPattern("EEE, MMM d")), "Ubud Sacred Monkey Forest, Rice Terraces & Balinese Fire Dance", "Tropical Spirituality & Green Vistas", "Tropical breeze, 28°C", "Tegallalang emerald rice terrace swings", 10200);
        d1.addActivity(new ActivityItem("09:00 - 11:30", "Morning", "Sacred Monkey Forest Sanctuary & Ancient Banyan Trees", "Stroll through a sacred nutmeg forest inhabited by over 1,000 playful Balinese long-tailed macaques.", "Padangtegal, Ubud", 6.0, "Wildlife & Nature", 150, "Do not bring loose sunglasses or plastic bottles; keep personal items zipped.", "https://images.unsplash.com/photo-1537996194471-e657df975ab4?auto=format&fit=crop&w=800&q=80", 4.8, true));
        d1.addActivity(new ActivityItem("12:30 - 15:30", "Afternoon", "Tegallalang Emerald Rice Terraces & Jungle Swing", "Descend into cascading UNESCO-recognized subak irrigation terraces and soar above palm canopies.", "Tegallalang", 15.0, "Adventure & Scenic", 180, "Hire a local farmer guide to explore lower tiers away from main selfie points.", "https://images.unsplash.com/photo-1518548419970-58e3b4079ab2?auto=format&fit=crop&w=800&q=80", 4.9, true));
        d1.addActivity(new ActivityItem("18:30 - 21:00", "Evening", "Ubud Palace Kecak & Fire Dance Performance", "Mesmerizing polyrhythmic chanting of 50 shirtless men telling the Ramayana epic surrounded by torches.", "Ubud Palace", 10.0, "Balinese Culture", 150, "Arrive at 18:45 to get front row seats directly in front of the stone temple gate.", "https://images.unsplash.com/photo-1555396273-367ea4eb4db5?auto=format&fit=crop&w=800&q=80", 4.9, true));
        plans.add(d1);

        for (int d = 2; d <= days; d++) {
            plans.add(buildGenericDay(d, start.plusDays(d - 1), "Bali", dailyBudget));
        }
        return plans;
    }

    private List<DayPlan> buildSwissItinerary(LocalDate start, int days, double dailyBudget) {
        List<DayPlan> plans = new ArrayList<>();
        DayPlan d1 = new DayPlan(1, start.format(DateTimeFormatter.ofPattern("EEE, MMM d")), "Glacier Express Scenic Journey & Alpine Cheese Fondue", "Alpine Grandeur & Crisp Mountain Air", "Crisp Alpine sun, 14°C", "Matterhorn reflection in Stellisee lake", 11800);
        d1.addActivity(new ActivityItem("09:00 - 12:30", "Morning", "Gornergrat Cogwheel Mountain Railway to 3,089m", "Europe's highest open-air cogwheel train providing majestic panoramas of 29 peaks exceeding 4,000 meters.", "Zermatt / Gornergrat", 55.0, "Alpine Mountain Rail", 210, "Sit on the right side of the train for continuous Matterhorn viewpoints during ascent.", "https://images.unsplash.com/photo-1530122037265-a5f1f91d3b99?auto=format&fit=crop&w=800&q=80", 5.0, true));
        d1.addActivity(new ActivityItem("13:30 - 16:30", "Afternoon", "Hike to Stellisee & Mirror Matterhorn Reflection", "Gentle alpine meadow hike surrounded by marmots and edelweiss flowers.", "Riffelsee / Stellisee", 0.0, "Nature Hike", 180, "Visit when the lake surface is calm early afternoon for the mirror reflection.", "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?auto=format&fit=crop&w=800&q=80", 4.9, true));
        d1.addActivity(new ActivityItem("18:30 - 21:30", "Evening", "Traditional Valais Cheese Fondue & Raclette by the Fireplace", "Melted Gruyère and Emmental cheeses served with crusty sourdough bread, cornichons, and crisp Fendant wine.", "Zermatt Village", 42.0, "Alpine Gastronomy", 180, "Order half-and-half (Moitié-Moitié) fondue with kirsch schnapps for authentic flavor.", "https://images.unsplash.com/photo-1555396273-367ea4eb4db5?auto=format&fit=crop&w=800&q=80", 4.9, false));
        plans.add(d1);

        for (int d = 2; d <= days; d++) {
            plans.add(buildGenericDay(d, start.plusDays(d - 1), "Swiss Alps", dailyBudget));
        }
        return plans;
    }

    private List<DayPlan> buildDubaiItinerary(LocalDate start, int days, double dailyBudget) {
        List<DayPlan> plans = new ArrayList<>();
        DayPlan d1 = new DayPlan(1, start.format(DateTimeFormatter.ofPattern("EEE, MMM d")), "Burj Khalifa Cloud View, Dubai Mall & Desert Sunset Safari", "Futuristic Architecture to Golden Dunes", "Clear skies, 31°C", "Sunset dune bashing in the Arabian Desert", 8900);
        d1.addActivity(new ActivityItem("09:30 - 12:00", "Morning", "Burj Khalifa At The Top (124th & 125th Floor)", "Soar 456 meters above the desert metropolis in high-speed double-decker elevators.", "Downtown Dubai", 46.0, "Iconic Skyscraper", 150, "Skip queues by booking morning time slot before noon crowds.", "https://images.unsplash.com/photo-1512453979798-5ea266f8880c?auto=format&fit=crop&w=800&q=80", 4.8, true));
        d1.addActivity(new ActivityItem("15:00 - 21:00", "Afternoon & Night", "Red Dunes Desert Safari, Camel Trek & Bedouin BBQ Camp", "4x4 dune bashing, sandboarding down velvet red dunes, falconry, and Arabian barbecue under starlit skies.", "Lahbab Desert", 65.0, "Desert Adventure", 360, "Opt for an open-top vintage Land Rover for a more authentic heritage experience.", "https://images.unsplash.com/photo-1451337516015-6b6e9a44a8a3?auto=format&fit=crop&w=800&q=80", 4.9, true));
        plans.add(d1);

        for (int d = 2; d <= days; d++) {
            plans.add(buildGenericDay(d, start.plusDays(d - 1), "Dubai", dailyBudget));
        }
        return plans;
    }

    private List<DayPlan> buildNewYorkItinerary(LocalDate start, int days, double dailyBudget) {
        List<DayPlan> plans = new ArrayList<>();
        DayPlan d1 = new DayPlan(1, start.format(DateTimeFormatter.ofPattern("EEE, MMM d")), "Central Park Rowboats, Museum Mile & Broadway Night", "The Soul of Manhattan", "Crisp breeze, 20°C", "Broadway orchestra performance", 14900);
        d1.addActivity(new ActivityItem("09:00 - 12:00", "Morning", "Central Park Ramble & Bow Bridge Rowboats", "Walk tree-lined pathways to Bethesda Fountain, then rent a vintage rowboat at the Loeb Boathouse.", "Central Park", 25.0, "Iconic Park", 180, "Row beneath Bow Bridge for quintessential New York cinematic photos.", "https://images.unsplash.com/photo-1538688525198-9b88f6f53126?auto=format&fit=crop&w=800&q=80", 4.8, true));
        d1.addActivity(new ActivityItem("13:00 - 16:00", "Afternoon", "The Metropolitan Museum of Art (The Met)", "Discover Egyptian temples, European masterpieces, and panoramic rooftop garden cocktails.", "Upper East Side", 30.0, "World-Class Museum", 180, "The Temple of Dendur wing in the afternoon sunlight is magical.", "https://images.unsplash.com/photo-1582555172866-f73bb12a2ab3?auto=format&fit=crop&w=800&q=80", 4.9, true));
        d1.addActivity(new ActivityItem("19:00 - 22:30", "Night", "Broadway Musical Performance & Times Square Lights", "Experience an award-winning musical in the Theater District followed by midnight pizza at Joe's.", "Times Square / Broadway", 85.0, "Theater & Arts", 210, "Visit the TKTS booth under the red steps in Duffy Square for same-day discount tickets.", "https://images.unsplash.com/photo-1508997449629-303059a039c0?auto=format&fit=crop&w=800&q=80", 5.0, true));
        plans.add(d1);

        for (int d = 2; d <= days; d++) {
            plans.add(buildGenericDay(d, start.plusDays(d - 1), "New York", dailyBudget));
        }
        return plans;
    }

    private List<DayPlan> buildDynamicItinerary(String destination, LocalDate start, int days, double dailyBudget, String style) {
        List<DayPlan> plans = new ArrayList<>();
        for (int d = 1; d <= days; d++) {
            plans.add(buildGenericDay(d, start.plusDays(d - 1), destination, dailyBudget));
        }
        return plans;
    }

    private DayPlan buildGenericDay(int dayNum, LocalDate date, String destination, double dailyBudget) {
        String dateStr = date.format(DateTimeFormatter.ofPattern("EEE, MMM d"));
        String[] themes = {
                "Historic Old Quarter, Architectural Landmarks & Artisan Markets",
                "Culinary Trails, Local Street Eats & Scenic Waterfront Panoramas",
                "Hidden Gems, Cultural Immersion & Sunset Rooftop Vistas",
                "Nature Escapes, Botanical Sanctuaries & Twilight Music",
                "Artisan Workshops, Flea Markets & Michelin-Guided Delicacies",
                "Scenic Coastal / Mountain Excursion & Village Traditions",
                "Farewell Highlights, Souvenir Curation & Celebratory Feast"
        };
        String theme = themes[(dayNum - 1) % themes.length];

        DayPlan plan = new DayPlan(dayNum, dateStr, String.format("Day %d: Exploring %s", dayNum, destination), theme, "Sunny, 22°C", "Spectacular sunset view & local dining", 12000 + (dayNum * 400));

        double act1Cost = Math.round(dailyBudget * 0.25 * 100.0) / 100.0;
        double act2Cost = Math.round(dailyBudget * 0.40 * 100.0) / 100.0;
        double act3Cost = Math.round(dailyBudget * 0.35 * 100.0) / 100.0;

        plan.addActivity(new ActivityItem("09:00 - 11:30", "Morning",
                String.format("Historic Landmarks & Old Quarter of %s", destination),
                String.format("Immerse yourself in the architectural highlights and morning atmosphere of %s.", destination),
                "Central Historic District", act1Cost, "Culture & Heritage", 150,
                "Start early to beat tour groups; bring comfortable walking shoes.",
                "https://images.unsplash.com/photo-1488646953014-85cb44e25828?auto=format&fit=crop&w=800&q=80", 4.8, true));

        plan.addActivity(new ActivityItem("13:00 - 16:00", "Afternoon",
                String.format("Curated Museum & Artisan Tasting in %s", destination),
                String.format("Discover the premier cultural institution and taste regional specialties.", destination),
                "Cultural Arts Quarter", act2Cost, "Arts & Gastronomy", 180,
                "Ask for the chef's special regional sampler menu.",
                "https://images.unsplash.com/photo-1555396273-367ea4eb4db5?auto=format&fit=crop&w=800&q=80", 4.9, true));

        plan.addActivity(new ActivityItem("18:30 - 21:30", "Evening",
                String.format("Golden Hour Vista & Celebrated Local Dining in %s", destination),
                String.format("Unwind with sunset panoramic views followed by an unforgettable dinner.", destination),
                "Waterfront / Skyline District", act3Cost, "Scenic & Culinary", 180,
                "Reserve a window table 30 minutes before twilight for the best lighting.",
                "https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?auto=format&fit=crop&w=800&q=80", 4.9, false));

        return plan;
    }

    public AgentMessage createProposalMessage(List<DayPlan> plans, TripRequest request) {
        int totalActivities = plans.stream().mapToInt(p -> p.getActivities().size()).sum();
        double totalCost = plans.stream().mapToDouble(DayPlan::getDailyEstimatedSpend).sum();
        String text = String.format(
                "🎭 Curated complete %d-day master itinerary for %s: %d immersive experiences (Culture, Cuisine, Panoramic Vistas). Total estimated activities budget: $%s. Fully synced with transit routes and pace preferences!",
                plans.size(),
                request.getDestination(),
                totalActivities,
                String.format("%.2f", totalCost)
        );
        return new AgentMessage(getName(), getRole(), "Atlas & Auditor", text, "ACTIVITY_CURATION", "success");
    }
}
