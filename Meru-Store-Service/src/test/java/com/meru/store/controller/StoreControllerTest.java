package com.meru.store.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meru.store.model.Store;
import com.meru.store.service.StoreServiceImpl;

@SpringBootTest
@Transactional
@TestInstance(Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
class StoreControllerTest {

	@Autowired
	private MockMvc mvc;
	
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
	void testGetMessage() throws Exception {
		mvc.perform(MockMvcRequestBuilders
				.get("/")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string("This is Store microservice"));
	}

	@Test
	void testGetAllStores() throws Exception {
		storeService.saveStore(store);
		
		mvc.perform(MockMvcRequestBuilders
				.get("/store")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].storeName",is("Meru")));
	}

	@Test
	void testGetStoreById() throws Exception {
		storeService.saveStore(store);
		Long id = store.getId();
		
		mvc.perform(MockMvcRequestBuilders
				.get("/store/{storeId}",id)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.storeName").value("Meru"));
	}


	@Test
	void testSaveStore() throws Exception {
		mvc.perform(MockMvcRequestBuilders
				.post("/store/create")
				.content(asJsonString(store))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.storeName").value("Meru"));
	}

	@Test
	void testUpdateStore() throws Exception {
		storeService.saveStore(store);
		Long id = store.getId();
		
		store = new Store();
		store.setId(id);
		store.setStoreName("Meru.com");
		store.setCity("Chennai");
		store.setLocation("Sholinganallur");
		
		mvc.perform(MockMvcRequestBuilders
				.put("/store/update/{storeId}",id)
				.content(asJsonString(store))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.storeName").value("Meru.com"));
	}

//	@Test
//	void testGetStoreByProduct() throws Exception{
//		store.setProducts(Arrays.asList(100L,200L,150L));
//		storeService.saveStore(store);
//		
//		mvc.perform(MockMvcRequestBuilders
//				.get("/store/product/{productId}",100L)
//				.accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("$.storeName").value("Meru"));
//				//.andExpect(content().string("Wait for some time, another service is down. Sorry for inconvenience!"));
//	}
	
	@Test
	void testDeleteStoreById() throws Exception {
		storeService.saveStore(store);
		Long id = store.getId();
		
		mvc.perform(MockMvcRequestBuilders
				.delete("/store/delete/{storeId}",id))
				.andExpect(status().isAccepted());
	}

	@Test
	void testDeleteAllStores() throws Exception {
		storeService.saveStore(store);
		
		mvc.perform(MockMvcRequestBuilders
				.delete("/store/deleteAll"))
				.andExpect(status().isAccepted());
	}
	
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

}
