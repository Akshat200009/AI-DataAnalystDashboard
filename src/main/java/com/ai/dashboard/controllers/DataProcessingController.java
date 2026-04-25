package com.ai.dashboard.controllers;

import com.ai.dashboard.entities.DataSet;
import com.ai.dashboard.services.DataProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/data")
public class DataProcessingController {

    @Autowired
    private DataProcessingService service;

    // FILTER API
    @GetMapping("/filter")
    public List<DataSet> filter(
            @RequestParam String column,
            @RequestParam String value) {

        return service.filterByColumn(column, value);
    }

    // GROUP API
    @GetMapping("/group")
    public Map<String, List<DataSet>> group(
            @RequestParam String column) {

        return service.groupByColumn(column);
    }

    // COUNT API
    @GetMapping("/count")
    public Map<String, Long> count(
            @RequestParam String column) {

        return service.countByColumn(column);
    }
    // SUM
    @GetMapping("/sum/experience")
    public double totalExperience() {
        return service.getTotalExperience();
    }

    // AVG
    @GetMapping("/avg/age")
    public double averageAge() {
        return service.getAverageAge();
    }

    // AVG BY COLUMN
    @GetMapping("/avg/experience")
    public Map<String, Double> avgExperienceByColumn(@RequestParam String column) {
        return service.avgExperienceByColumn(column);
    }
}