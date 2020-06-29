package com.meru.inventory.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.meru.inventory.model.Inventory;

@RunWith(SpringRunner.class)
@DataJpaTest
class InventoryRepositoryTest {

	@Autowired
	private InventoryRepository inventoryRepository;
	
	@Test
	void testSaveInventory() {
		Inventory inventory = new Inventory();
		inventory.setId(Long.valueOf(1));
		inventory.setOfflineProductQuantity("10");
		inventory.setOnlineProductQuantity("50");
		inventory.setProductId(Long.valueOf(1));
		
		Inventory savedInDB = inventoryRepository.save(inventory);
		
		Inventory getFromDB = inventoryRepository.findById(savedInDB.getId()).get(); 
		
		assertThat(getFromDB.getId()).isEqualTo(savedInDB.getId());
		assertThat(getFromDB.getOfflineProductQuantity()).isEqualTo(savedInDB.getOfflineProductQuantity());
		assertThat(getFromDB.getOnlineProductQuantity()).isEqualTo(savedInDB.getOnlineProductQuantity());
		assertThat(getFromDB.getProductId()).isEqualTo(savedInDB.getProductId());
		inventoryRepository.deleteById(inventory.getId());
	}

	@Test
	void testFindAll() {
		Inventory inventory = new Inventory();
		inventory.setId(Long.valueOf(2));
		inventory.setOfflineProductQuantity("10");
		inventory.setOnlineProductQuantity("50");
		inventory.setProductId(Long.valueOf(2));
		
		inventoryRepository.save(inventory);
		
		List<Inventory> inventoryList = new ArrayList<Inventory>(); 
		inventoryRepository.findAll().forEach(inventoryList :: add);
		
		assertThat(inventoryList.size()).isEqualTo(1);
		
		inventoryRepository.deleteById(inventory.getId());
	}
	
	@Test
	void testFindById() {
		Inventory inventory = new Inventory();
		inventory.setId(Long.valueOf(4));
		inventory.setOfflineProductQuantity("10");
		inventory.setOnlineProductQuantity("50");
		inventory.setProductId(Long.valueOf(4));
		
		inventoryRepository.save(inventory);
		
		Inventory getFromDB = inventoryRepository.findById(inventory.getId()).get(); 
		
		assertThat(getFromDB.getId()).isEqualTo(inventory.getId());
		assertThat(getFromDB.getOfflineProductQuantity()).isEqualTo(inventory.getOfflineProductQuantity());
		assertThat(getFromDB.getOnlineProductQuantity()).isEqualTo(inventory.getOnlineProductQuantity());
		assertThat(getFromDB.getProductId()).isEqualTo(inventory.getProductId());
		inventoryRepository.deleteById(inventory.getId());
	}
	
	@Test
	void testFindByProductId() {
		Inventory inventory = new Inventory();
		inventory.setId(Long.valueOf(3));
		inventory.setOfflineProductQuantity("10");
		inventory.setOnlineProductQuantity("50");
		inventory.setProductId(Long.valueOf(3));
		
		inventoryRepository.save(inventory);
		
		Inventory getFromDB = inventoryRepository.findByProductId(inventory.getProductId()); 
		
		assertThat(getFromDB.getId()).isEqualTo(inventory.getId());
		assertThat(getFromDB.getOfflineProductQuantity()).isEqualTo(inventory.getOfflineProductQuantity());
		assertThat(getFromDB.getOnlineProductQuantity()).isEqualTo(inventory.getOnlineProductQuantity());
		assertThat(getFromDB.getProductId()).isEqualTo(inventory.getProductId());
		inventoryRepository.deleteById(inventory.getId());
	}
	
}
