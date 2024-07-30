package com.energybox.backendcodingchallenge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.energybox.backendcodingchallenge.domain.Gateway;
import com.energybox.backendcodingchallenge.domain.Sensor;
import com.energybox.backendcodingchallenge.exception.CustomDataNotFoundException;
import com.energybox.backendcodingchallenge.exception.CustomMissingMandatoryFieldsException;
import com.energybox.backendcodingchallenge.exception.DataAlreadyExistsException;
import com.energybox.backendcodingchallenge.repository.GatewayRepository;
import com.energybox.backendcodingchallenge.service.GatewayService;
import com.energybox.backendcodingchallenge.utils.ApplicationConstants;

@ExtendWith(SpringExtension.class)
public class GatewayServiceTest {
	
	@Mock
	private GatewayRepository gatewayRepo;

	@InjectMocks
	private GatewayService gatewayService;
	
	
	@Test
    @DisplayName("Test create gateway")
    public void testCreateGateway() throws Exception {
	   
		
        Gateway g= new Gateway(1,"testing",null);
		
        when(gatewayRepo.findById(1)).thenReturn(Optional.empty());
        when(gatewayRepo.save(g)).thenReturn(g);
		//When
        Gateway output = gatewayService.createGateway(g);

		//Then
		assertEquals(g,output);
		verify(gatewayRepo,times(1)).save(any(Gateway.class));
		
    }
	
	@Test
    @DisplayName("Test create gateway")
    public void testCreateGatewayNegative() throws Exception {
	   
		
		Gateway g= new Gateway(1,"testing",null);
		
        when(gatewayRepo.findById(1)).thenReturn(Optional.of(g));
        when(gatewayRepo.save(g)).thenReturn(g);
		//When
        
        DataAlreadyExistsException exception= assertThrows(DataAlreadyExistsException.class, ()->{
			gatewayService.createGateway(g);
		});
        assertEquals(exception.getMessage(), ApplicationConstants.DATA_ALREADY_EXISTS+1);
		verify(gatewayRepo,times(0)).save(any(Gateway.class));
		
    }
	
	@Test
    @DisplayName("Test create sensor")
    public void testCreateGatewayNegative_2() throws CustomMissingMandatoryFieldsException {
	   
		
        CustomMissingMandatoryFieldsException exception= assertThrows(CustomMissingMandatoryFieldsException.class, ()->{
        	gatewayService.createGateway(null);
		});
        assertEquals(exception.getMessage(), ApplicationConstants.REQUEST_BODY_CANNOT_BE_EMPTY);
		verify(gatewayRepo,times(0)).save(any(Gateway.class));
		
    }
	
	@Test
    @DisplayName("Test create sensor")
    public void testCreateGatewayNegative_3() throws CustomMissingMandatoryFieldsException {
	   
		
        Gateway s= new Gateway(1,null,null);
		
        CustomMissingMandatoryFieldsException exception= assertThrows(CustomMissingMandatoryFieldsException.class, ()->{
			gatewayService.createGateway(s);
		});
        assertEquals(exception.getMessage(), ApplicationConstants.MANDATORY_FIELDS_CANNOT_BE_EMPTY+"Name");
		verify(gatewayRepo,times(0)).save(any(Gateway.class));
		
    }
	
	@Test
    @DisplayName("Test find all sensors")
    public void testfinaAll() throws Exception {
	   
		List<Gateway> expectation = Arrays.asList(new Gateway());
        
		
        when(gatewayRepo.findAll()).thenReturn(expectation);
        
		//When
        List<Gateway> output = gatewayService.findAllGateways();

		//Then
		assertEquals(expectation,output);
		verify(gatewayRepo,times(1)).findAll();
		
    }
	
	@Test
    @DisplayName("Test find by id ")
    public void testfindbyId() throws Exception {
	   
		Gateway expectation = new Gateway();
        
		
        when(gatewayRepo.findById(any(Integer.class))).thenReturn(Optional.of(expectation));
        
		//When
        Optional<Gateway> output = gatewayService.findbyId(1);

		//Then
		assertEquals(expectation,output.get());
		verify(gatewayRepo,times(1)).findById(any(Integer.class));
		
    }
	
	@Test
    @DisplayName("Test find by id ")
    public void testAddSensor() throws Exception {
	   
		List<Sensor> sensors= new ArrayList<>();
		sensors.add(new Sensor());
		Gateway expectation = new Gateway(1,"testing",sensors);
        
		
        when(gatewayRepo.findById(any(Integer.class))).thenReturn(Optional.of(expectation));
        when(gatewayRepo.save(any(Gateway.class))).thenReturn(expectation);
        
		//When
        Gateway output = gatewayService.addSensor(1,new Sensor());

		//Then
		assertEquals(expectation,output);
		verify(gatewayRepo,times(1)).findById(any(Integer.class));
		verify(gatewayRepo,times(1)).save(any(Gateway.class));
		
    }
	
	@Test
    @DisplayName("Test find by id ")
    public void testAddSensorNegative() throws Exception {
	   
		
		
        when(gatewayRepo.findById(any(Integer.class))).thenReturn(Optional.empty());
        
		//When
        
        CustomDataNotFoundException exception= assertThrows(CustomDataNotFoundException.class, ()->{
        	gatewayService.addSensor(1,new Sensor());
		});
        assertEquals(exception.getMessage(), "Data with id:1 not found in DB");

		
		verify(gatewayRepo,times(1)).findById(any(Integer.class));
		verify(gatewayRepo,times(0)).save(any(Gateway.class));
		
    }
	
	
}
