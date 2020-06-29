package com.meru.promotion.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
import com.meru.promotion.model.Promotion;
import com.meru.promotion.service.PromotionService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PromotionController.class)
@ImportAutoConfiguration(RefreshAutoConfiguration.class)
class PromotionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PromotionService promotionService;
	
	@Test
	void testGetAllPromotion() throws Exception {
		List<Promotion> list_of_Promotion = new ArrayList<>();
		
		Promotion promotion1 = new Promotion();
		promotion1.setId(Long.valueOf(1));
		promotion1.setCategory("Electronics");
		promotion1.setDiscount("30% Discount");
		list_of_Promotion.add(promotion1);
		
		Promotion promotion2 = new Promotion();
		promotion2.setId(Long.valueOf(2));
		promotion2.setCategory("Mobiles");
		promotion2.setDiscount("30% Discount");
		list_of_Promotion.add(promotion2);
		
		Mockito.when(promotionService.getAllPromotions()).thenReturn(list_of_Promotion);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/promotion/");
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String expectedResponse = this.mapToJson(list_of_Promotion);
		String outputResponse = result.getResponse().getContentAsString();
		
		assertThat(outputResponse).isEqualTo(expectedResponse);
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}

	@Test
	void testGetPromotionById() throws Exception {
		Promotion promotion = new Promotion();
		promotion.setId(Long.valueOf(1));
		promotion.setCategory("Electronics");
		promotion.setDiscount("30% Discount");
		
		Mockito.when(promotionService.getPromotionById(Mockito.anyLong())).thenReturn(promotion);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/promotion/1");
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String expectedResponse = this.mapToJson(promotion);
		String outputResponse = result.getResponse().getContentAsString();
		
		assertThat(outputResponse).isEqualTo(expectedResponse);
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}
	
	@Test
	void testGetPromotionByCategory() throws Exception {
		List<Promotion> list_of_Promotion = new ArrayList<>();
		
		Promotion promotion = new Promotion();
		promotion.setId(Long.valueOf(1));
		promotion.setCategory("Electronics");
		promotion.setDiscount("30% Discount");
		list_of_Promotion.add(promotion);
		
		Mockito.when(promotionService.getPromotionByCategory(Mockito.anyString())).thenReturn(list_of_Promotion);
	
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/promotion/category/Electronics");
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String expectedResponse = this.mapToJson(list_of_Promotion);
		String outputResponse = result.getResponse().getContentAsString();
		
		assertThat(outputResponse).isEqualTo(expectedResponse);
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());	
	}
	
	@Test
	void testAddPromotion() throws Exception {
		Promotion promotion = new Promotion();
		promotion.setId(Long.valueOf(1));
		promotion.setCategory("Electronics");
		promotion.setDiscount("30% Discount");
		
		String expectedResponse = this.mapToJson(promotion);
		
		Mockito.when(promotionService.addPromotion(Mockito.any(Promotion.class))).thenReturn(promotion);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/promotion/create")
				.content(expectedResponse)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String outputResponse = result.getResponse().getContentAsString();
		
		assertThat(outputResponse).isEqualTo(expectedResponse);
		assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
	}
	
	@Test
	void testUpdatePromotion() throws Exception {
		Promotion promotion = new Promotion();
		promotion.setId(Long.valueOf(1));
		promotion.setCategory("Electronics");
		promotion.setDiscount("30% Discount");
		
		String expectedResponse = this.mapToJson(promotion);
		
		Mockito.when(promotionService.updatePromotion(Mockito.anyLong(), Mockito.any(Promotion.class))).thenReturn(promotion);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/promotion/update/1")
				.contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
				.content(expectedResponse);
		
		MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();
		
		String outputResponse = result.getResponse().getContentAsString();
		
		assertThat(outputResponse).isEqualTo(expectedResponse);
		assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
	}
	
	@Test
	void testDeletePromotionById() throws Exception {
		Mockito.when(promotionService.deletePromotion(Mockito.anyLong())).thenReturn("Success");
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/promotion/delete/1");
        
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
