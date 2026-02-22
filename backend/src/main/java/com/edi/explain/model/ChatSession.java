package com.edi.explain.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class ChatSession {
    private String sessionId;
    private String fileName;
    private String extractedText;
    private String systemPrompt;
    private List<ChatMessage> conversationHistory;
    private LocalDateTime createdAt;

    public ChatSession(String fileName, String extractedText, String systemPrompt) {
        this.sessionId = UUID.randomUUID().toString();
        this.fileName = fileName;
        this.extractedText = extractedText;
        this.systemPrompt = systemPrompt;
        this.conversationHistory = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
    }

    public void addMessage(ChatMessage message) {
        this.conversationHistory.add(message);
    }
}
