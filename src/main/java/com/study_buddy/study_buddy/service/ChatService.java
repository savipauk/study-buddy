package com.study_buddy.study_buddy.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatService {

    @Value("${spring.ai.openai.chat.api-key}")
    private String apiKey;

    @Value("${spring.ai.openai.chat.base-url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public ChatService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getChatResponse(String userMessage) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.set("Content-Type", "application/json");

        // Kreiraj tijelo zahtjeva
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", List.of(
                Map.of("role", "user", "content", userMessage)
        ));
        requestBody.put("max_tokens", 50);
        requestBody.put("temperature", 0.7);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, entity, Map.class);

            // Parsiraj odgovor
            if (response.getBody() != null && response.getBody().containsKey("choices")) {
                Map choice = (Map) ((List) response.getBody().get("choices")).get(0);
                Map message = (Map) choice.get("message");
                return (String) message.get("content");
                // return (String) choice.get("text");
            } else {
                return "Ne mogu trenutno odgovoriti. Pokušajte ponovno kasnije.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Dogodila se greška pri komunikaciji s OpenAI API-jem.";
        }
    }
}

