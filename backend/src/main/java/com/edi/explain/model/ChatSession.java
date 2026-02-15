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

    public ChatSession(String fileName, String extractedText) {
        this.sessionId = UUID.randomUUID().toString();
        this.fileName = fileName;
        this.extractedText = extractedText;
        this.systemPrompt = buildSystemPrompt(extractedText);
        this.conversationHistory = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
    }

    private String buildSystemPrompt(String text) {
        return """
                You are an AI assistant helping users understand documents.
                The user has uploaded a document with the following content:

                ---
                %s
                ---

                Your role is to:
                - Answer questions about this document
                - Explain complex concepts in simple terms
                - Provide summaries when asked
                - Help users understand legal, technical, or academic content

                Please be helpful, accurate, and concise.
                """.formatted(text);
    }

    public void addMessage(ChatMessage message) {
        this.conversationHistory.add(message);
    }
}
