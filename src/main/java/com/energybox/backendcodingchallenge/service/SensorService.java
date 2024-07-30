package com.energybox.backendcodingchallenge.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.neo4j.ogm.utils.StringUtils;
import org.springframework.stereotype.Service;

import com.energybox.backendcodingchallenge.domain.Sensor;
import com.energybox.backendcodingchallenge.exception.CustomDataNotFoundException;
import com.energybox.backendcodingchallenge.exception.CustomMissingMandatoryFieldsException;
import com.energybox.backendcodingchallenge.exception.DataAlreadyExistsException;
import com.energybox.backendcodingchallenge.repository.SensorRepository;
import com.energybox.backendcodingchallenge.utils.ApplicationConstants;

@Service
public class SensorService {
	
	private SensorRepository sensorRepo;
	
	

	public SensorService(SensorRepository sensorRepo) {
		super();
		this.sensorRepo = sensorRepo;
	}

	public Sensor createSeansor(Sensor sensor) {
		if(Objects.isNull(sensor) || Objects.isNull(sensor.getId()) 
				|| Objects.isNull(sensor.getName()) || StringUtils.isBlank(sensor.getName())) {
			
			if(Objects.isNull(sensor)) throw new CustomMissingMandatoryFieldsException(ApplicationConstants.REQUEST_BODY_CANNOT_BE_EMPTY);
			else {
				String expMsg =ApplicationConstants.MANDATORY_FIELDS_CANNOT_BE_EMPTY;
				if(Objects.isNull(sensor.getId())) expMsg+="ID";
				if(Objects.isNull(sensor.getName()) || StringUtils.isBlank(sensor.getName())) expMsg+="Name";
				throw new CustomMissingMandatoryFieldsException(expMsg);
			}
		}

		Optional<Sensor> sensorResp = this.sensorRepo.findById(sensor.getId());
		if(sensorResp.isPresent()) 
			throw new DataAlreadyExistsException(ApplicationConstants.DATA_ALREADY_EXISTS+sensor.getId());
		return this.sensorRepo.save(sensor);
	}
	
	public List<Sensor> findAllSensors() {
		return this.sensorRepo.findAll();
	}
	
	public Sensor findSensorbyId(Integer id) {
		
		Optional<Sensor> sensor= this.sensorRepo.findById(id);
		if(sensor.isPresent()) return sensor.get();
		else throw new CustomDataNotFoundException(id);
	}

	
	public List<Sensor> findbyType(String type) {
		return this.sensorRepo.findbyType(type);
	}
}
