package com.meru.promotion.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.meru.promotion.exception.ResourceNotFoundException;
import com.meru.promotion.model.Promotion;
import com.meru.promotion.repository.PromotionRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
class PromotionServiceTest {

	@Autowired
	private PromotionService promotionService;
	
	@MockBean
	private PromotionRepository promotionRepository;
	
	@Test
	void testCreatePromotion() throws ResourceNotFoundException {
		Promotion promotion = new Promotion();
		promotion.setId(Long.valueOf(1));
		promotion.setCategory("Electronics");
		promotion.setDiscount("30% Discount");
		
		Mockito.when(promotionRepository.save(promotion)).thenReturn(promotion);
		assertThat(promotionService.addPromotion(promotion)).isEqualTo(promotion);
	}

	@Test
	void testGetAllPromotion() throws ResourceNotFoundException {
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
		
		Mockito.when(promotionRepository.findAll()).thenReturn(list_of_Promotion);
		assertThat(promotionService.getAllPromotions()).isEqualTo(list_of_Promotion);
	}

	@Test
	void testGetPromotionById() throws ResourceNotFoundException {
		Promotion promotion = new Promotion();
		promotion.setId(Long.valueOf(1));
		promotion.setCategory("Electronics");
		promotion.setDiscount("30% Discount");
		
		Mockito.when(promotionRepository.findById(promotion.getId())).thenReturn(Optional.of(promotion));
		assertThat(promotionService.getPromotionById(promotion.getId())).isEqualTo(promotion);
	}
	
	@Test
	void testGetPromotionByCategory() throws ResourceNotFoundException {
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
		
		Mockito.when(promotionRepository.findAll()).thenReturn(list_of_Promotion);
		assertThat(promotionService.getPromotionByCategory("Electronics").get(0)).isEqualTo(list_of_Promotion.get(0));
	}
	
	@Test
	void testUpdatePromotion() throws ResourceNotFoundException {
		Promotion promotion1 = new Promotion();
		promotion1.setId(Long.valueOf(1));
		promotion1.setCategory("Electronics");
		promotion1.setDiscount("30% Discount");
		
		Promotion promotion2 = new Promotion();
		promotion2.setId(Long.valueOf(1));
		promotion2.setCategory("Electronics");
		promotion2.setDiscount("50% Discount");
		
		Mockito.when(promotionRepository.findById(promotion1.getId())).thenReturn(Optional.of(promotion1));
		Mockito.when(promotionRepository.save(promotion1)).thenReturn(promotion2);
		assertThat(promotionService.updatePromotion(promotion1.getId(), promotion1).getDiscount()).isEqualTo(promotion2.getDiscount());
	}
	
	@Test
	void testDeleteInventoryById() throws ResourceNotFoundException {
		Promotion promotion = new Promotion();
		promotion.setId(Long.valueOf(1));
		promotion.setCategory("Electronics");
		promotion.setDiscount("30% Discount");
		
		Mockito.when(promotionRepository.findById(promotion.getId())).thenReturn(Optional.of(promotion));
		promotionService.deletePromotion(promotion.getId());
		verify(promotionRepository, times(1)).deleteById(promotion.getId());	
	}
	
}
