package org.example;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.*;

@Service
public class GeminiService {
    // Correct way: The app asks Render for the key at runtime
    private final String API_KEY = System.getenv("GEMINI_API_KEY");

    // ✅ FIXED: Updated from deprecated gemini-1.5-flash to gemini-2.5-flash
    private final String URL = "https://generativelanguage.googleapis.com/v1/models/gemini-2.5-flash:generateContent?key=" + API_KEY;

    public String getAiResponse(String userPrompt) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String structuredPrompt = "You are a helpful WhatsApp Chatbot. " +
                "Keep responses concise, use emojis occasionally, and be friendly. " +
                "User says: " + userPrompt;

        Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", structuredPrompt)
                        ))
                )
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(URL, entity, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                List<?> candidates = (List<?>) response.getBody().get("candidates");
                if (candidates != null && !candidates.isEmpty()) {
                    Map<?, ?> firstCandidate = (Map<?, ?>) candidates.get(0);
                    Map<?, ?> content = (Map<?, ?>) firstCandidate.get("content");
                    List<?> parts = (List<?>) content.get("parts");
                    Map<?, ?> textPart = (Map<?, ?>) parts.get(0);
                    return textPart.get("text").toString();
                }
            }
            return "I heard you, but I couldn't process the answer. 😶";
        } catch (Exception e) {
            System.err.println("Gemini Error: " + e.getMessage());
            return "Sorry, I'm having trouble thinking right now. 🤖 (" + e.getMessage() + ")";
        }
    }
}