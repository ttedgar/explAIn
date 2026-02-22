package com.edi.explain.dto;

public record SessionResponse(String sessionId, String fileName, int messageCount, String createdAt) {}
