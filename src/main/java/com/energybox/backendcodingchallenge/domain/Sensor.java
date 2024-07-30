package com.energybox.backendcodingchallenge.domain;

import java.util.List;
import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.data.annotation.Id;

@NodeEntity
public class Sensor {
	
	@Id
	private Integer id;
	
	private String name;
	
	private List<String> types;
	

	public Sensor(Integer id, String name, List<String> types) {
		super();
		this.id = id;
		this.name = name;
		this.types = types;
	}
	
	public Sensor() {
		
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}
	@Override
	public String toString() {
		return "Sensor [id=" + id + ", name=" + name + ", types=" + types + "]";
	}
	
	
	
}
