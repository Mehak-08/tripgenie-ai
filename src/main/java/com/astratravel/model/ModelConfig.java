package com.astratravel.model;

public class ModelConfig {
    private String activeModel = "gpt-6-astra"; // "gpt-6-astra", "gpt-6-astra-turbo", "gpt-6-astra-omni"
    private String apiKey = "";
    private String customEndpoint = "https://api.openai.com/v1";
    private boolean simulatedEngineFallback = true;
    private double temperature = 0.7;
    private int maxTokens = 4096;

    public ModelConfig() {}

    public String getActiveModel() { return activeModel; }
    public void setActiveModel(String activeModel) { this.activeModel = activeModel; }

    public String getApiKey() { return apiKey; }
    public void setApiKey(String apiKey) { this.apiKey = apiKey; }

    public String getCustomEndpoint() { return customEndpoint; }
    public void setCustomEndpoint(String customEndpoint) { this.customEndpoint = customEndpoint; }

    public boolean isSimulatedEngineFallback() { return simulatedEngineFallback; }
    public void setSimulatedEngineFallback(boolean simulatedEngineFallback) { this.simulatedEngineFallback = simulatedEngineFallback; }

    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }

    public int getMaxTokens() { return maxTokens; }
    public void setMaxTokens(int maxTokens) { this.maxTokens = maxTokens; }
}
