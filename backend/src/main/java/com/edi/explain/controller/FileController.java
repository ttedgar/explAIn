package com.edi.explain.controller;

import com.edi.explain.model.ChatSession;
import com.edi.explain.service.SessionService;
import com.edi.explain.service.TextExtractionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FileController {

    private final TextExtractionService textExtractionService;
    private final SessionService sessionService;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            if (fileName == null || fileName.isBlank()) {
                return ResponseEntity.badRequest().body(Map.of(
                    "error", "File name is required"
                ));
            }

            String extractedText = textExtractionService.extractText(file);

            ChatSession session = sessionService.createSession(fileName, extractedText);

            log.info("=== FILE UPLOADED ===");
            log.info("File name: {}", fileName);
            log.info("File size: {} bytes", file.getSize());
            log.info("Content type: {}", file.getContentType());
            log.info("Session ID: {}", session.getSessionId());
            log.info("Text length: {} characters", extractedText.length());

            return ResponseEntity.ok(Map.of(
                "message", "File processed successfully",
                "sessionId", session.getSessionId(),
                "fileName", fileName,
                "textLength", String.valueOf(extractedText.length())
            ));
        } catch (Exception e) {
            log.error("Error processing file", e);
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Failed to process file: " + e.getMessage()
            ));
        }
    }
}
