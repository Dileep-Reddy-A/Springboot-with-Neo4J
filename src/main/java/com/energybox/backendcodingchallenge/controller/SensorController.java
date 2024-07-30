package com.energybox.backendcodingchallenge.controller;


import java.io.IOException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.energybox.backendcodingchallenge.domain.Sensor;
import com.energybox.backendcodingchallenge.service.SensorService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping( value = "/sensor" )
public class SensorController {

	private final SensorService sensorService;

    public SensorController( SensorService service ) {
        this.sensorService = service;
    }

    @ApiOperation( value = "create a sensor", response = Sensor.class )
    @PostMapping( value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<Sensor> createSensor(@RequestBody Sensor sensor) {
   
    	Sensor output=this.sensorService.createSeansor(sensor);
        return new ResponseEntity<>(output,  HttpStatus.CREATED );
    }
    
    @GetMapping( value = "/findall", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<List<Sensor>> findAll()
    		throws IOException, InterruptedException {
    	
    	List<Sensor> sensors= this.sensorService.findAllSensors();
    	
        return ResponseEntity.ok().body(sensors);
    }
    
    @GetMapping( value = "/findType/{type}", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<List<Sensor>> findByType(@PathVariable String type)
    		throws IOException, InterruptedException {
    	
    	
    	List<Sensor> sensors= this.sensorService.findbyType(type);
 
        return ResponseEntity.ok().body(sensors);
    }
}
