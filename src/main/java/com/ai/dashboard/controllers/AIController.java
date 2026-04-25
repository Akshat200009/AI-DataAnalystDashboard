package com.ai.dashboard.controllers;

import com.ai.dashboard.services.AIService;
import com.ai.dashboard.services.DataProcessingService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "http://localhost:5173")
public class AIController {

    @Autowired
    private AIService aiService;

    @Autowired
    private DataProcessingService dataService;
    @GetMapping("/query")
    public Map<String, Object> processQuery(@RequestParam String q) {

        String aiResponse = aiService.askAI(q);

        Map<String, Object> response = new HashMap<>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> map = mapper.readValue(aiResponse, Map.class);

            String action = map.get("action");
            String column = map.get("column");

            if ("count".equalsIgnoreCase(action)) {

                Map<String, Long> result = dataService.countByColumn(column);

                String insight = dataService.generateInsight(result);

                response.put("data", result);
                response.put("insight", insight);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }
}