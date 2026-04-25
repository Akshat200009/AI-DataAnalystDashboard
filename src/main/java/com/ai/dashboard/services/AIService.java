package com.ai.dashboard.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class AIService {

    private final String API_URL = "https://api.groq.com/openai/v1/chat/completions";

    public String askAI(String question) {

        String apiKey = System.getenv("GROQ_API_KEY");

        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> request = new HashMap<>();

        request.put("model", "llama3-8b-8192");

        request.put("messages", new Object[]{
                Map.of("role", "user", "content", question)
        });

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + apiKey);
        headers.put("Content-Type", "application/json");

        org.springframework.http.HttpEntity<Map<String, Object>> entity =
                new org.springframework.http.HttpEntity<>(request,
                        new org.springframework.http.HttpHeaders() {{
                            set("Authorization", "Bearer " + apiKey);
                            set("Content-Type", "application/json");
                        }});

        try {
            String response = restTemplate.postForObject(API_URL, entity, String.class);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return "{}";
        }
    }
}