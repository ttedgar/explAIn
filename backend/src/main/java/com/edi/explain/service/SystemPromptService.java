package com.edi.explain.service;

import org.springframework.stereotype.Service;

@Service
public class SystemPromptService {

    public String buildSystemPrompt(String extractedText) {
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
                """.formatted(extractedText);
    }
}
