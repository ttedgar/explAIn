package com.edi.explain.service;

import com.edi.explain.model.ChatMessage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@ConditionalOnProperty(name = "ai.provider", havingValue = "gemini", matchIfMissing = true)
public class GeminiService implements AIService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private static final String GEMINI_API_URL =
        "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent";

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String sendMessage(String systemPrompt, List<ChatMessage> conversationHistory, String userMessage) {
        try {
            Map<String, Object> payload = buildPayload(systemPrompt, conversationHistory, userMessage);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

            String url = GEMINI_API_URL + "?key=" + apiKey;
            ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                String.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return parseGeminiResponse(response.getBody());
            } else {
                log.error("Gemini API returned non-OK status: {}", response.getStatusCode());
                return "Sorry, I encountered an error processing your request.";
            }
        } catch (Exception e) {
            log.error("Error calling Gemini API", e);
            return "Sorry, I encountered an error: " + e.getMessage();
        }
    }

    private Map<String, Object> buildPayload(String systemPrompt, List<ChatMessage> history, String userMessage) {
        List<Map<String, Object>> contents = new ArrayList<>();

        contents.add(Map.of(
            "role", "user",
            "parts", List.of(Map.of("text", systemPrompt))
        ));

        for (ChatMessage msg : history) {
            contents.add(Map.of(
                "role", msg.getRole(),
                "parts", List.of(Map.of("text", msg.getContent()))
            ));
        }

        contents.add(Map.of(
            "role", "user",
            "parts", List.of(Map.of("text", userMessage))
        ));

        Map<String, Object> payload = new HashMap<>();
        payload.put("contents", contents);

        return payload;
    }

    private String parseGeminiResponse(String responseBody) {
        try {
            JsonNode root = objectMapper.readTree(responseBody);
            JsonNode candidates = root.path("candidates");

            if (candidates.isArray() && !candidates.isEmpty()) {
                JsonNode content = candidates.get(0).path("content");
                JsonNode parts = content.path("parts");

                if (parts.isArray() && !parts.isEmpty()) {
                    return parts.get(0).path("text").asText();
                }
            }

            log.warn("Unexpected Gemini response structure: {}", responseBody);
            return "Sorry, I couldn't parse the response from the AI.";
        } catch (Exception e) {
            log.error("Error parsing Gemini response", e);
            return "Sorry, I encountered an error parsing the AI response.";
        }
    }
}
