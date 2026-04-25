package com.ai.dashboard.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ai.dashboard.services.DatasetService;

@RestController
@RequestMapping("/api")
public class DatasetController {

	@Autowired
	private DatasetService service;

	@PostMapping("/upload")
	public String uploadfile(@RequestParam("file") MultipartFile file) {
		return service.savefile(file);
	}

	@PostMapping("/parse")
	public List<Map<String, String>> parseFile(@RequestParam("file") MultipartFile file) {
		return service.parseCSV(file);
	}

}
