package com.edi.explain.dto;

public record UploadResponse(String message, String sessionId, String fileName, int textLength) {}
