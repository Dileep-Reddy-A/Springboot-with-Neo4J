package com.energybox.backendcodingchallenge.domain;



import java.util.List;
import org.neo4j.ogm.annotation.NodeEntity;
import org.springframework.data.annotation.Id;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Gateway {
	
	
	@Id 
	private Integer id;
	
	private String name;
	
	@Relationship(type = "CONNECTED_TO",direction = Relationship.Direction.INCOMING)
	private List<Sensor> sensors;
	
	
	public List<Sensor> getSensors() {
		return sensors;
	}

	public Gateway(Integer id, String name, List<Sensor> sensors) {
		super();
		this.id = id;
		this.name = name;
		this.sensors = sensors;
	}

	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}

	
	public Gateway() {
		super();
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

	@Override
	public String toString() {
		return "Gateway [id=" + id + ", name=" + name + "]";
	}
	
	
	
}

