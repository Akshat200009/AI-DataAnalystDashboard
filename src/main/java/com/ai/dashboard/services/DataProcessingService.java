package com.ai.dashboard.services;

import com.ai.dashboard.entities.DataSet;
import com.ai.dashboard.repository.DatasetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DataProcessingService {

    @Autowired
    private DatasetRepository repository;

    // FILTER DATA
    public List<DataSet> filterByColumn(String column, String value) {
        List<DataSet> data = repository.findAll();

        return data.stream()
                .filter(d -> value.equalsIgnoreCase(d.getData().get(column)))
                .collect(Collectors.toList());
    }

    // GROUP BY COLUMN
    public Map<String, List<DataSet>> groupByColumn(String column) {
        List<DataSet> data = repository.findAll();

        return data.stream()
                .collect(Collectors.groupingBy(d -> d.getData().get(column)));
    }

    //️COUNT BY COLUMN (Aggregation)
    public Map<String, Long> countByColumn(String column) {

        List<DataSet> data = repository.findAll();

        return data.stream()
                .collect(Collectors.groupingBy(
                        d -> {
                            Map<String, String> map = d.getData();

                            // If whole map is null
                            if (map == null) return "UNKNOWN";

                            // Get value safely
                            String value = map.get(column);

                            // If value is null or empty
                            if (value == null || value.trim().isEmpty()) {
                                return "UNKNOWN";
                            }

                            return value;
                        },
                        Collectors.counting()
                ));
    }    
    public double getTotalExperience() {

        List<DataSet> data = repository.findAll();

        return data.stream()
                .map(d -> d.getData().get("ExperienceInCurrentDomain"))
                .filter(Objects::nonNull)
                .mapToDouble(Double::parseDouble)
                .sum();
    }
    public double getAverageAge() {

        List<DataSet> data = repository.findAll();

        return data.stream()
                .map(d -> d.getData().get("Age"))
                .filter(Objects::nonNull)
                .mapToDouble(Double::parseDouble)
                .average()
                .orElse(0.0);
    }
    public Map<String, Double> avgExperienceByColumn(String column) {

        List<DataSet> data = repository.findAll();

        return data.stream()
                .collect(Collectors.groupingBy(
                        d -> d.getData().getOrDefault(column, "UNKNOWN"),
                        Collectors.averagingDouble(d -> {
                            String val = d.getData().get("ExperienceInCurrentDomain");
                            return val != null ? Double.parseDouble(val) : 0.0;
                        })
                ));
    }
    public Map<String, Double> avgByColumn(String metric, String groupByColumn) {

        List<DataSet> data = repository.findAll();

        return data.stream()
                .filter(d -> d.getData() != null)
                .collect(Collectors.groupingBy(
                        d -> {
                            String key = d.getData().get(groupByColumn);
                            return (key == null || key.isEmpty()) ? "UNKNOWN" : key;
                        },
                        Collectors.averagingDouble(d -> {
                            try {
                                String value = d.getData().get(metric);
                                return value != null ? Double.parseDouble(value) : 0.0;
                            } catch (Exception e) {
                                return 0.0;
                            }
                        })
                ));
    }
    public String generateInsight(Map<String, Long> data) {

        if (data == null || data.isEmpty()) {
            return "No data available.";
        }

        String maxKey = "";
        long maxValue = 0;

        // find max
        for (Map.Entry<String, Long> entry : data.entrySet()) {
            if (entry.getValue() > maxValue) {
                maxValue = entry.getValue();
                maxKey = entry.getKey();
            }
        }

        String insight = "Most employees are " + maxKey + " (" + maxValue + ").";

        // optional: add second highest
        String secondKey = "";
        long secondValue = 0;

        for (Map.Entry<String, Long> entry : data.entrySet()) {
            if (!entry.getKey().equals(maxKey) && entry.getValue() > secondValue) {
                secondValue = entry.getValue();
                secondKey = entry.getKey();
            }
        }

        if (!secondKey.isEmpty()) {
            insight += " Followed by " + secondKey + " (" + secondValue + ").";
        }

        return insight;
    }
}