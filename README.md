# 🧞‍♂️ TripGenie AI — Autonomous Multi-Agent Travel Planner

> **Your Wish is Our Itinerary.**  
> An intelligent vacation architect powered by **Java 24**, **Spring Boot 3.4**, and a cooperative **Multi-Agent AI Swarm**.

---

## 🌟 What is TripGenie AI? (In Simple Words)

Most travel websites and chatbots make you do all the hard work, or they spit out generic recommendations that don't respect your budget.

**TripGenie AI** works differently. Instead of relying on a single overwhelmed chatbot, it deploys a **specialized team of 5 autonomous AI agents** that work together in real-time. 

Think of it like hiring a personal dream team:
- One agent finds the best flights.
- Another hand-picks boutique hotels in walkable neighborhoods.
- A third plans your daily activities hour-by-hour.
- A fourth accountant agent watches every single dollar to ensure you never exceed your budget.
- A lead orchestrator coordinates the team, makes sure everyone agrees, and presents you with a finalized, verified **Trip Dossier**.

You can watch them converse and negotiate live on an interactive radar screen, listen to real-time audio chimes, and explore a comprehensive vacation plan with boarding passes, hotel vouchers, day-by-day itineraries, interactive packing lists, and budget breakdowns.

---

## 👥 Meet Your 5 AI Agents (The Team)

| Agent | Name | Role | What They Do |
| :--- | :--- | :--- | :--- |
| 🌟 | **Atlas** | *Lead Orchestrator* | Scopes your trip, delegates tasks to the swarm, resolves conflicts, and signs off on the master dossier. |
| ✈️ | **AeroStream** | *Flight Specialist* | Scans flight routes, selects reputable airlines (ANA, Air France, Singapore Airlines, etc.), and optimizes fare classes. |
| 🏨 | **Sanctuary** | *Accommodations Specialist* | Curates boutique hotels and luxury enclaves matching your travel style, checking metro proximity and neighborhood vibes. |
| 🎭 | **Curator** | *Experiences & Culture Scout* | Crafts timed daily schedules (Morning, Afternoon, Evening) with landmark visits, culinary stops, and hidden gems. |
| ⚖️ | **Auditor** | *Budget & Tradeoff Analyst* | Audits every dollar, locks in an untouchable **emergency buffer**, and forces other agents to renegotiate if costs get too high. |

---

## ⚙️ How It Works (Step-by-Step)

```
[ 1. User Input ] ➔ Destination, Dates, Budget & Travel Style
       │
       ▼
[ 2. Backend Dispatch ] ➔ Asynchronous Background Swarm Initialized (Java 24)
       │
       ▼
[ 3. Live Streaming ] ➔ Server-Sent Events (SSE) push agent dialogue to browser
       │
       ▼
[ 4. Negotiation Round ] ➔ If flights + hotel > 75% of budget, agents adjust room tiers
       │
       ▼
[ 5. Fiscal Audit ] ➔ Line-item verification + Emergency reserve secured
       │
       ▼
[ 6. Master Dossier ] ➔ Complete day plans, tickets, packing list & interactive charts
       │
       ▼
[ 7. Copilot Tweaks ] ➔ Chat directly with Atlas to refine food, pace, or activities
```

1. **You enter your trip details:** Destination (e.g., *Tokyo*, *Paris*, *Bali*), budget cap, dates, and preferred travel style (*Balanced Explorer*, *Luxury Nomad*, *Gastronomic Immersion*, etc.).
2. **The Swarm dispatches:** The backend launches an asynchronous session via a background thread pool.
3. **Live SSE Stream:** Your browser connects to a real-time event stream (`/api/trip/stream/{id}`). You watch the radar light up with laser beams and read the agents' live dialogue in the terminal feed.
4. **Automatic Inter-Agent Tradeoffs:** If accommodations and flights eat up too much budget, Sanctuary and AeroStream automatically renegotiate room categories to protect Curator's experiential budget.
5. **Final Dossier Handover:** Atlas verifies all details and delivers a polished trip plan with interactive tabs.
6. **Conversational Copilot:** Want more street food or a slower wellness pace? You can ask Atlas directly to tweak your plan on the fly.

---

## 💻 Tech Stack

### **Backend**
- **Java 24:** Modern LTS/latest Java runtime with clean OOP abstractions.
- **Spring Boot 3.4.3:** High-performance REST web layer (`spring-boot-starter-web`).
- **Server-Sent Events (`SseEmitter`):** Real-time, low-latency streaming of agent messages to the client without the overhead of WebSockets.
- **Jackson:** JSON serialization for models and streaming payloads.
- **Dual-Mode AI Engine (`AstraLLMService`):**
  - **Live LLM Calls:** Native HTTP client integration for OpenAI / GPT-6 Astra endpoints.
  - **Autonomous Heuristic Fallback:** Built-in intelligence that runs seamlessly even without an internet connection or API key.

### **Frontend**
- **Vanilla Modern Web (Zero Framework Overhead):** Clean HTML5, modular CSS3, and ES6+ JavaScript.
- **Ocean & Sunset Glassmorphism:** Custom CSS design system with HSL tokens and dark-mode aesthetics.
- **Interactive SVG Radar HUD:** Dynamic laser beam animations connecting active agents.
- **Web Audio API:** In-browser audio synthesizer generating futuristic chimes during agent handoffs.
- **Dynamic Data Visualizations:** Native SVG Donut Chart for budget allocations.
- **Smart Travel Utilities:** Live world timezone clocks, instant multi-currency converter, and printable PDF export.

---

## 📋 Prerequisites

Before running the project, ensure you have the following installed on your computer:

1. **Java Development Kit (JDK) 24** (or JDK 21+):
   - Verify by running:
     ```bash
     java -version
     ```
2. **Apache Maven 3.9+**:
   - Verify by running:
     ```bash
     mvn -v
     ```
3. **A Modern Web Browser:** Chrome, Edge, Firefox, Brave, or Safari.

---

## 🚀 How to Run the Project (Quick Start)

### **Step 1: Open Your Terminal**
Navigate to the project folder:
```bash
cd "c:\Users\MEHAK\OneDrive\Desktop\AI travel Agent"
```

---

### **Step 2: (Optional) Add an OpenAI API Key**
The project comes with a **built-in high-intelligence autonomous engine**, so it works right out of the box with **zero configuration**!

If you want live responses from OpenAI/GPT-6, you can optionally set an environment variable:
- **Windows (PowerShell):**
  ```powershell
  $env:OPENAI_API_KEY="your_actual_api_key_here"
  ```
- **Windows (CMD):**
  ```cmd
  set OPENAI_API_KEY=your_actual_api_key_here
  ```
*(You can also configure or paste your API key anytime directly inside the web UI under the ⚙️ Settings button).*

---

### **Step 3: Build and Run with Maven**
Run the Spring Boot application:
```bash
mvn clean spring-boot:run
```

You will see the Spring Boot banner and the application will start on port `8080`:
```text
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.4.3)

... Tomcat started on port 8080 (http) with context path '/'
... Started TravelPlannerApplication in 2.1 seconds
```

---

### **Step 4: Open in Your Browser**
Open your web browser and go to:
👉 **[http://localhost:8080](http://localhost:8080)**

---

### **Step 5: Plan Your First Trip!**
1. Pick a popular destination like **Tokyo 🇯🇵**, **Paris 🇫🇷**, **Bali 🇮🇩**, or type in your own custom destination.
2. Select your budget (e.g., `$2,400`) and travel dates.
3. Choose your travel style (*Balanced Explorer*, *Luxury Nomad*, *Gastronomic Immersion*, etc.).
4. Click **INITIALIZE MULTI-AGENT SWARM**.
5. Watch the live agent radar, read the agent chatter, and explore your finished trip dossier!

---

## 📂 Project Structure

```text
AI travel Agent/
├── pom.xml                               # Maven project configuration (Java 24, Spring Boot 3.4.3)
├── README.md                             # Project overview and guide (this file)
└── src/
    └── main/
        ├── java/com/astratravel/
        │   ├── TravelPlannerApplication.java   # Main Spring Boot entry point
        │   ├── agent/                          # The 5 Autonomous AI Agents
        │   │   ├── Agent.java                  # Shared agent interface
        │   │   ├── AtlasOrchestratorAgent.java # Master lead orchestrator
        │   │   ├── FlightAgent.java            # AeroStream - flight routes & fares
        │   │   ├── StayAgent.java              # Sanctuary - hotels & neighborhood vibes
        │   │   ├── ActivityAgent.java          # Curator - daily itinerary & experiences
        │   │   └── BudgetAuditorAgent.java     # Auditor - budget integrity & balance
        │   ├── controller/
        │   │   └── TravelController.java       # REST endpoints & SSE streaming handler
        │   ├── model/                          # Domain data structures
        │   │   ├── TripRequest.java            # User inputs (budget, dates, style)
        │   │   ├── TripDossier.java            # Complete synthesized vacation package
        │   │   ├── DayPlan.java                # Single day itinerary breakdown
        │   │   ├── ActivityItem.java           # Timed activity cards
        │   │   ├── FlightOption.java           # Boarding pass specs
        │   │   ├── StayOption.java             # Hotel & amenities specs
        │   │   ├── BudgetBreakdown.java        # Categorical spending audit
        │   │   └── ModelConfig.java            # AI model & API key configuration
        │   └── service/
        │       ├── MultiAgentOrchestrationService.java # Swarm lifecycle & SSE dispatcher
        │       └── AstraLLMService.java                # LLM client + Heuristic fallback engine
        └── resources/
            ├── application.properties          # Server port (8080) & static path settings
            └── static/                         # High-performance frontend
                ├── index.html                  # Semantic UI layout & modals
                ├── styles.css                  # Ocean & sunset glassmorphic styling
                └── app.js                      # SSE listener, SVG charts, Audio synth & reactive UI
```

---

## 🛠️ Handy Tips & Features to Try

- **Inspect an Agent:** Click any agent chip in the top navigation or on the radar nodes to open their **Agent Intelligence Profile** and view their background, role, and decision rules.
- **Toggle Ambient Audio:** Click the 🔊 button in the top right to turn ambient sci-fi chimes on or off.
- **Try the Copilot Tweak:** After generating a plan, go to the **Copilot Tweak** tab and click *"More foodie street food"* or type a custom request to see Atlas adapt the plan instantly.
- **Export or Print:** Click **Print / Export Dossier PDF** or **Export Dossier JSON** at the bottom of your dossier to save your itinerary offline.
- **World Clocks & Currency Calculator:** Scroll to the footer to check live time zones across major travel hubs and convert spending values instantly.

---

## Publicly Accessible Link : https://tripgenie-ai-qe5e.onrender.com/        

## 📜 License
This project is open-source and built for educational, portfolio, and personal travel planning use. Happy travels! ✈️🌍
