package com.meru.promotion.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.meru.promotion.model.Promotion;

@RunWith(SpringRunner.class)
@DataJpaTest
class PromotionRepositoryTest {

	@Autowired
	private PromotionRepository promotionRepository;
	
	@Test
	void testSaveInventory() {
		Promotion promotion = new Promotion();
		promotion.setId(Long.valueOf(1));
		promotion.setCategory("Electronics");
		promotion.setDiscount("30% Discount");
		
		Promotion savedInDB = promotionRepository.save(promotion);
		
		Promotion getFromDB = promotionRepository.findById(savedInDB.getId()).get(); 
		
		assertThat(getFromDB.getId()).isEqualTo(savedInDB.getId());
		assertThat(getFromDB.getCategory()).isEqualTo(savedInDB.getCategory());
		assertThat(getFromDB.getDiscount()).isEqualTo(savedInDB.getDiscount());
		promotionRepository.deleteById(promotion.getId());
	}

	@Test
	void testFindAll() {
		Promotion promotion = new Promotion();
		promotion.setId(Long.valueOf(2));
		promotion.setCategory("Mobiles");
		promotion.setDiscount("50% Discount");
		
		promotionRepository.save(promotion);
		
		List<Promotion> promotionList = new ArrayList<Promotion>(); 
		promotionRepository.findAll().forEach(promotionList :: add);
		
		assertThat(promotionList.size()).isEqualTo(1);
		
		promotionRepository.deleteById(promotion.getId());
	}
	
	@Test
	void testFindById() {
		Promotion promotion = new Promotion();
		promotion.setId(Long.valueOf(3));
		promotion.setCategory("Furniture");
		promotion.setDiscount("20% Discount");
		
		promotionRepository.save(promotion);
		
		Promotion getFromDB = promotionRepository.findById(promotion.getId()).get(); 
		
		assertThat(getFromDB.getId()).isEqualTo(promotion.getId());
		assertThat(getFromDB.getCategory()).isEqualTo(promotion.getCategory());
		assertThat(getFromDB.getDiscount()).isEqualTo(promotion.getDiscount());
		promotionRepository.deleteById(promotion.getId());
	}

}
