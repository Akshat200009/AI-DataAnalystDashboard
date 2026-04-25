package com.ai.dashboard.entities;

import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DataSet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ElementCollection
	private Map<String, String> data;

	public void setData(Map<String, String> data) {
		this.data = data;
	}

	@Column
	private String filename;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public Map<String, String> getData() {
		return data;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

}
