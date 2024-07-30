package com.energybox.backendcodingchallenge;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.energybox.backendcodingchallenge.domain.Sensor;
import com.energybox.backendcodingchallenge.exception.CustomDataNotFoundException;
import com.energybox.backendcodingchallenge.exception.CustomMissingMandatoryFieldsException;
import com.energybox.backendcodingchallenge.exception.DataAlreadyExistsException;
import com.energybox.backendcodingchallenge.repository.SensorRepository;
import com.energybox.backendcodingchallenge.service.SensorService;
import com.energybox.backendcodingchallenge.utils.ApplicationConstants;

@ExtendWith(SpringExtension.class)
public class SensorServiceTest {
	
	@Mock
	private SensorRepository sensorRepo;

	@InjectMocks
	private SensorService sensorService;
	
	
	@Test
    @DisplayName("Test create sensor")
    public void testCreateSnsor() throws Exception {
	   
		
        Sensor s= new Sensor(1,"testing",null);
		
        when(sensorRepo.findById(1)).thenReturn(Optional.empty());
        when(sensorRepo.save(s)).thenReturn(s);
		//When
        Sensor output = sensorService.createSeansor(s);

		//Then
		assertEquals(s,output);
		verify(sensorRepo,times(1)).save(any(Sensor.class));
		
    }
	@Test
    @DisplayName("Test create sensor")
    public void testCreateSnsorNegative() throws DataAlreadyExistsException {
	   
		
        Sensor s= new Sensor(1,"testing",null);
		
        when(sensorRepo.findById(1)).thenReturn(Optional.of(s));
        when(sensorRepo.save(s)).thenReturn(s);
		//When
        
        DataAlreadyExistsException exception= assertThrows(DataAlreadyExistsException.class, ()->{
			sensorService.createSeansor(s);
		});
        assertEquals(exception.getMessage(), ApplicationConstants.DATA_ALREADY_EXISTS+1);
		verify(sensorRepo,times(0)).save(any(Sensor.class));
		
    }
	
	@Test
    @DisplayName("Test create sensor")
    public void testCreateSnsorNegative_2() throws CustomMissingMandatoryFieldsException {
	   
		
        CustomMissingMandatoryFieldsException exception= assertThrows(CustomMissingMandatoryFieldsException.class, ()->{
			sensorService.createSeansor(null);
		});
        assertEquals(exception.getMessage(), ApplicationConstants.REQUEST_BODY_CANNOT_BE_EMPTY);
		verify(sensorRepo,times(0)).save(any(Sensor.class));
		
    }
	
	@Test
    @DisplayName("Test create sensor")
    public void testCreateSnsorNegative_3() throws CustomMissingMandatoryFieldsException {
	   
		
        Sensor s= new Sensor(1,null,null);
		
        CustomMissingMandatoryFieldsException exception= assertThrows(CustomMissingMandatoryFieldsException.class, ()->{
			sensorService.createSeansor(s);
		});
        assertEquals(exception.getMessage(), ApplicationConstants.MANDATORY_FIELDS_CANNOT_BE_EMPTY+"Name");
		verify(sensorRepo,times(0)).save(any(Sensor.class));
		
    }
	
	@Test
    @DisplayName("Test find all sensors")
    public void testfinaAll() throws Exception {
	   
		List<Sensor> expectation = Arrays.asList(new Sensor());
        
		
        when(sensorRepo.findAll()).thenReturn(expectation);
        
		//When
        List<Sensor> output = sensorService.findAllSensors();

		//Then
		assertEquals(expectation,output);
		verify(sensorRepo,times(1)).findAll();
		
    }
	
	@Test
    @DisplayName("Test find by id ")
    public void testfindbyId() throws Exception {
	   
		Sensor expectation = new Sensor();
        
		
        when(sensorRepo.findById(any(Integer.class))).thenReturn(Optional.of(expectation));
        
		//When
        Sensor output = sensorService.findSensorbyId(1);

		//Then
		assertEquals(expectation,output);
		verify(sensorRepo,times(1)).findById(any(Integer.class));
		
    }
	
	@Test
    @DisplayName("Test find by id negative ")
    public void testfindbyIdNegative() throws CustomDataNotFoundException {
	   
		
		
        when(sensorRepo.findById(any(Integer.class))).thenReturn(Optional.empty());
        
		
		//Then
        CustomDataNotFoundException exception= assertThrows(CustomDataNotFoundException.class, ()->{
        	sensorService.findSensorbyId(1);
		});
        assertEquals(exception.getMessage(), "Data with id:1 not found in DB");
		verify(sensorRepo,times(1)).findById(any(Integer.class));
		
    }
	
	@Test
    @DisplayName("Test find by type ")
    public void testfindbyType() throws Exception {
	   
		List<Sensor> expectation = Arrays.asList(new Sensor());
        
		
        when(sensorRepo.findbyType(any(String.class))).thenReturn(expectation);
        
        String type = "temperature";
		//When
        List<Sensor> output = sensorService.findbyType(type);

		//Then
		assertEquals(expectation,output);
		verify(sensorRepo,times(1)).findbyType(type);
		
    }
	
	
}
