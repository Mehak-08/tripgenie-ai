/**
 * ASTRA TRAVEL OS - AUTONOMOUS MULTI-AGENT CLIENT ENGINE (GPT-6 ASTRA)
 */

(function () {
  'use strict';

  // State
  const state = {
    sessionId: null,
    dossier: null,
    sse: null,
    soundEnabled: true,
    audioCtx: null,
    activeDayIndex: 0,
    activeTab: 'tab-itinerary'
  };

  // DOM Elements
  const el = {
    // Form & Inputs
    tripForm: document.getElementById('tripForm'),
    destinationInput: document.getElementById('destinationInput'),
    originInput: document.getElementById('originInput'),
    budgetInput: document.getElementById('budgetInput'),
    currencySelect: document.getElementById('currencySelect'),
    startDateInput: document.getElementById('startDateInput'),
    endDateInput: document.getElementById('endDateInput'),
    travelersInput: document.getElementById('travelersInput'),
    styleInput: document.getElementById('styleInput'),
    tripDurationBadge: document.getElementById('tripDurationBadge'),
    launchBtn: document.getElementById('launchBtn'),

    // Sections
    heroSection: document.getElementById('heroSection'),
    agentCommandSection: document.getElementById('agentCommandSection'),
    dossierSection: document.getElementById('dossierSection'),

    // Radar & Nodes
    deckPhaseBadge: document.getElementById('deckPhaseBadge'),
    progressStatusText: document.getElementById('progressStatusText'),
    progressPercent: document.getElementById('progressPercent'),
    progressFill: document.getElementById('progressFill'),
    terminalFeed: document.getElementById('terminalFeed'),
    hudSyncVal: document.getElementById('hudSyncVal'),

    // Radar Nodes & Beams
    nodeOrchestrator: document.getElementById('nodeOrchestrator'),
    nodeFlight: document.getElementById('nodeFlight'),
    nodeStay: document.getElementById('nodeStay'),
    nodeActivity: document.getElementById('nodeActivity'),
    nodeBudget: document.getElementById('nodeBudget'),
    beamFlight: document.getElementById('beamFlight'),
    beamStay: document.getElementById('beamStay'),
    beamActivity: document.getElementById('beamActivity'),
    beamBudget: document.getElementById('beamBudget'),

    // Header roster chips
    agentRoster: document.getElementById('agentRoster'),
    soundToggleBtn: document.getElementById('soundToggleBtn'),
    soundIcon: document.getElementById('soundIcon'),
    settingsBtn: document.getElementById('settingsBtn'),

    // Dossier Hero Elements
    dossierHeroBg: document.getElementById('dossierHeroBg'),
    dossierCountryBadge: document.getElementById('dossierCountryBadge'),
    dossierDestName: document.getElementById('dossierDestName'),
    dossierTagline: document.getElementById('dossierTagline'),
    dossierSummaryText: document.getElementById('dossierSummaryText'),
    metricTotalSpend: document.getElementById('metricTotalSpend'),
    metricSavings: document.getElementById('metricSavings'),
    metricDuration: document.getElementById('metricDuration'),
    metricDates: document.getElementById('metricDates'),
    metricWeather: document.getElementById('metricWeather'),
    metricBuffer: document.getElementById('metricBuffer'),

    // Dossier Tabs & Containers
    dayPillsBar: document.getElementById('dayPillsBar'),
    dayContainer: document.getElementById('dayContainer'),
    flightsContainer: document.getElementById('flightsContainer'),
    hotelContainer: document.getElementById('hotelContainer'),
    budgetDonutSvg: document.getElementById('budgetDonutSvg'),
    donutTotalVal: document.getElementById('donutTotalVal'),
    donutLegend: document.getElementById('donutLegend'),
    budgetTableBody: document.getElementById('budgetTableBody'),
    auditNotesList: document.getElementById('auditNotesList'),
    packingChecklist: document.getElementById('packingChecklist'),
    insiderHacksList: document.getElementById('insiderHacksList'),
    specCurrency: document.getElementById('specCurrency'),
    specPower: document.getElementById('specPower'),
    specEmergency: document.getElementById('specEmergency'),

    // Tweak elements
    tweakTextInput: document.getElementById('tweakTextInput'),
    sendTweakBtn: document.getElementById('sendTweakBtn'),
    tweakFeedback: document.getElementById('tweakFeedback'),

    // Global Actions
    printBtn: document.getElementById('printBtn'),
    exportJsonBtn: document.getElementById('exportJsonBtn'),
    planAnotherBtn: document.getElementById('planAnotherBtn'),

    // Modal
    settingsModal: document.getElementById('settingsModal'),
    closeSettingsBtn: document.getElementById('closeSettingsBtn'),
    cancelSettingsBtn: document.getElementById('cancelSettingsBtn'),
    saveSettingsBtn: document.getElementById('saveSettingsBtn'),
    modelSelect: document.getElementById('modelSelect'),
    apiKeyInput: document.getElementById('apiKeyInput'),
    endpointInput: document.getElementById('endpointInput'),
    fallbackToggle: document.getElementById('fallbackToggle')
  };

  // Initialize
  function init() {
    setupDefaultDates();
    bindEvents();
    loadServerConfig();
    initWorldClocks();
    initCurrencyConverter();
    initFooterUtilities();
    initAgentProfileModal();
  }

  // Set default dates: 14 days from now, lasting 5 days
  function setupDefaultDates() {
    const today = new Date();
    const depart = new Date(today);
    depart.setDate(today.getDate() + 14);

    const ret = new Date(depart);
    ret.setDate(depart.getDate() + 5);

    el.startDateInput.value = depart.toISOString().split('T')[0];
    el.endDateInput.value = ret.toISOString().split('T')[0];
    updateDurationBadge();
  }

  function updateDurationBadge() {
    const d1 = new Date(el.startDateInput.value);
    const d2 = new Date(el.endDateInput.value);
    if (!isNaN(d1) && !isNaN(d2) && d2 > d1) {
      const nights = Math.round((d2 - d1) / (1000 * 60 * 60 * 24));
      el.tripDurationBadge.textContent = `${nights} Nights / ${nights + 1} Days`;
    } else {
      el.tripDurationBadge.textContent = 'Select valid dates';
    }
  }

  // Web Audio Synthesizer for ambient sci-fi chimes
  function playTone(freq = 520, type = 'sine', duration = 0.18, gainVal = 0.08) {
    if (!state.soundEnabled) return;
    try {
      if (!state.audioCtx) {
        state.audioCtx = new (window.AudioContext || window.webkitAudioContext)();
      }
      if (state.audioCtx.state === 'suspended') {
        state.audioCtx.resume();
      }
      const osc = state.audioCtx.createOscillator();
      const gain = state.audioCtx.createGain();
      osc.type = type;
      osc.frequency.setValueAtTime(freq, state.audioCtx.currentTime);
      gain.gain.setValueAtTime(gainVal, state.audioCtx.currentTime);
      gain.gain.exponentialRampToValueAtTime(0.001, state.audioCtx.currentTime + duration);
      osc.connect(gain);
      gain.connect(state.audioCtx.destination);
      osc.start();
      osc.stop(state.audioCtx.currentTime + duration);
    } catch (e) {
      // Audio context policy fallback
    }
  }

  function playVictoryChime() {
    if (!state.soundEnabled) return;
    setTimeout(() => playTone(440, 'triangle', 0.2, 0.1), 0);
    setTimeout(() => playTone(554, 'triangle', 0.2, 0.1), 120);
    setTimeout(() => playTone(659, 'triangle', 0.25, 0.1), 240);
    setTimeout(() => playTone(880, 'triangle', 0.4, 0.12), 360);
  }

  // Event Listeners
  function bindEvents() {
    el.startDateInput.addEventListener('change', updateDurationBadge);
    el.endDateInput.addEventListener('change', updateDurationBadge);

    // Quick preset destination chips
    document.querySelectorAll('.preset-chip').forEach(chip => {
      chip.addEventListener('click', (e) => {
        document.querySelectorAll('.preset-chip').forEach(c => c.classList.remove('active'));
        chip.classList.add('active');
        el.destinationInput.value = chip.dataset.dest;
        playTone(600, 'sine', 0.08, 0.05);
      });
    });

    // Preset budget pills
    document.querySelectorAll('.budget-pill').forEach(pill => {
      pill.addEventListener('click', () => {
        document.querySelectorAll('.budget-pill').forEach(p => p.classList.remove('active'));
        pill.classList.add('active');
        el.budgetInput.value = pill.dataset.val;
        playTone(650, 'sine', 0.08, 0.05);
      });
    });

    // Launch Swarm CTA
    el.launchBtn.addEventListener('click', launchMission);

    // Sound toggle
    el.soundToggleBtn.addEventListener('click', () => {
      state.soundEnabled = !state.soundEnabled;
      el.soundIcon.textContent = state.soundEnabled ? '🔊' : '🔇';
      if (state.soundEnabled) playTone(700, 'sine', 0.1, 0.08);
    });

    // Settings modal triggers
    el.settingsBtn.addEventListener('click', openSettings);
    el.closeSettingsBtn.addEventListener('click', closeSettings);
    el.cancelSettingsBtn.addEventListener('click', closeSettings);
    el.saveSettingsBtn.addEventListener('click', saveServerConfig);

    // Dossier tab navigation
    document.querySelectorAll('.tab-btn').forEach(btn => {
      btn.addEventListener('click', () => {
        document.querySelectorAll('.tab-btn').forEach(b => b.classList.remove('active'));
        document.querySelectorAll('.tab-pane').forEach(p => p.classList.remove('active'));

        btn.classList.add('active');
        const targetId = btn.dataset.tab;
        const targetPane = document.getElementById(targetId);
        if (targetPane) {
          targetPane.classList.add('active');
          state.activeTab = targetId;
        }
        playTone(580, 'sine', 0.06, 0.04);
      });
    });

    // Tweak preset buttons
    document.querySelectorAll('.tweak-preset-btn').forEach(btn => {
      btn.addEventListener('click', () => {
        el.tweakTextInput.value = btn.dataset.msg;
        playTone(620, 'sine', 0.06, 0.04);
      });
    });

    // Tweak send
    el.sendTweakBtn.addEventListener('click', applyTweak);
    el.tweakTextInput.addEventListener('keydown', (e) => {
      if (e.key === 'Enter') applyTweak();
    });

    // Action toolbar buttons
    el.printBtn.addEventListener('click', () => window.print());
    el.exportJsonBtn.addEventListener('click', exportDossierJson);
    el.planAnotherBtn.addEventListener('click', () => {
      window.scrollTo({ top: 0, behavior: 'smooth' });
    });
  }

  // Launch Multi-Agent Mission
  async function launchMission() {
    const destination = el.destinationInput.value.trim();
    const budget = parseFloat(el.budgetInput.value);
    const startDate = el.startDateInput.value;
    const endDate = el.endDateInput.value;

    if (!destination) {
      alert('Please enter a destination city or country.');
      el.destinationInput.focus();
      return;
    }
    if (!budget || budget < 300) {
      alert('Please enter a budget of at least $300.');
      el.budgetInput.focus();
      return;
    }
    if (!startDate || !endDate) {
      alert('Please select valid departure and return dates.');
      return;
    }

    const payload = {
      destination: destination,
      origin: el.originInput.value.trim() || 'New York (JFK)',
      startDate: startDate,
      endDate: endDate,
      budget: budget,
      currency: el.currencySelect.value,
      travelers: parseInt(el.travelersInput.value, 10),
      travelStyle: el.styleInput.value,
      interests: 'Culture, Cuisine, Iconic Sightseeing'
    };

    // UI State for live show
    el.launchBtn.disabled = true;
    el.launchBtn.innerHTML = `<span class="spinner-small"></span> <span>DEPLOYING SWARM...</span>`;
    playTone(440, 'triangle', 0.3, 0.1);

    // Reset & reveal Command Deck
    el.terminalFeed.innerHTML = '';
    el.agentCommandSection.classList.remove('hidden');
    el.dossierSection.classList.add('hidden');
    updateProgress(10, 'MISSION_INIT', 'Atlas Orchestrator is formulating multi-agent mission protocol...');
    resetRadarVisuals();

    // Scroll smoothly to the Live Command Deck
    el.agentCommandSection.scrollIntoView({ behavior: 'smooth', block: 'start' });

    try {
      const response = await fetch('/api/trip/plan', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
      });

      if (!response.ok) throw new Error('Failed to initialize session');
      const data = await response.json();
      state.sessionId = data.sessionId;

      // Start SSE Streaming
      listenToAgentStream(data.sessionId);

    } catch (err) {
      console.error(err);
      alert('Error launching multi-agent swarm: ' + err.message);
      el.launchBtn.disabled = false;
      el.launchBtn.innerHTML = `<span class="btn-sparkle">✦</span> <span class="btn-text">INITIALIZE MULTI-AGENT SWARM</span> <span class="btn-icon">➔</span>`;
    }
  }

  // Connect to Server-Sent Events (SSE)
  function listenToAgentStream(sessionId) {
    if (state.sse) {
      state.sse.close();
    }

    state.sse = new EventSource('/api/trip/stream/' + sessionId);

    // Status updates
    state.sse.addEventListener('status', (e) => {
      try {
        const data = JSON.parse(e.data);
        updateProgress(data.progress, data.phase, data.message);
      } catch (err) {
        console.error('SSE Status Parse Error', err);
      }
    });

    // Real-time Agent Messages
    state.sse.addEventListener('agent_message', (e) => {
      try {
        const msg = JSON.parse(e.data);
        renderAgentMessage(msg);
        animateNodeSpeaking(msg.sender);
      } catch (err) {
        console.error('SSE Message Parse Error', err);
      }
    });

    // Complete Event (Final Dossier Handover)
    state.sse.addEventListener('dossier_complete', (e) => {
      try {
        const dossier = JSON.parse(e.data);
        state.dossier = dossier;
        handleDossierComplete(dossier);
        state.sse.close();
      } catch (err) {
        console.error('SSE Complete Parse Error', err);
      }
    });

    // Error
    state.sse.addEventListener('error', (e) => {
      console.warn('SSE stream closed or encountered error', e);
      state.sse.close();
      el.launchBtn.disabled = false;
      el.launchBtn.innerHTML = `<span class="btn-sparkle">✦</span> <span class="btn-text">INITIALIZE MULTI-AGENT SWARM</span> <span class="btn-icon">➔</span>`;
    });
  }

  function updateProgress(percent, phase, text) {
    el.progressPercent.textContent = percent + '%';
    el.progressFill.style.width = percent + '%';
    el.progressStatusText.textContent = text;
    el.deckPhaseBadge.textContent = 'PHASE: ' + phase.replace(/_/g, ' ');
  }

  function resetRadarVisuals() {
    [el.nodeOrchestrator, el.nodeFlight, el.nodeStay, el.nodeActivity, el.nodeBudget].forEach(n => {
      n.classList.remove('speaking');
    });
    [el.beamFlight, el.beamStay, el.beamActivity, el.beamBudget].forEach(b => {
      b.classList.remove('active');
    });
    // Reset roster chips
    document.querySelectorAll('.agent-chip').forEach(c => c.classList.remove('active'));
    document.querySelector('.chip-orchestrator').classList.add('active');
  }

  function animateNodeSpeaking(senderName) {
    resetRadarVisuals();
    playTone(550, 'sine', 0.12, 0.07);

    const s = senderName.toLowerCase();
    if (s.includes('atlas') || s.includes('orchestrator')) {
      el.nodeOrchestrator.classList.add('speaking');
      document.querySelector('.chip-orchestrator').classList.add('active');
    } else if (s.includes('aero') || s.includes('flight')) {
      el.nodeFlight.classList.add('speaking');
      el.beamFlight.classList.add('active');
      document.querySelector('.chip-flight').classList.add('active');
    } else if (s.includes('sanctuary') || s.includes('stay')) {
      el.nodeStay.classList.add('speaking');
      el.beamStay.classList.add('active');
      document.querySelector('.chip-stay').classList.add('active');
    } else if (s.includes('curator') || s.includes('activity')) {
      el.nodeActivity.classList.add('speaking');
      el.beamActivity.classList.add('active');
      document.querySelector('.chip-activity').classList.add('active');
    } else if (s.includes('auditor') || s.includes('budget')) {
      el.nodeBudget.classList.add('speaking');
      el.beamBudget.classList.add('active');
      document.querySelector('.chip-budget').classList.add('active');
    }
  }

  function renderAgentMessage(msg) {
    const chatMsg = document.createElement('div');
    chatMsg.className = 'chat-msg';

    let avatarClass = 'avatar-orchestrator';
    let avatarIcon = '🌟';
    const s = msg.sender.toLowerCase();
    if (s.includes('aero')) { avatarClass = 'avatar-flight'; avatarIcon = '✈️'; }
    else if (s.includes('sanctuary')) { avatarClass = 'avatar-stay'; avatarIcon = '🏨'; }
    else if (s.includes('curator')) { avatarClass = 'avatar-activity'; avatarIcon = '🎭'; }
    else if (s.includes('auditor')) { avatarClass = 'avatar-budget'; avatarIcon = '⚖️'; }

    let bubbleClass = 'msg-bubble';
    if (msg.sentiment === 'alert') bubbleClass += ' alert';
    if (msg.sentiment === 'negotiate') bubbleClass += ' negotiate';
    if (msg.sentiment === 'final') bubbleClass += ' final';

    chatMsg.innerHTML = `
      <div class="msg-avatar-wrap ${avatarClass}">
        <span>${avatarIcon}</span>
      </div>
      <div class="msg-body">
        <div class="msg-meta">
          <span class="msg-sender">${escapeHtml(msg.sender)}</span>
          <span class="msg-role">${escapeHtml(msg.senderRole)}</span>
          <span class="msg-time">${msg.timestamp}</span>
        </div>
        <div class="${bubbleClass}">
          ${escapeHtml(msg.content)}
        </div>
      </div>
    `;

    el.terminalFeed.appendChild(chatMsg);
    el.terminalFeed.scrollTop = el.terminalFeed.scrollHeight;
  }

  // When dossier is received
  function handleDossierComplete(dossier) {
    updateProgress(100, 'MISSION_ACCOMPLISHED', 'All agents synchronized. Master dossier verified.');
    el.launchBtn.disabled = false;
    el.launchBtn.innerHTML = `<span class="btn-sparkle">✦</span> <span class="btn-text">REGENERATE EXPEDITION</span> <span class="btn-icon">➔</span>`;

    playVictoryChime();

    // Render Dossier views
    renderDossier(dossier);

    // Reveal Dossier Section with smooth animation
    el.dossierSection.classList.remove('hidden');
    setTimeout(() => {
      el.dossierSection.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }, 600);
  }

  // Render Full Dossier Data
  function renderDossier(dossier) {
    // 1. Hero Header
    el.dossierHeroBg.style.backgroundImage = `url('${dossier.heroImageUrl || 'https://images.unsplash.com/photo-1503899036084-c55cdd92da26?auto=format&fit=crop&w=1600&q=80'}')`;
    el.dossierCountryBadge.textContent = (dossier.destinationCountry || 'GLOBAL').toUpperCase();
    el.dossierDestName.textContent = dossier.destination;
    el.dossierTagline.textContent = dossier.destinationTagline || 'An engineered autonomous journey';
    el.dossierSummaryText.textContent = dossier.summary || '';

    // Fast metrics
    const b = dossier.budget;
    if (b) {
      el.metricTotalSpend.textContent = `$${b.totalCost.toFixed(2)}`;
      el.metricSavings.textContent = b.remainingSavings > 0
        ? `Surplus savings: $${b.remainingSavings.toFixed(2)} under budget`
        : `Optimized right to budget`;
      el.metricBuffer.textContent = `$${b.emergencyBuffer.toFixed(2)}`;
    }

    el.metricDuration.textContent = `${dossier.totalDays} Nights / ${dossier.totalDays + 1} Days`;
    el.metricDates.textContent = `${dossier.startDate} to ${dossier.endDate}`;
    el.metricWeather.textContent = dossier.weatherForecast || 'Mild & pleasant';

    // 2. Render Tabs
    renderItinerary(dossier.itinerary);
    renderFlights(dossier.outboundFlight, dossier.returnFlight);
    renderHotel(dossier.stay);
    renderBudget(dossier.budget);
    renderPrep(dossier);
  }

  // Render Day-by-Day Itinerary
  function renderItinerary(itinerary) {
    if (!itinerary || !itinerary.length) return;
    el.dayPillsBar.innerHTML = '';
    state.activeDayIndex = 0;

    itinerary.forEach((day, index) => {
      const btn = document.createElement('button');
      btn.type = 'button';
      btn.className = 'day-pill-btn' + (index === 0 ? ' active' : '');
      btn.innerHTML = `
        <span class="pill-day-title">Day ${day.dayNumber}</span>
        <span class="pill-day-date">${day.dateStr}</span>
      `;
      btn.addEventListener('click', () => {
        document.querySelectorAll('.day-pill-btn').forEach(b => b.classList.remove('active'));
        btn.classList.add('active');
        state.activeDayIndex = index;
        renderActiveDay(day);
        playTone(600, 'sine', 0.05, 0.03);
      });
      el.dayPillsBar.appendChild(btn);
    });

    renderActiveDay(itinerary[0]);
  }

  function renderActiveDay(day) {
    el.dayContainer.innerHTML = `
      <div class="day-header-card glass-card">
        <div>
          <h3 class="day-theme-title">${escapeHtml(day.title)}</h3>
          <p class="day-theme-desc"><strong>Theme:</strong> ${escapeHtml(day.theme)}</p>
        </div>
        <div class="day-stat-pills">
          <span class="day-stat-chip">🌤️ ${escapeHtml(day.weatherSummary)}</span>
          <span class="day-stat-chip">👟 ~${day.estimatedSteps.toLocaleString()} steps</span>
          <span class="day-stat-chip">💰 Est. spend: $${day.dailyEstimatedSpend.toFixed(2)}</span>
        </div>
      </div>

      <div class="activities-grid">
        ${day.activities.map(act => `
          <div class="activity-card">
            <div class="activity-img-wrap">
              <img class="activity-img" src="${act.imageUrl || 'https://images.unsplash.com/photo-1540959733332-eab4deabeeaf?auto=format&fit=crop&w=800&q=80'}" alt="${escapeHtml(act.title)}" loading="lazy" />
              <span class="activity-period-tag">${act.period}</span>
              ${act.mustVisit ? '<span class="activity-must-visit">★ MUST VISIT</span>' : ''}
            </div>
            <div class="activity-content">
              <div class="activity-timing-row">
                <span class="activity-slot">🕒 ${escapeHtml(act.timeSlot)}</span>
                <span class="activity-cost-badge">${act.cost > 0 ? `$${act.cost.toFixed(2)}` : 'FREE'}</span>
              </div>
              <h4 class="activity-title">${escapeHtml(act.title)}</h4>
              <p class="activity-desc">${escapeHtml(act.description)}</p>
              ${act.insiderTip ? `
                <div class="activity-tip-box">
                  <span>💡</span>
                  <span><strong>Insider Tip:</strong> ${escapeHtml(act.insiderTip)}</span>
                </div>
              ` : ''}
            </div>
          </div>
        `).join('')}
      </div>
    `;
  }

  // Render Realistic Airline Boarding Passes
  function renderFlights(outbound, inbound) {
    if (!outbound || !inbound) return;
    el.flightsContainer.innerHTML = `
      ${renderBoardingPass(outbound, 'OUTBOUND EXPEDITION')}
      ${renderBoardingPass(inbound, 'RETURN VOYAGE')}
    `;
  }

  function renderBoardingPass(flight, titleBadge) {
    return `
      <div class="boarding-pass glass-card">
        <div class="pass-main">
          <div class="pass-header">
            <div class="pass-airline-brand">
              <div class="airline-logo-badge">✈️</div>
              <div>
                <div class="airline-name">${escapeHtml(flight.airline)}</div>
                <div style="font-size: 0.76rem; color: var(--text-faint); font-family: var(--font-mono);">${flight.aircraft || 'Airbus A350'}</div>
              </div>
            </div>
            <div class="pass-class-badge">${escapeHtml(flight.cabinClass)} • ${titleBadge}</div>
          </div>

          <div class="pass-flight-route">
            <div class="route-endpoint">
              <span class="airport-code">${escapeHtml(flight.departureAirport.substring(0, 3))}</span>
              <span class="city-name">${escapeHtml(flight.departureCity)}</span>
              <span class="time-stamp">${escapeHtml(flight.departureTime)}</span>
            </div>

            <div class="flight-duration-center">
              <span class="duration-text">${escapeHtml(flight.duration)}</span>
              <div class="flight-line-wrap">
                <div class="line-dash"></div>
                <span class="plane-icon">✈</span>
                <div class="line-dash"></div>
              </div>
              <span class="stops-tag">${escapeHtml(flight.stops)}</span>
            </div>

            <div class="route-endpoint" style="text-align: right;">
              <span class="airport-code">${escapeHtml(flight.arrivalAirport.substring(0, 3))}</span>
              <span class="city-name">${escapeHtml(flight.arrivalCity)}</span>
              <span class="time-stamp">${escapeHtml(flight.arrivalTime)}</span>
            </div>
          </div>

          <div class="pass-meta-grid">
            <div class="meta-item">
              <span class="meta-title">FLIGHT NO.</span>
              <span class="meta-val">${escapeHtml(flight.flightNumber)}</span>
            </div>
            <div class="meta-item">
              <span class="meta-title">BAGGAGE</span>
              <span class="meta-val">${escapeHtml(flight.baggage)}</span>
            </div>
            <div class="meta-item">
              <span class="meta-title">GATE / SEAT</span>
              <span class="meta-val">Auto-Assigned VIP</span>
            </div>
            <div class="meta-item">
              <span class="meta-title">AIRFARE</span>
              <span class="meta-val" style="color: #38BDF8;">$${flight.price.toFixed(2)}</span>
            </div>
          </div>
        </div>

        <div class="pass-stub">
          <div>
            <div class="stub-title">TRIPGENIE PASS</div>
            <div class="stub-price">$${flight.price.toFixed(2)}</div>
            <div style="font-family: var(--font-mono); font-size: 0.72rem; color: #2A9D8F; margin-top: 4px;">CONFIRMED</div>
          </div>
          <div class="barcode-sim"></div>
          <div class="stub-qr-code">${flight.qrCodeData}</div>
        </div>
      </div>
    `;
  }

  // Render Hotel & Accommodation
  function renderHotel(stay) {
    if (!stay) return;
    el.hotelContainer.innerHTML = `
      <div class="hotel-gallery">
        <img class="hotel-hero-img" src="${stay.imageUrl || 'https://images.unsplash.com/photo-1566073771259-6a8506099945?auto=format&fit=crop&w=1200&q=80'}" alt="${escapeHtml(stay.hotelName)}" />
        <div class="hotel-overlay-badge">🏨 CURATED BY SANCTUARY AGENT</div>
      </div>

      <div class="hotel-details">
        <div class="hotel-title-group">
          <div class="hotel-stars">★ ★ ★ ★ ★ <span style="color: var(--text-faint); font-size: 0.8rem;">(${stay.starRating} / 5.0) • Guest Score: ${stay.guestScore}/10 (${stay.reviewCount.toLocaleString()} reviews)</span></div>
          <h3 class="hotel-name">${escapeHtml(stay.hotelName)}</h3>
          <div class="hotel-district">📍 ${escapeHtml(stay.district)} • ${escapeHtml(stay.metroDistance)}</div>
        </div>

        <div class="hotel-vibe-box">
          <div class="vibe-label">DISTRICT & NEIGHBORHOOD VIBE:</div>
          <p class="vibe-text">${escapeHtml(stay.vibe)}</p>
        </div>

        <div style="margin-bottom: 12px;">
          <div style="font-size: 0.8rem; font-family: var(--font-mono); color: var(--text-faint); margin-bottom: 6px;">ROOM CATEGORY:</div>
          <div style="font-size: 0.95rem; font-weight: 600; color: #F8FAFC;">${escapeHtml(stay.roomType)}</div>
        </div>

        <div class="amenities-list">
          ${stay.amenities.map(a => `<span class="amenity-chip">✓ ${escapeHtml(a)}</span>`).join('')}
        </div>

        <div class="hotel-pricing-bar">
          <div class="rate-group">
            <span class="nightly-rate">$${stay.nightlyRate.toFixed(2)} <span style="font-size: 0.82rem; color: var(--text-muted); font-weight: 400;">/ night</span></span>
            <span class="total-stay">Total for ${stay.totalNights} nights: $${stay.totalCost.toFixed(2)} (Taxes & fees included)</span>
          </div>
          <span style="font-family: var(--font-mono); font-size: 0.78rem; color: #10B981; background: rgba(16, 185, 129, 0.12); padding: 6px 14px; border-radius: 9999px;">
            VERIFIED AVAILABLE
          </span>
        </div>
      </div>
    `;
  }

  // Render Budget Breakdown & SVG Donut Chart
  function renderBudget(budget) {
    if (!budget) return;
    el.donutTotalVal.textContent = `$${budget.totalBudget.toFixed(0)}`;

    const slices = [
      { name: 'Flights', amount: budget.flightsCost, color: '#06B6D4', agent: 'AeroStream' },
      { name: 'Accommodations', amount: budget.staysCost, color: '#10B981', agent: 'Sanctuary' },
      { name: 'Curated Activities', amount: budget.activitiesCost, color: '#F59E0B', agent: 'Curator' },
      { name: 'Dining & Local Transit', amount: budget.diningAndTransitCost, color: '#A855F7', agent: 'Curator & Atlas' },
      { name: 'Untouchable Buffer', amount: budget.emergencyBuffer, color: '#EC4899', agent: 'Auditor' }
    ];

    // Compute SVG donut circle coordinates
    const radius = 80;
    const circumference = 2 * Math.PI * radius;
    let accumulatedOffset = 0;

    el.budgetDonutSvg.innerHTML = '';
    el.donutLegend.innerHTML = '';
    el.budgetTableBody.innerHTML = '';

    slices.forEach(slice => {
      const share = slice.amount / budget.totalBudget;
      const strokeLength = Math.max(share * circumference, 0);
      const dashoffset = -accumulatedOffset;
      accumulatedOffset += strokeLength;

      // Circle element
      const circle = document.createElementNS('http://www.w3.org/2000/svg', 'circle');
      circle.setAttribute('cx', '120');
      circle.setAttribute('cy', '120');
      circle.setAttribute('r', radius.toString());
      circle.setAttribute('class', 'donut-slice');
      circle.setAttribute('stroke', slice.color);
      circle.setAttribute('stroke-dasharray', `${strokeLength} ${circumference}`);
      circle.setAttribute('stroke-dashoffset', dashoffset.toString());
      circle.setAttribute('title', `${slice.name}: $${slice.amount.toFixed(2)} (${(share * 100).toFixed(1)}%)`);
      el.budgetDonutSvg.appendChild(circle);

      // Legend entry
      const legendItem = document.createElement('div');
      legendItem.className = 'legend-item';
      legendItem.innerHTML = `
        <div class="legend-left">
          <span class="legend-color" style="background: ${slice.color}"></span>
          <span>${slice.name}</span>
        </div>
        <span style="font-family: var(--font-mono); font-weight: 700;">$${slice.amount.toFixed(2)}</span>
      `;
      el.donutLegend.appendChild(legendItem);

      // Table row
      const tr = document.createElement('tr');
      tr.innerHTML = `
        <td><span style="color: ${slice.color}">●</span> ${slice.name}</td>
        <td>${slice.agent}</td>
        <td style="font-family: var(--font-mono); font-weight: 700;">$${slice.amount.toFixed(2)}</td>
        <td style="font-family: var(--font-mono); color: var(--text-faint);">${(share * 100).toFixed(1)}%</td>
      `;
      el.budgetTableBody.appendChild(tr);
    });

    // Auditor Notes List
    el.auditNotesList.innerHTML = '';
    if (budget.optimizationNotes && budget.optimizationNotes.length) {
      budget.optimizationNotes.forEach(note => {
        const li = document.createElement('li');
        li.textContent = note;
        el.auditNotesList.appendChild(li);
      });
    }
  }

  // Render Prep & Packing
  function renderPrep(dossier) {
    el.packingChecklist.innerHTML = '';
    if (dossier.packingChecklist && dossier.packingChecklist.length) {
      dossier.packingChecklist.forEach((item, i) => {
        const div = document.createElement('label');
        div.className = 'check-item';
        div.innerHTML = `
          <input type="checkbox" id="pack-${i}" />
          <span>${escapeHtml(item)}</span>
        `;
        div.querySelector('input').addEventListener('change', (e) => {
          div.classList.toggle('checked', e.target.checked);
          playTone(680, 'sine', 0.05, 0.03);
        });
        el.packingChecklist.appendChild(div);
      });
    }

    el.insiderHacksList.innerHTML = '';
    if (dossier.localInsiderHacks && dossier.localInsiderHacks.length) {
      dossier.localInsiderHacks.forEach(hack => {
        const div = document.createElement('div');
        div.className = 'hack-box';
        div.textContent = hack;
        el.insiderHacksList.appendChild(div);
      });
    }

    el.specCurrency.textContent = dossier.localCurrencyAdvice || 'Standard Cards & Local ATM';
    el.specPower.textContent = dossier.powerPlugInfo || 'Universal dual-voltage';
    el.specEmergency.textContent = dossier.emergencyNumber || '112';
  }

  // Conversational Tweaks with Atlas Copilot
  async function applyTweak() {
    const msg = el.tweakTextInput.value.trim();
    if (!msg || !state.dossier) return;

    el.sendTweakBtn.disabled = true;
    el.tweakFeedback.classList.remove('hidden');
    playTone(500, 'triangle', 0.15, 0.07);

    try {
      const res = await fetch('/api/trip/tweak', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          userMessage: msg,
          currentDossier: state.dossier
        })
      });

      if (!res.ok) throw new Error('Tweak application failed');
      const updatedDossier = await res.json();
      state.dossier = updatedDossier;

      // Re-render updated dossier
      renderDossier(updatedDossier);
      playVictoryChime();

      el.tweakTextInput.value = '';
    } catch (e) {
      alert('Error updating trip: ' + e.message);
    } finally {
      el.sendTweakBtn.disabled = false;
      el.tweakFeedback.classList.add('hidden');
    }
  }

  // Export JSON
  function exportDossierJson() {
    if (!state.dossier) return;
    const blob = new Blob([JSON.stringify(state.dossier, null, 2)], { type: 'application/json' });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = `tripgenie-vacation-dossier-${state.dossier.destination.replace(/[^a-zA-Z0-9]/g, '_')}.json`;
    a.click();
    URL.revokeObjectURL(url);
  }

  // Config & Settings
  async function loadServerConfig() {
    try {
      const res = await fetch('/api/config');
      if (res.ok) {
        const config = await res.json();
        el.modelSelect.value = config.activeModel || 'gpt-6-astra';
        el.endpointInput.value = config.customEndpoint || 'https://api.openai.com/v1';
        el.fallbackToggle.checked = config.simulatedEngineFallback !== false;
      }
    } catch (e) {
      console.warn('Could not load config', e);
    }
  }

  async function saveServerConfig() {
    const payload = {
      activeModel: el.modelSelect.value,
      apiKey: el.apiKeyInput.value.trim(),
      customEndpoint: el.endpointInput.value.trim(),
      simulatedEngineFallback: el.fallbackToggle.checked
    };

    try {
      const res = await fetch('/api/config', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
      });
      if (res.ok) {
        closeSettings();
        playTone(650, 'sine', 0.1, 0.08);
      }
    } catch (e) {
      alert('Error saving settings: ' + e.message);
    }
  }

  function openSettings() {
    el.settingsModal.classList.remove('hidden');
  }

  function closeSettings() {
    el.settingsModal.classList.add('hidden');
  }

  // Utility to escape HTML
  function escapeHtml(str) {
    if (!str) return '';
    return String(str)
      .replace(/&/g, '&amp;')
      .replace(/</g, '&lt;')
      .replace(/>/g, '&gt;')
      .replace(/"/g, '&quot;')
      .replace(/'/g, '&#039;');
  }

  // ==========================================================================
  // SMART TRAVEL FOOTER UTILITIES (WORLD CLOCKS, CURRENCY CONVERTER, PRESETS)
  // ==========================================================================

  function initWorldClocks() {
    const clockMap = [
      { id: 'clockTokyo', tz: 'Asia/Tokyo' },
      { id: 'clockParis', tz: 'Europe/Paris' },
      { id: 'clockBali', tz: 'Asia/Makassar' },
      { id: 'clockNYC', tz: 'America/New_York' },
      { id: 'clockDubai', tz: 'Asia/Dubai' },
      { id: 'clockRome', tz: 'Europe/Rome' }
    ];

    function updateClocks() {
      const now = new Date();
      clockMap.forEach(item => {
        const elem = document.getElementById(item.id);
        if (elem) {
          try {
            elem.textContent = now.toLocaleTimeString('en-US', {
              timeZone: item.tz,
              hour12: false,
              hour: '2-digit',
              minute: '2-digit',
              second: '2-digit'
            });
          } catch (e) {
            elem.textContent = '--:--:--';
          }
        }
      });
    }

    updateClocks();
    setInterval(updateClocks, 1000);
  }

  function initCurrencyConverter() {
    const amountInput = document.getElementById('calcAmount');
    const fromSelect = document.getElementById('calcFrom');
    if (!amountInput || !fromSelect) return;

    // Approximate live conversion rates relative to 1 USD
    const rates = {
      USD: 1.0,
      EUR: 0.925,
      GBP: 0.791,
      JPY: 154.2,
      IDR: 15850,
      INR: 86.8
    };

    function recalculate() {
      const amt = parseFloat(amountInput.value) || 0;
      const baseCur = fromSelect.value || 'USD';
      const usdValue = amt / (rates[baseCur] || 1.0);

      const resEUR = document.getElementById('resEUR');
      const resJPY = document.getElementById('resJPY');
      const resIDR = document.getElementById('resIDR');
      const resGBP = document.getElementById('resGBP');

      if (resEUR) resEUR.textContent = '€ ' + (usdValue * rates.EUR).toLocaleString('en-US', { minimumFractionDigits: 2, maximumFractionDigits: 2 });
      if (resJPY) resJPY.textContent = '¥ ' + Math.round(usdValue * rates.JPY).toLocaleString('en-US');
      if (resIDR) {
        const idrVal = usdValue * rates.IDR;
        if (idrVal >= 1000000) {
          resIDR.textContent = (idrVal / 1000000).toFixed(2) + 'M Rp';
        } else {
          resIDR.textContent = Math.round(idrVal).toLocaleString('en-US') + ' Rp';
        }
      }
      if (resGBP) resGBP.textContent = '£ ' + (usdValue * rates.GBP).toLocaleString('en-US', { minimumFractionDigits: 2, maximumFractionDigits: 2 });
    }

    amountInput.addEventListener('input', recalculate);
    fromSelect.addEventListener('change', recalculate);
    recalculate();
  }

  function initFooterUtilities() {
    // 1-Click Vacation Presets
    document.querySelectorAll('.preset-shortcut-btn').forEach(btn => {
      btn.addEventListener('click', () => {
        const dest = btn.dataset.dest;
        const budget = btn.dataset.budget;
        const style = btn.dataset.style;

        if (dest && el.destinationInput) el.destinationInput.value = dest;
        if (budget && el.budgetInput) el.budgetInput.value = budget;
        if (style && el.styleInput) el.styleInput.value = style;

        // Sync budget pill if matching
        document.querySelectorAll('.budget-pill').forEach(p => {
          if (p.dataset.val === budget) {
            p.classList.add('active');
          } else {
            p.classList.remove('active');
          }
        });

        // Smooth scroll to top form
        window.scrollTo({ top: 0, behavior: 'smooth' });
        playTone(680, 'sine', 0.12, 0.08);

        // Highlight destination input briefly
        if (el.destinationInput) {
          el.destinationInput.focus();
          el.destinationInput.classList.add('pulse-highlight');
          setTimeout(() => el.destinationInput.classList.remove('pulse-highlight'), 1200);
        }
      });
    });

    // Back to Top Button
    const backToTopBtn = document.getElementById('backToTopBtn');
    if (backToTopBtn) {
      backToTopBtn.addEventListener('click', () => {
        window.scrollTo({ top: 0, behavior: 'smooth' });
        playTone(550, 'sine', 0.08, 0.04);
      });
    }
  }

  // ==========================================================================
  // INTERACTIVE AGENT PROFILES & LIVE TELEMETRY MODAL
  // ==========================================================================

  function switchDossierTab(targetTabId) {
    const btn = document.querySelector(`.tab-btn[data-tab="${targetTabId}"]`);
    const pane = document.getElementById(targetTabId);
    if (btn && pane) {
      document.querySelectorAll('.tab-btn').forEach(b => b.classList.remove('active'));
      document.querySelectorAll('.tab-pane').forEach(p => p.classList.remove('active'));
      btn.classList.add('active');
      pane.classList.add('active');
      state.activeTab = targetTabId;
      pane.scrollIntoView({ behavior: 'smooth', block: 'start' });
      playTone(580, 'sine', 0.06, 0.04);
    }
  }

  const AGENT_PROFILES = {
    atlas: {
      id: 'atlas',
      name: 'Atlas',
      fullName: 'Atlas Lead Orchestrator',
      title: 'Autonomous Swarm Commander & Lead Orchestrator',
      avatar: '🌟',
      color: '#0F4C5C',
      accent: '#E9C46A',
      badge: 'LEAD ORCHESTRATOR',
      role: 'Central Intelligence & Synthesis',
      model: 'GPT-6 Astra Swarm Core (Java 24 Virtual Threads)',
      freq: 520,
      quote: 'Synthesizing multi-agent intelligence to build your dream vacation. Zero compromises on budget or safety.',
      pingResponses: [
        'Atlas online. All 4 specialist sub-agents synchronized and operating at peak cognitive efficiency.',
        'Monitoring global travel constraints and weather trends for optimal trip execution.',
        'Conflict-resolution matrix standing by. Ready to balance budget vs luxury tradeoffs on demand.'
      ],
      mission: 'Atlas initiates and oversees the entire vacation generation lifecycle. When you enter destination and budget, Atlas breaks down requirements into discrete domain tasks for AeroStream, Sanctuary, Curator, and Auditor, then merges their parallel outputs into an executable unified dossier.',
      powers: [
        'Autonomous Task Decomposition & Parallel Dispatch',
        'Inter-Agent Conflict & Budget Dispute Arbitrator',
        'Trip Coherence & Real-time Route Synthesis',
        'Dynamic User Tweak & Adjustment Coordinator'
      ],
      dossierTab: 'tab-itinerary',
      tabName: 'Full Itinerary & Summary'
    },
    flight: {
      id: 'flight',
      name: 'AeroStream',
      fullName: 'AeroStream Flight Specialist',
      title: 'Autonomous Flight Routes & Airspace Specialist',
      avatar: '✈️',
      color: '#2A9D8F',
      accent: '#64DFDF',
      badge: 'FLIGHT SPECIALIST',
      role: 'Airfare Matrix & Transit Logistics',
      model: 'GPT-6 Astra Aero-Reasoning Engine v24.3',
      freq: 650,
      quote: 'Scanning thousands of flight corridors to lock in the smoothest routing and lowest baggage fees.',
      pingResponses: [
        'AeroStream online. Scanning airline routing matrix and historical delay rates.',
        'Enforcing strict 90+ minute international connection buffers to prevent missed layovers.',
        'Fare volatility index is stable. Optimal departure window identified.'
      ],
      mission: 'AeroStream acts as your autonomous flight concierge. It identifies the optimal balance between price, travel time, and comfort—locking in non-stop or smart layover corridors with zero hidden luggage fees.',
      powers: [
        'Multi-Carrier Routing & Fare Matrix Optimization',
        'Strict Layover Safety Checks (No risky sprint connections)',
        'Luggage & Hidden Baggage Surcharge Protection',
        'Seamless Boarding Pass Generation & Airport Transit Prep'
      ],
      dossierTab: 'tab-flights',
      tabName: 'Flights & Boarding Passes'
    },
    stay: {
      id: 'stay',
      name: 'Sanctuary',
      fullName: 'Sanctuary Stays Specialist',
      title: 'Autonomous Accommodations & Neighborhoods Specialist',
      avatar: '🏨',
      color: '#1B7A6F',
      accent: '#52B788',
      badge: 'STAYS SPECIALIST',
      role: 'Hotels, Villas & Neighborhood Safety',
      model: 'GPT-6 Astra Hospitality Engine v24.3',
      freq: 440,
      quote: 'Curating boutique sanctuaries with verified 8.5+ ratings in vibrant, walkable neighborhoods.',
      pingResponses: [
        'Sanctuary online. Auditing local neighborhoods for walkability, safety, and nocturnal noise levels.',
        'Screening properties for zero surprise resort fees and high-speed fiber WiFi.',
        'Shortlisting accommodations located within 15 minutes of major cultural nodes.'
      ],
      mission: 'Sanctuary curates exceptional accommodations matching your travel persona. From serene Bali jungle villas to chic Paris boutique hotels, every stay is audited for safety, comfort, cleanliness, and value.',
      powers: [
        'Neighborhood Walkability & Transit Proximity Audits',
        'Minimum 8.5/10 Certified Guest Rating Threshold',
        'Zero Hidden Resort/Amenity Fee Guarantee',
        'Curated Match to Travel Style (Luxury, Backpacker, Nomad)'
      ],
      dossierTab: 'tab-hotel',
      tabName: 'Accommodations & Vetted Hotels'
    },
    activity: {
      id: 'activity',
      name: 'Curator',
      fullName: 'Curator Experience Specialist',
      title: 'Autonomous Cultural & Daily Experiences Specialist',
      avatar: '🎭',
      color: '#F4A261',
      accent: '#E76F51',
      badge: 'EXPERIENCES & VIBE',
      role: 'Daily Itinerary, Food & Culture',
      model: 'GPT-6 Astra Cultural Knowledge Engine v24.3',
      freq: 780,
      quote: 'Crafting unforgettable days with hidden culinary treasures, sunset viewpoints, and authentic local vibes.',
      pingResponses: [
        'Curator online. Mapping geographical clusters to eliminate crisscrossing transit time.',
        'Pinpointing local culinary hotspots and timing sunset views to golden hour.',
        'Filtering out generic tourist traps in favor of authentic immersive experiences.'
      ],
      mission: 'Curator designs your day-by-day story. It groups sights geographically to prevent exhausting commute times, schedules morning and afternoon adventures, and recommends insider culinary stops.',
      powers: [
        'Geographical Activity Clustering (Save 2+ hours daily in transit)',
        'Tourist Trap Elimination & Authentic Local Discovery',
        'Golden Hour & Sunset Optimization',
        'Daily Pacing (Morning Energy, Afternoon Wonder, Evening Unwind)'
      ],
      dossierTab: 'tab-itinerary',
      tabName: 'Daily Itinerary & Activities'
    },
    budget: {
      id: 'budget',
      name: 'Auditor',
      fullName: 'Auditor Budget Specialist',
      title: 'Autonomous Budget Auditor & Tradeoff Analyst',
      avatar: '⚖️',
      color: '#E76F51',
      accent: '#F4A261',
      badge: 'BUDGET AUDITOR',
      role: 'Financial Prudence & Emergency Buffer',
      model: 'GPT-6 Astra Financial Reasoning Engine v24.3',
      freq: 360,
      quote: 'Guarding every dollar. Enforcing a non-negotiable 10% emergency buffer so you are never stranded.',
      pingResponses: [
        'Auditor online. Budget integrity verified across all spending categories.',
        '10% emergency reserve locked and safeguarded against flight or accommodation overages.',
        'Cost-per-day breakdown calculated and balanced within your spending envelope.'
      ],
      mission: 'Auditor acts as the financial conscience of the swarm. It reviews pricing proposals from AeroStream, Sanctuary, and Curator, ensuring the grand total remains strictly within budget and locks in a 10% emergency buffer.',
      powers: [
        'Strict Spending Envelope Enforcement',
        'Automatic 10% Untouchable Emergency Safety Buffer',
        'Real-Time Category Allocation & Cost Donut Analysis',
        'Tradeoff Optimization (Upgrading stay vs saving on flight)'
      ],
      dossierTab: 'tab-budget',
      tabName: 'Budget Donut & Financial Breakdown'
    }
  };

  function initAgentProfileModal() {
    const modal = document.getElementById('agentProfileModal');
    const closeBtn = document.getElementById('closeAgentModalBtn');
    const body = document.getElementById('agentModalBody');
    const tabs = document.querySelectorAll('.agent-nav-tab');

    if (!modal || !body) return;

    function renderAgentProfile(agentKey) {
      const agent = AGENT_PROFILES[agentKey] || AGENT_PROFILES.atlas;

      // Update active nav tab
      tabs.forEach(t => {
        if (t.dataset.agent === agentKey) {
          t.classList.add('active');
        } else {
          t.classList.remove('active');
        }
      });

      const hasDossier = !!state.dossier;

      body.innerHTML = `
        <div class="agent-hero-banner" style="border-left: 5px solid ${agent.color};">
          <div class="agent-avatar-badge" style="background: ${agent.color}18; color: ${agent.color}; border-color: ${agent.color};">
            <span>${agent.avatar}</span>
          </div>
          <div class="agent-hero-info">
            <div class="agent-status-row">
              <span class="agent-badge-pill" style="background: ${agent.color};">${agent.badge}</span>
              <span class="agent-live-tag">
                <span class="live-green-pulse"></span> ONLINE & SYNCHRONIZED
              </span>
            </div>
            <h3 class="agent-name-title" id="agentModalTitle">${agent.name}</h3>
            <span class="agent-role-subtitle">${agent.title}</span>
          </div>
        </div>

        <div class="agent-quote-card">
          “${escapeHtml(agent.quote)}”
        </div>

        <div>
          <div class="agent-section-title">
            <span>🎯</span> Core Autonomous Directive
          </div>
          <p class="agent-mission-text">${escapeHtml(agent.mission)}</p>
          <div class="agent-powers-grid">
            ${agent.powers.map(p => `
              <div class="agent-power-item">
                <span class="power-check">✓</span>
                <span>${escapeHtml(p)}</span>
              </div>
            `).join('')}
          </div>
        </div>

        <div class="agent-telemetry-bar">
          <div class="telemetry-item">
            <span class="tel-label">COGNITIVE ENGINE</span>
            <span class="tel-val">GPT-6 Astra v24.3</span>
          </div>
          <div class="telemetry-item">
            <span class="tel-label">SWARM LATENCY</span>
            <span class="tel-val">${Math.floor(Math.random() * 6) + 11}ms (Active)</span>
          </div>
          <div class="telemetry-item">
            <span class="tel-label">SWARM CONSENSUS</span>
            <span class="tel-val">100% In Agreement</span>
          </div>
        </div>

        <div class="agent-ping-box">
          <div class="ping-header">
            <span class="agent-section-title" style="margin: 0; font-size: 0.98rem;">
              <span>⚡</span> Real-Time Agent Diagnostic
            </span>
            <button type="button" class="btn-ping-agent" id="pingAgentBtn">
              <span>⚡</span> Ping ${agent.name}
            </button>
          </div>
          <div class="agent-live-bubble" id="pingBubble">
            <span class="bubble-avatar">${agent.avatar}</span>
            <span id="pingBubbleText">Agent standing by in swarm cluster. Click "Ping ${agent.name}" to verify live sensory telemetry.</span>
          </div>
        </div>

        <div class="agent-modal-footer">
          ${hasDossier ? `
            <button type="button" class="btn-primary" id="jumpToDossierTabBtn" style="padding: 10px 22px; font-size: 0.95rem;">
              <span>📂</span> View ${agent.name}'s ${agent.tabName} ➔
            </button>
          ` : `
            <span style="font-size: 0.85rem; color: var(--text-faint); font-family: var(--font-mono);">
              ℹ️ Ready to plan trip with ${agent.name}. Enter parameters above.
            </span>
          `}
          <button type="button" class="btn-secondary" id="closeAgentProfileBtn" style="padding: 10px 20px;">
            Close
          </button>
        </div>
      `;

      // Wire Ping Button
      const pingBtn = body.querySelector('#pingAgentBtn');
      const pingText = body.querySelector('#pingBubbleText');
      if (pingBtn && pingText) {
        pingBtn.addEventListener('click', () => {
          playTone(agent.freq, 'sine', 0.15, 0.1);
          const randResp = agent.pingResponses[Math.floor(Math.random() * agent.pingResponses.length)];
          pingText.textContent = randResp;
          const bubble = body.querySelector('#pingBubble');
          if (bubble) {
            bubble.style.borderColor = agent.color;
            bubble.style.background = 'rgba(42, 157, 143, 0.08)';
            setTimeout(() => {
              bubble.style.background = '#FFFFFF';
            }, 600);
          }
        });
      }

      // Wire Jump to Dossier Tab
      const jumpBtn = body.querySelector('#jumpToDossierTabBtn');
      if (jumpBtn) {
        jumpBtn.addEventListener('click', () => {
          closeModal();
          switchDossierTab(agent.dossierTab);
        });
      }

      // Wire close button inside footer
      const innerCloseBtn = body.querySelector('#closeAgentProfileBtn');
      if (innerCloseBtn) {
        innerCloseBtn.addEventListener('click', closeModal);
      }
    }

    function openModal(agentKey) {
      renderAgentProfile(agentKey);
      modal.classList.remove('hidden');
      const agent = AGENT_PROFILES[agentKey] || AGENT_PROFILES.atlas;
      playTone(agent.freq || 520, 'sine', 0.12, 0.08);
    }

    function closeModal() {
      modal.classList.add('hidden');
    }

    // Modal navigation tabs
    tabs.forEach(tab => {
      tab.addEventListener('click', () => {
        const key = tab.dataset.agent;
        if (key) {
          renderAgentProfile(key);
          const agent = AGENT_PROFILES[key];
          if (agent) playTone(agent.freq, 'sine', 0.08, 0.05);
        }
      });
    });

    // Close button
    if (closeBtn) closeBtn.addEventListener('click', closeModal);

    // Backdrop click
    modal.addEventListener('click', (e) => {
      if (e.target === modal) closeModal();
    });

    // Esc key
    document.addEventListener('keydown', (e) => {
      if (e.key === 'Escape' && !modal.classList.contains('hidden')) {
        closeModal();
      }
    });

    // Wire all header agent chips!
    document.querySelectorAll('.agent-chip').forEach(chip => {
      chip.addEventListener('click', (e) => {
        e.preventDefault();
        const agentKey = chip.dataset.agent;
        if (agentKey) openModal(agentKey);
      });
    });

    // Wire constellation radar nodes!
    document.querySelectorAll('.node.interactive-node').forEach(node => {
      node.addEventListener('click', (e) => {
        e.preventDefault();
        const agentKey = node.dataset.agent;
        if (agentKey) openModal(agentKey);
      });

      node.addEventListener('keydown', (e) => {
        if (e.key === 'Enter' || e.key === ' ') {
          e.preventDefault();
          const agentKey = node.dataset.agent;
          if (agentKey) openModal(agentKey);
        }
      });
    });
  }

  // Boot
  document.addEventListener('DOMContentLoaded', init);
})();
