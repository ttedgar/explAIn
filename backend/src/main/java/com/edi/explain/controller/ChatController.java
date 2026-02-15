package com.edi.explain.controller;

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
    public ResponseEntity<Map<String, String>> sendMessage(
            @PathVariable String sessionId,
            @RequestBody Map<String, String> request) {
        try {
            String userMessage = request.get("message");
            if (userMessage == null || userMessage.isBlank()) {
                return ResponseEntity.badRequest().body(Map.of(
                    "error", "Message is required"
                ));
            }

            String aiResponse = sessionService.sendMessage(sessionId, userMessage);

            return ResponseEntity.ok(Map.of(
                "response", aiResponse,
                "sessionId", sessionId
            ));
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
    public ResponseEntity<?> getSession(@PathVariable String sessionId) {
        return sessionService.getSession(sessionId)
            .map(session -> ResponseEntity.ok(Map.of(
                "sessionId", session.getSessionId(),
                "fileName", session.getFileName(),
                "messageCount", session.getConversationHistory().size(),
                "createdAt", session.getCreatedAt().toString()
            )))
            .orElse(ResponseEntity.notFound().build());
    }
}
