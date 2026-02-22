package com.edi.explain.service;

import com.edi.explain.exception.FileExtractionException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public class TextExtractionService {

    public String extractText(MultipartFile file) throws FileExtractionException {
        try (InputStream inputStream = file.getInputStream()) {
            try {
                BodyContentHandler handler = new BodyContentHandler(-1);
                AutoDetectParser parser = new AutoDetectParser();
                Metadata metadata = new Metadata();
                ParseContext context = new ParseContext();

                parser.parse(inputStream, handler, metadata, context);

                return handler.toString().trim();
            } catch (Exception e) {
                throw new FileExtractionException("Failed to extract text from file: " + e.getMessage(), e);
            }
        } catch (FileExtractionException e) {
            throw e;
        } catch (Exception e) {
            throw new FileExtractionException("Failed to extract text from file: " + e.getMessage(), e);
        }
    }
}
