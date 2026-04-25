package com.ai.dashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.dashboard.entities.DataSet;

public interface DatasetRepository extends JpaRepository<DataSet,Long>{

}
