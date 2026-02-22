package com.edi.explain.controller;

import com.edi.explain.dto.UploadResponse;
import com.edi.explain.service.SessionService;
import com.edi.explain.service.TextExtractionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FileController {

    private final TextExtractionService textExtractionService;
    private final SessionService sessionService;

    @PostMapping("/upload")
    public ResponseEntity<UploadResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.isBlank()) {
            throw new IllegalArgumentException("File name is required");
        }

        String extractedText = textExtractionService.extractText(file);

        var session = sessionService.createSession(fileName, extractedText);

        log.info("=== FILE UPLOADED ===");
        log.info("File name: {}", fileName);
        log.info("File size: {} bytes", file.getSize());
        log.info("Content type: {}", file.getContentType());
        log.info("Session ID: {}", session.getSessionId());
        log.info("Text length: {} characters", extractedText.length());

        return ResponseEntity.ok(new UploadResponse(
            "File processed successfully",
            session.getSessionId(),
            fileName,
            extractedText.length()
        ));
    }
}
