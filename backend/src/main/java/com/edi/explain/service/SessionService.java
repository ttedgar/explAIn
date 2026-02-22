package com.edi.explain.service;

import com.edi.explain.exception.SessionNotFoundException;
import com.edi.explain.model.ChatMessage;
import com.edi.explain.model.ChatSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionService {

    private final Map<String, ChatSession> sessions = new ConcurrentHashMap<>();
    private final AIService aiService;

    public ChatSession createSession(String fileName, String extractedText) {
        ChatSession session = new ChatSession(fileName, extractedText);
        sessions.put(session.getSessionId(), session);
        log.info("Created session {} for file: {}", session.getSessionId(), fileName);
        return session;
    }

    public Optional<ChatSession> getSession(String sessionId) {
        return Optional.ofNullable(sessions.get(sessionId));
    }

    public String sendMessage(String sessionId, String userMessage) {
        ChatSession session = sessions.get(sessionId);
        if (session == null) {
            throw new SessionNotFoundException(sessionId);
        }

        ChatMessage userMsg = new ChatMessage("user", userMessage);
        session.addMessage(userMsg);

        String aiResponse = aiService.sendMessage(
            session.getSystemPrompt(),
            session.getConversationHistory(),
            userMessage
        );

        ChatMessage aiMsg = new ChatMessage("model", aiResponse);
        session.addMessage(aiMsg);

        log.info("Session {}: User asked, AI responded ({} chars)",
            sessionId, aiResponse.length());

        return aiResponse;
    }

    public void deleteSession(String sessionId) {
        sessions.remove(sessionId);
        log.info("Deleted session: {}", sessionId);
    }
}
