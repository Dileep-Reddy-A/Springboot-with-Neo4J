package com.energybox.backendcodingchallenge.controller;

import com.energybox.backendcodingchallenge.domain.Gateway;
import com.energybox.backendcodingchallenge.domain.Sensor;
import com.energybox.backendcodingchallenge.exception.CustomDataNotFoundException;
import com.energybox.backendcodingchallenge.service.GatewayService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@RestController
@RequestMapping( value = "/gateways" )
public class GatewayController {

    private final GatewayService service;

    public GatewayController( GatewayService service ) {
        this.service = service;
    }

    @ApiOperation( value = "create a gateway", response = Gateway.class )
    @PostMapping( value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<Gateway> create(@RequestBody Gateway gateway)
    		throws IOException, InterruptedException {
    	
    	
    	Gateway gatewayResp= this.service.createGateway(gateway);

        return new ResponseEntity<Gateway>( gatewayResp, HttpStatus.CREATED );
    }
    
   // @ApiOperation( value = "create a gateway", response = List>.class )
    @GetMapping( value = "/findall", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<List<Gateway>> findAll()
    		throws IOException, InterruptedException {
    	
    	
    	List<Gateway> gateways= this.service.findAllGateways();
    	
        return ResponseEntity.ok().body(gateways);
    }
    
    @DeleteMapping( value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<String> deleteGateway(@PathVariable Integer id)
    		throws IOException, InterruptedException {
    	
    	this.service.deleteById(id);
    	
        return ResponseEntity.ok().body("Gateway with id:" +id+" deleted succesfully");
    }
    
    @GetMapping( value = "/getlinkedsensors/{id}", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<List<Sensor>> findAllSensorsLinked(@PathVariable Integer id)
    		throws IOException, InterruptedException {
    
    	Optional<Gateway> gateway= this.service.findbyId(id);

    	if(gateway.isPresent()) {
    		return ResponseEntity.ok().body(gateway.get().getSensors());
    	}
    	else throw new CustomDataNotFoundException(id);
        
    
    }
    
    @PatchMapping( value = "/addsensor/{id}", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<Gateway> findAllSensorsLinked(@PathVariable Integer id, @RequestBody Sensor sensor)
    		throws IOException, InterruptedException {
    	
    
    	Gateway gateway= this.service.addSensor(id,sensor);
   
    	if(Objects.nonNull(gateway)) {
    		return ResponseEntity.ok().body(gateway);
    	}
        else throw new CustomDataNotFoundException(id);
    
    }
    
    @GetMapping( value = "/getGatewayBySensorType/{type}", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<List<Gateway>> findAllSensorsLinked(@PathVariable String type)
    		throws IOException, InterruptedException {
    	
    	return ResponseEntity.ok(this.service.getSensorbyType(type));
    
    }
    
    
}
