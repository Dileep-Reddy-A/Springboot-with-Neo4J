package com.energybox.backendcodingchallenge;

import static org.mockito.ArgumentMatchers.any;

import com.energybox.backendcodingchallenge.domain.Sensor;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.MockitoJUnitRunner;
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
import java.util.Optional;



import com.energybox.backendcodingchallenge.controller.GatewayController;
import com.energybox.backendcodingchallenge.domain.Gateway;
import com.energybox.backendcodingchallenge.service.GatewayService;



@ExtendWith(SpringExtension.class)
@WebMvcTest(value = GatewayController.class)
public class GatewayControllerTest {
	
	    @Autowired
		private MockMvc mockMvc;
		
		@MockBean
		private GatewayService gatewayService;
		
		private ObjectMapper objectMapper= new ObjectMapper();
	  
	
	   @Test
	   @DisplayName("Test findAll()")
	   public void findAllGateways() throws Exception {
		  List<Gateway> output = Arrays.asList(new Gateway());
	      when(gatewayService.findAllGateways()).thenReturn(output);
	      mockMvc.perform(get("/gateways/findall")
	              .accept(MediaType.APPLICATION_JSON))
	              .andExpect(status().isOk())
	              .andExpect(jsonPath("$.size()").value(output.size()));
	              //.andExpect(jsonPath("$.id").value(id));
	      
	      verify(gatewayService,times(1)).findAllGateways();
	   }
	   
	   @Test
	   @DisplayName("Test add sensor")
	   public void testAddSensor() throws Exception {
		  Gateway g= new Gateway(1,"testing",null);
		  
	      when(gatewayService.createGateway(any(Gateway.class))).thenReturn(g);
	      ResultActions response = mockMvc.perform(post("/gateways/add")
	              //.param("id", String.valueOf(employeeId))
	              .contentType(MediaType.APPLICATION_JSON)
	              .content(objectMapper.writeValueAsString(g)));
	      response.andExpect(status().isCreated())
	              .andExpect(jsonPath("$.id").value(1));
	      
	      verify(gatewayService,times(1)).createGateway(any(Gateway.class));
	   
	   }
	   

	   @Test
	   @DisplayName("Test findAll linked sensors ")
	   public void testfindAllLinnkedSensors() throws Exception {
		   
		   Gateway g= new Gateway(1,"testing",List.of(new Sensor()));
	      when(gatewayService.findbyId(any(Integer.class))).thenReturn(Optional.of(g));
	      mockMvc.perform(get("/gateways/getlinkedsensors/{id}",1)
	              .accept(MediaType.APPLICATION_JSON))
	              .andExpect(status().isOk())
	              .andExpect(jsonPath("$.size()").value(g.getSensors().size()));
	              //.andExpect(jsonPath("$.id").value(id));
	      
	      verify(gatewayService,times(1)).findbyId(any(Integer.class));
	   }

	   @Test
	   @DisplayName("Test findAll linked sensors ")
	   public void testfindAllLinnkedSensorsNegative() throws Exception {
		   
		 
	      when(gatewayService.findbyId(any(Integer.class))).thenReturn(Optional.empty());
	      mockMvc.perform(get("/gateways/getlinkedsensors/{id}",1)
	              .accept(MediaType.APPLICATION_JSON))
	              .andExpect(status().isBadRequest());
	      
	      verify(gatewayService,times(1)).findbyId(any(Integer.class));
	   }
	   
	   
	   

	   @Test
	   @DisplayName("Test add sensors ")
	   public void testAddSensorsNegative() throws Exception {
		   
		 
		  when(gatewayService.addSensor(any(Integer.class),any(Sensor.class))).thenReturn(null);
		   
	      mockMvc.perform(patch("/gateways/addsensor/{id}",1)
	              .accept(MediaType.APPLICATION_JSON))
	              .andExpect(status().isBadRequest());
	      
	      verify(gatewayService,times(0)).addSensor(any(Integer.class),any(Sensor.class));
	   }

	   
	  

}
