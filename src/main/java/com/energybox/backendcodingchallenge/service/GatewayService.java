package com.energybox.backendcodingchallenge.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.neo4j.ogm.utils.StringUtils;
import org.springframework.stereotype.Service;

import com.energybox.backendcodingchallenge.domain.Gateway;
import com.energybox.backendcodingchallenge.domain.Sensor;
import com.energybox.backendcodingchallenge.exception.CustomDataNotFoundException;
import com.energybox.backendcodingchallenge.exception.CustomMissingMandatoryFieldsException;
import com.energybox.backendcodingchallenge.exception.DataAlreadyExistsException;
import com.energybox.backendcodingchallenge.repository.GatewayRepository;
import com.energybox.backendcodingchallenge.utils.ApplicationConstants;

@Service
public class GatewayService {
	
	private GatewayRepository gatewayRepo;
	
	
	
	public GatewayService(GatewayRepository gatewayRepo) {
		super();
		this.gatewayRepo = gatewayRepo;
	}



	public Gateway createGateway(Gateway gateway) {
		
		if(Objects.isNull(gateway) || Objects.isNull(gateway.getId()) 
				|| Objects.isNull(gateway.getName())|| StringUtils.isBlank(gateway.getName())) {
			
			if(Objects.isNull(gateway)) throw new CustomMissingMandatoryFieldsException(ApplicationConstants.REQUEST_BODY_CANNOT_BE_EMPTY);
			else {
				String expMsg =ApplicationConstants.MANDATORY_FIELDS_CANNOT_BE_EMPTY;
				if(Objects.isNull(gateway.getId())) expMsg+="ID";
				if(Objects.isNull(gateway.getName())|| StringUtils.isBlank(gateway.getName())) expMsg+="Name";
				throw new CustomMissingMandatoryFieldsException(expMsg);
			}
		}
		
		Optional<Gateway> g = this.gatewayRepo.findById(gateway.getId());
		if(g.isPresent()) 
			throw new DataAlreadyExistsException(ApplicationConstants.DATA_ALREADY_EXISTS+gateway.getId());
		
		return this.gatewayRepo.save(gateway);
	}
	
	public List<Gateway> findAllGateways() {
		return this.gatewayRepo.findAll();
	}
	
	public Optional<Gateway> findbyId(Integer id) {
		return this.gatewayRepo.findById(id);
	}
	
	public void deleteById(Integer id) {
		Optional<Gateway> gateway =this.gatewayRepo.findById(id);
		if(gateway.isPresent()) {
			this.gatewayRepo.deleteById(id);
		}
		else throw new CustomDataNotFoundException(id);
	}
	
	public Gateway addSensor(Integer id,Sensor s) {
		Optional<Gateway> gateway =this.gatewayRepo.findById(id);
		if(gateway.isPresent()) {
			gateway.get().getSensors().add(s);
			return gatewayRepo.save(gateway.get());
		}
		else throw new CustomDataNotFoundException(id);
		
	}
	
	public List<Gateway> getSensorbyType(String s) {
		
		//return gatewayRepo.findbySensorType(s).stream().distinct().collect(Collectors.toList());
		return this.gatewayRepo.findAll().parallelStream()
				.filter(x-> Objects.nonNull(x.getSensors()) && x.getSensors().parallelStream()
						.anyMatch(y->Objects.nonNull(y.getTypes()) && y.getTypes().contains(s)))
				.collect(Collectors.toList());
		
	}
}

