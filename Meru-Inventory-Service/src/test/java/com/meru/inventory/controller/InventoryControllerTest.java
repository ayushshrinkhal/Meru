package com.meru.inventory.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meru.inventory.model.Inventory;
import com.meru.inventory.service.InventoryService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = InventoryController.class)
@ImportAutoConfiguration(RefreshAutoConfiguration.class)
class InventoryControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private InventoryService inventoryService;
	
	@Test
	void testGetAllInventory() throws Exception {
		List<Inventory> list_of_Inventory = new ArrayList<>();
		
		Inventory inventory1 = new Inventory();
		inventory1.setId(Long.valueOf(1));
		inventory1.setOfflineProductQuantity("10");
		inventory1.setOnlineProductQuantity("50");
		inventory1.setProductId(Long.valueOf(1));
		list_of_Inventory.add(inventory1);
		
		Inventory inventory2 = new Inventory();
		inventory2.setId(Long.valueOf(2));
		inventory2.setOfflineProductQuantity("15");
		inventory2.setOnlineProductQuantity("50");
		inventory2.setProductId(Long.valueOf(2));
		list_of_Inventory.add(inventory2);
		
		Mockito.when(inventoryService.getAllInventory()).thenReturn(list_of_Inventory);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/inventory/");
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String expectedResponse = this.mapToJson(list_of_Inventory);
		String outputResponse = result.getResponse().getContentAsString();
		
		assertThat(outputResponse).isEqualTo(expectedResponse);
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}

	@Test
	void testGetInventoryById() throws Exception {
		Inventory inventory = new Inventory();
		inventory.setId(Long.valueOf(1));
		inventory.setOfflineProductQuantity("10");
		inventory.setOnlineProductQuantity("50");
		inventory.setProductId(Long.valueOf(1));
		
		Mockito.when(inventoryService.findInventoryById(Mockito.anyLong())).thenReturn(inventory);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/inventory/1");
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String expectedResponse = this.mapToJson(inventory);
		String outputResponse = result.getResponse().getContentAsString();
		
		assertThat(outputResponse).isEqualTo(expectedResponse);
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}
	
	@Test
	void testGetInventoryByProductId() throws Exception {
		Inventory inventory = new Inventory();
		inventory.setId(Long.valueOf(1));
		inventory.setOfflineProductQuantity("10");
		inventory.setOnlineProductQuantity("50");
		inventory.setProductId(Long.valueOf(1));
		
		Mockito.when(inventoryService.findInventoryByProductId(Mockito.anyLong())).thenReturn(inventory);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/inventory/store/1");
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String expectedResponse = this.mapToJson(inventory);
		String outputResponse = result.getResponse().getContentAsString();
		
		assertThat(outputResponse).isEqualTo(expectedResponse);
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());	
	}
	
	@Test
	void testAddInventory() throws Exception {
		Inventory inventory = new Inventory();
		inventory.setId(Long.valueOf(1));
		inventory.setOfflineProductQuantity("10");
		inventory.setOnlineProductQuantity("50");
		inventory.setProductId(Long.valueOf(1));
		
		String expectedResponse = this.mapToJson(inventory);
		
		Mockito.when(inventoryService.createInventory(Mockito.any(Inventory.class))).thenReturn(inventory);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/inventory/create")
				.content(expectedResponse)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String outputResponse = result.getResponse().getContentAsString();
		
		assertThat(outputResponse).isEqualTo(expectedResponse);
		assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
	}
	
	@Test
	void testUpdateInventory() throws Exception {
		Inventory inventory = new Inventory();
		inventory.setId(Long.valueOf(1));
		inventory.setOfflineProductQuantity("10");
		inventory.setOnlineProductQuantity("50");
		inventory.setProductId(Long.valueOf(1));
		
		String expectedResponse = this.mapToJson(inventory);
		
		Mockito.when(inventoryService.updateInventory(Mockito.anyLong(), Mockito.any(Inventory.class))).thenReturn(inventory);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/inventory/update/1")
				.contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
				.content(expectedResponse);
		
		MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();
		
		String outputResponse = result.getResponse().getContentAsString();
		
		assertThat(outputResponse).isEqualTo(expectedResponse);
		assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
	}
	
	@Test
	void testDeleteInventoryById() throws Exception {
		Mockito.when(inventoryService.deleteInventoryById(Mockito.anyLong())).thenReturn("Success");
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/inventory/delete/1");
        
        MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();
		
        String outputResponse = result.getResponse().getContentAsString();
        
        assertThat(outputResponse).isEqualTo("Success");
		assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
	}
	
	@Test
	void testDeleteInventoryByProductId() throws Exception {
		Mockito.when(inventoryService.deleteInventoryByProductId(Mockito.anyLong())).thenReturn("Success");
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/inventory/delete/product/1");
        
        MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();
		
        String outputResponse = result.getResponse().getContentAsString();
        
        assertThat(outputResponse).isEqualTo("Success");
		assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
	}
	
	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
	
}
