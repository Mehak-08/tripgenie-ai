package com.astratravel.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AgentMessage {
    private String id;
    private String sender;
    private String senderRole;
    private String recipient;
    private String content;
    private String phase;
    private String timestamp;
    private String sentiment; // "neutral", "success", "alert", "negotiate", "final"
    private double confidence = 0.96;
    private String dataPayloadJson; // Optional JSON snippet

    public AgentMessage() {
        this.timestamp = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public AgentMessage(String sender, String senderRole, String recipient, String content, String phase, String sentiment) {
        this.id = "msg-" + System.currentTimeMillis() + "-" + (int)(Math.random() * 1000);
        this.sender = sender;
        this.senderRole = senderRole;
        this.recipient = recipient;
        this.content = content;
        this.phase = phase;
        this.sentiment = sentiment;
        this.timestamp = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        this.confidence = 0.94 + (Math.random() * 0.05);
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }

    public String getSenderRole() { return senderRole; }
    public void setSenderRole(String senderRole) { this.senderRole = senderRole; }

    public String getRecipient() { return recipient; }
    public void setRecipient(String recipient) { this.recipient = recipient; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getPhase() { return phase; }
    public void setPhase(String phase) { this.phase = phase; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public String getSentiment() { return sentiment; }
    public void setSentiment(String sentiment) { this.sentiment = sentiment; }

    public double getConfidence() { return confidence; }
    public void setConfidence(double confidence) { this.confidence = confidence; }

    public String getDataPayloadJson() { return dataPayloadJson; }
    public void setDataPayloadJson(String dataPayloadJson) { this.dataPayloadJson = dataPayloadJson; }
}
