package com.meru.inventory.service;

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

import com.meru.inventory.exception.ResourceNotFoundException;
import com.meru.inventory.model.Inventory;
import com.meru.inventory.repository.InventoryRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
class InventoryServiceTest {

	@Autowired
	private InventoryService inventoryService;
	
	@MockBean
	private InventoryRepository inventoryRepository;
	
	@Test
	void testCreateInventory() throws ResourceNotFoundException {
		Inventory inventory = new Inventory();
		inventory.setId(Long.valueOf(1));
		inventory.setOfflineProductQuantity("10");
		inventory.setOnlineProductQuantity("50");
		inventory.setProductId(Long.valueOf(1));
		
		Mockito.when(inventoryRepository.save(inventory)).thenReturn(inventory);
		assertThat(inventoryService.createInventory(inventory)).isEqualTo(inventory);
	}
	
	@Test
	void testGetAllInventory() throws ResourceNotFoundException {
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
		
		Mockito.when(inventoryRepository.findAll()).thenReturn(list_of_Inventory);
		assertThat(inventoryService.getAllInventory()).isEqualTo(list_of_Inventory);
	}
	
	@Test
	void testFindInventoryById() throws ResourceNotFoundException {
		Inventory inventory = new Inventory();
		inventory.setId(Long.valueOf(1));
		inventory.setOfflineProductQuantity("10");
		inventory.setOnlineProductQuantity("50");
		inventory.setProductId(Long.valueOf(1));
		
		Mockito.when(inventoryRepository.findById(inventory.getId())).thenReturn(Optional.of(inventory));
		assertThat(inventoryService.findInventoryById(inventory.getId())).isEqualTo(inventory);
	}
	
	@Test
	void testfindInventoryByProductId() throws ResourceNotFoundException {
		Inventory inventory = new Inventory();
		inventory.setId(Long.valueOf(1));
		inventory.setOfflineProductQuantity("10");
		inventory.setOnlineProductQuantity("50");
		inventory.setProductId(Long.valueOf(1));
		
		Mockito.when(inventoryRepository.findByProductId(inventory.getProductId())).thenReturn(inventory);
		assertThat(inventoryService.findInventoryByProductId(inventory.getProductId())).isEqualTo(inventory);
	}
	
	@Test
	void testUpdateInventory() throws ResourceNotFoundException {
		Inventory inventory1 = new Inventory();
		inventory1.setId(Long.valueOf(1));
		inventory1.setOfflineProductQuantity("10");
		inventory1.setOnlineProductQuantity("50");
		inventory1.setProductId(Long.valueOf(1));
		
		Inventory inventory2 = new Inventory();
		inventory2.setId(Long.valueOf(1));
		inventory2.setOfflineProductQuantity("15");
		inventory2.setOnlineProductQuantity("50");
		inventory2.setProductId(Long.valueOf(1));
		
		Mockito.when(inventoryRepository.findById(inventory1.getId())).thenReturn(Optional.of(inventory1));
		Mockito.when(inventoryRepository.save(inventory1)).thenReturn(inventory2);
		assertThat(inventoryService.updateInventory(inventory1.getProductId(), inventory1).getOfflineProductQuantity()).isEqualTo(inventory2.getOfflineProductQuantity());
	}
	
	@Test
	void testDeleteInventoryById() throws ResourceNotFoundException {
		Inventory inventory = new Inventory();
		inventory.setId(Long.valueOf(1));
		inventory.setOfflineProductQuantity("10");
		inventory.setOnlineProductQuantity("50");
		inventory.setProductId(Long.valueOf(1));
		
		Mockito.when(inventoryRepository.findById(inventory.getId())).thenReturn(Optional.of(inventory));
		inventoryService.deleteInventoryById(inventory.getId());
		verify(inventoryRepository, times(1)).deleteById(inventory.getId());	
	}
	
	@Test
	void testDeleteInventoryByProductId() throws ResourceNotFoundException {
		Inventory inventory = new Inventory();
		inventory.setId(Long.valueOf(1));
		inventory.setOfflineProductQuantity("10");
		inventory.setOnlineProductQuantity("50");
		inventory.setProductId(Long.valueOf(1));
		
		Mockito.when(inventoryRepository.findByProductId(inventory.getProductId())).thenReturn(inventory);
		inventoryService.deleteInventoryByProductId(inventory.getProductId());
		verify(inventoryRepository, times(1)).delete(inventory);	
	}
	
}
