package com.edi.explain.controller;

import com.edi.explain.dto.ChatResponse;
import com.edi.explain.dto.SendMessageRequest;
import com.edi.explain.dto.SessionResponse;
import com.edi.explain.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ChatController {

    private final SessionService sessionService;

    @PostMapping("/{sessionId}")
    public ResponseEntity<?> sendMessage(
            @PathVariable String sessionId,
            @RequestBody SendMessageRequest request) {
        try {
            if (request.message() == null || request.message().isBlank()) {
                return ResponseEntity.badRequest().body(Map.of(
                    "error", "Message is required"
                ));
            }

            String aiResponse = sessionService.sendMessage(sessionId, request.message());

            return ResponseEntity.ok(new ChatResponse(aiResponse, sessionId));
        } catch (IllegalArgumentException e) {
            log.error("Session not found: {}", sessionId);
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        } catch (Exception e) {
            log.error("Error processing chat message", e);
            return ResponseEntity.internalServerError().body(Map.of(
                "error", "Failed to process message: " + e.getMessage()
            ));
        }
    }

    @GetMapping("/session/{sessionId}")
    public ResponseEntity<SessionResponse> getSession(@PathVariable String sessionId) {
        return sessionService.getSession(sessionId)
            .map(session -> ResponseEntity.ok(new SessionResponse(
                session.getSessionId(),
                session.getFileName(),
                session.getConversationHistory().size(),
                session.getCreatedAt().toString()
            )))
            .orElse(ResponseEntity.notFound().build());
    }
}
