package com.ai.dashboard.services;

import com.ai.dashboard.entities.DataSet;
import com.ai.dashboard.repository.DatasetRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;

@Service
public class DatasetService {

    @Autowired
    private DatasetRepository repository;

    public List<Map<String, String>> parseCSV(MultipartFile file) {

        List<Map<String, String>> dataList = new ArrayList<>();

        try {
            Reader reader = new InputStreamReader(file.getInputStream());

            CSVParser csvParser = new CSVParser(reader,
                    CSVFormat.DEFAULT.builder()
                            .setHeader()
                            .setIgnoreHeaderCase(true)
                            .setTrim(true)
                            .setAllowMissingColumnNames(true)
                            .setIgnoreEmptyLines(true)
                            .setQuote('"')
                            .setEscape('\\')
                            .setIgnoreSurroundingSpaces(true)
                            .build()
            );

            Iterator<CSVRecord> iterator = csvParser.iterator();

            while (true) {
                try {
                    if (!iterator.hasNext()) break;

                    CSVRecord record = iterator.next();

                    Map<String, String> row = new HashMap<>();

                    for (String header : csvParser.getHeaderMap().keySet()) {
                        row.put(header, record.get(header));
                    }

                    dataList.add(row);

                } catch (Exception e) {
                    System.out.println("❌ Skipping bad row: " + e.getMessage());
                }
            }

            return dataList;

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public String savefile(MultipartFile file) {

        List<Map<String, String>> data = parseCSV(file);

        for (Map<String, String> row : data) {
            DataSet ds = new DataSet();
            ds.setData(row);
            repository.save(ds);
        }

        return "File uploaded and data saved successfully!";
    }}