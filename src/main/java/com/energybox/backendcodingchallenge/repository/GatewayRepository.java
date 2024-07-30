package com.energybox.backendcodingchallenge.repository;
import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.energybox.backendcodingchallenge.domain.Gateway;


@Repository
public interface GatewayRepository extends Neo4jRepository< Gateway, Integer>{

	@Query("MATCH (g:Gateway)-[:SENSORS]->(s:Sensor)\n"
			+ "WHERE $type IN s.types\n"
			+ "RETURN g;")
	List<Gateway> findbySensorType(@Param("type") String type);
}
