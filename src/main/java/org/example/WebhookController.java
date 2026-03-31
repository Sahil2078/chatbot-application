package org.example;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    private final GeminiService geminiService;

    // Constructor injection is the best practice!
    public WebhookController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping
    public String receiveMessage(@RequestBody MessageRequest request) {
        // 1. Extract the message from the Postman JSON
        String userMsg = request.getMessage();

        // 2. Log it so you can see it in your IntelliJ console
        System.out.println("LOG: Forwarding to Gemini -> " + userMsg);

        // 3. Validation: Make sure we aren't sending empty text to Google
        if (userMsg == null || userMsg.trim().isEmpty()) {
            return "Please type a message! ⌨️";
        }

        // 4. GET THE REAL RESPONSE
        // This calls your GeminiService which hits the Google API
        return geminiService.getAiResponse(userMsg);
    }
}