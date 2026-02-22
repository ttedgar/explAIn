package com.edi.explain.service;

import com.edi.explain.model.ChatMessage;

import java.util.List;

public interface AIService {
  String sendMessage(String systemPrompt, List<ChatMessage> conversationHistory, String userMessage);
}
