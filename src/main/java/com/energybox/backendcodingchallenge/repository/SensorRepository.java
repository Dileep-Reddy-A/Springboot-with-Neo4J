package com.energybox.backendcodingchallenge.repository;

import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.energybox.backendcodingchallenge.domain.Sensor;

@Repository
public interface SensorRepository extends Neo4jRepository< Sensor, Integer>{

	
	@Query("MATCH (sensor:Sensor) WHERE ANY(type IN sensor.types WHERE type = $type) RETURN sensor")
	List<Sensor> findbyType(@Param("type") String type);
	
}

