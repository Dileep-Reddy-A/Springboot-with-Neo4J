package com.energybox.backendcodingchallenge;

import com.energybox.backendcodingchallenge.controller.SensorController;
import com.energybox.backendcodingchallenge.domain.Sensor;
import com.energybox.backendcodingchallenge.service.SensorService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;


@ExtendWith(SpringExtension.class)
@WebMvcTest(value = SensorController.class)
public class SensorControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private SensorService sensorService;
	
	private ObjectMapper objectMapper= new ObjectMapper();
  

   @Test
   @DisplayName("Test findAll()")
   public void findAllSensors() throws Exception {
	  List<Sensor> output = Arrays.asList(new Sensor());
      when(sensorService.findAllSensors()).thenReturn(output);
      mockMvc.perform(get("/sensor/findall")
              .accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.size()").value(output.size()));
              //.andExpect(jsonPath("$.id").value(id));
      
      verify(sensorService,times(1)).findAllSensors();
   }
   
   @Test
   @DisplayName("Test add sensor")
   public void testAddSensor() throws Exception {
	  Sensor s= new Sensor(1,"testing",null);
      when(sensorService.createSeansor(any(Sensor.class))).thenReturn(s);
      ResultActions response = mockMvc.perform(post("/sensor/add")
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(s)));
      response.andExpect(status().isCreated())
              .andExpect(jsonPath("$.id").value(1));
      
      verify(sensorService,times(1)).createSeansor(any(Sensor.class));
   
   }
   
   @Test
   @DisplayName("Test find by type()")
   public void testFindByType() throws Exception {
	  List<Sensor> output = Arrays.asList(new Sensor());
      when(sensorService.findbyType(any(String.class))).thenReturn(output);
      mockMvc.perform(get("/sensor/findType/{type}","temperature")
              .accept(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.size()").value(output.size()));
              //.andExpect(jsonPath("$.id").value(id));
      
      verify(sensorService,times(1)).findbyType(any(String.class));
   }
   
  


}