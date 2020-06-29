package com.meru.store.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.meru.store.exception.ResourceNotFoundException;
import com.meru.store.model.Inventory;
import com.meru.store.model.Store;
import com.meru.store.service.StoreServiceImpl;

@RestController
@CrossOrigin()
public class StoreController {

	@Autowired
	private StoreServiceImpl storeService;

	@Autowired
	private RestTemplate restTemplate;

	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@GetMapping("/")
	public ResponseEntity<String> getMessage(){
		return ResponseEntity.ok("This is Store microservice");
	}

	@GetMapping("/store")
	public ResponseEntity<List<Store>> getAllStores() throws ResourceNotFoundException{
		return ResponseEntity.ok(storeService.findAllStore());
	}

	@GetMapping("/store/{storeId}")
	public ResponseEntity<Store> getStoreById(@PathVariable("storeId") Long storeId) throws ResourceNotFoundException{
		return ResponseEntity.ok(storeService.findStoreById(storeId));
	}

	@GetMapping("/store/product/{productId}")
//	@HystrixCommand(fallbackMethod = "getFallbackStoreByProduct", commandProperties = {
//			@HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "1000"), })
	public ResponseEntity<List<Store>> getStoreByProduct(@PathVariable("productId") Long productId) throws ResourceNotFoundException, JsonProcessingException{
		Inventory subject;
		String quantity = "";
		try {
			subject = this.restTemplate.getForObject("http://meru-zuul-gateway/api/meru-inventory-service/inventory/store/"+productId, Inventory.class);
			quantity = subject.getOfflineProductQuantity();
		}
		catch(Exception e) {
			throw new ResourceNotFoundException("Product with ProductId: "+productId+" is not available in offline stores");	
		}
		
		if(!quantity.equalsIgnoreCase("0")) {
			try {
				return ResponseEntity.ok(storeService.findByProduct(productId));
			}
			catch(Exception e) {
				throw new ResourceNotFoundException("Exception caused in Store Service");
			}
		}
		else
			throw new ResourceNotFoundException("Exception caused in Store Service");	
	}

	@PostMapping("/store/create")
	public ResponseEntity<Store> saveStore(@RequestBody Store store){
		return new ResponseEntity<Store>(storeService.saveStore(store), HttpStatus.CREATED);
	}
	
	@PutMapping("/store/addProduct/{storeId}")
	public ResponseEntity<Store> addProduct(@PathVariable("storeId") Long storeId, @RequestBody Long productId) throws ResourceNotFoundException{
		return new ResponseEntity<Store>(storeService.addProductToStore(storeId, productId), HttpStatus.CREATED);
	}

	@PutMapping("/store/update/{storeId}")
	public ResponseEntity<Store> updateStore(@PathVariable("storeId") Long storeId, @RequestBody Store store) throws ResourceNotFoundException{
		return new ResponseEntity<Store>(storeService.updateStore(storeId, store), HttpStatus.CREATED);
	}

	@DeleteMapping("/store/delete/{storeId}")
	public ResponseEntity<String> deleteStoreById(@PathVariable("storeId") Long storeId) throws ResourceNotFoundException {
		return new ResponseEntity<String>(storeService.deleteStoreById(storeId), HttpStatus.ACCEPTED); 
	}

	@DeleteMapping("/store/deleteAll")
	public ResponseEntity<String> deleteAllStores() throws ResourceNotFoundException {
		return new ResponseEntity<String>(storeService.deleteAllStore(), HttpStatus.ACCEPTED); 
	}
	
	public ResponseEntity<List<Store>> getFallbackStoreByProduct(@PathVariable("productId") Long productId) throws ResourceNotFoundException, JsonProcessingException{
		return new ResponseEntity<List<Store>>(Arrays.asList(new Store(productId,"Product is not available in offline stores. Sorry for inconvenience!","","",null)), HttpStatus.OK);
	}
}
