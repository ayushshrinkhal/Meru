package com.meru.store.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.meru.store.exception.ResourceNotFoundException;
import com.meru.store.model.Store;

@SpringBootTest
@Transactional
@TestInstance(Lifecycle.PER_CLASS)
public class StoreServiceImplTest {

	@Autowired
	private StoreServiceImpl storeService;
	
	private Store store;
	
	@BeforeEach
	public void storeInit() {
		store = new Store();
		
		store.setStoreName("Meru");
		store.setCity("Bengaluru");
		store.setLocation("Electronic city");
	}
	
	@Test
	public void testFindStoreById() throws ResourceNotFoundException {
		storeService.saveStore(store);
		long id = store.getId();
		assertThat(storeService.findStoreById(id)).hasFieldOrPropertyWithValue("storeName", "Meru");
	}

	@Test
	public void testFindStoreByName() throws ResourceNotFoundException {
		storeService.saveStore(store);
		
		assertThat(storeService.findStoreByName("Meru")).hasFieldOrPropertyWithValue("city", "Bengaluru");
	}

	@Test
	public void testFindAllStore() throws ResourceNotFoundException {
		storeService.saveStore(store);
		assertThat(storeService.findAllStore()).isNotEmpty();
	}

	@Test
	public void testSaveStore() {
		assertThat(storeService.saveStore(store)).hasFieldOrPropertyWithValue("storeName", "Meru");
	}

	@Test
	public void testUpdateStore() {
		assertThat(storeService.saveStore(store)).hasFieldOrPropertyWithValue("storeName", "Meru");
	}

	@Test
	public void testDeleteStoreById() throws ResourceNotFoundException {
		storeService.saveStore(store);
		long id = store.getId();
		assertThat(storeService.deleteStoreById(id)).isEqualTo("Store with id: "+id+" deleted");
	}

	@Test
	public void testDeleteAllStore() {
		storeService.saveStore(store);
		Store newStore = new Store();
		newStore.setCity("Chennai");
		newStore.setLocation("Sholinganallur");
		newStore.setStoreName("meru.com");
		storeService.saveStore(store);
		
		assertThat(storeService.deleteAllStore()).isEqualTo("All store deleted");
	}

	@Test
	public void testFindByProduct() throws ResourceNotFoundException {
		store.setProducts(new ArrayList<Long>(Arrays.asList(1L,2L)));
		storeService.saveStore(store);
		
		Store newStore = new Store();
		newStore.setCity("Chennai");
		newStore.setLocation("Sholinganallur");
		newStore.setStoreName("meru.com");
		newStore.setProducts(new ArrayList<Long>(Arrays.asList(3L,4L)));
		storeService.saveStore(newStore);
		assertThat(storeService.findByProduct(3L)).hasSize(1);
	}

}
