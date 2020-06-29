package com.meru.product.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.meru.product.exception.ResourceNotFoundException;
import com.meru.product.model.Inventory;
import com.meru.product.model.Product;
import com.meru.product.service.ProductService;
import com.meru.product.model.ProductInventory;
import com.meru.product.model.ProductPromotionStore;

@RestController
@CrossOrigin()
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private RestTemplate restTemplate;

	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@GetMapping("/")
	public ResponseEntity<List<ProductPromotionStore>> getAllProducts() throws ResourceNotFoundException, ParseException {
		List<ProductPromotionStore> list = new ArrayList<>();
		List<Product> products = productService.getAllProduct();
		
		for(Product product : products) {
			ProductPromotionStore pps = new ProductPromotionStore();
			JSONParser jsonParser=new JSONParser();
			pps.setId(product.getId());
			pps.setName(product.getName());
			pps.setCategory(product.getCategory());
			pps.setDescription(product.getDescription());
			pps.setPrice(product.getPrice());
			
			String offers = "";
			try {
				HttpHeaders headers = new HttpHeaders();
				headers.set("Accept", "application/json");
				HttpEntity<Object> entity = new HttpEntity<Object>(headers);
				ResponseEntity<String> response = this.restTemplate.exchange("http://meru-zuul-gateway/api/meru-promotion-service/promotion/category/"+product.getCategory(), HttpMethod.GET, entity, String.class);
				offers = response.getBody();
			}
			catch(Exception ex) {
				offers = "";
			}
			if(offers.length() > 0) {
				JSONArray jsonOffers = (JSONArray) jsonParser.parse(offers);
				List<String> offer = new ArrayList<>();
				for(int i = 0; i < jsonOffers.size(); i++) {
					JSONObject obj = (JSONObject) jsonOffers.get(i);
					offer.add(String.valueOf(obj.get("discount")));
				}
				pps.setOffers(offer);
			}
			else
				pps.setOffers(null);
			
			String stores = "";
			try {
				HttpHeaders headers = new HttpHeaders();
				headers.set("Accept", "application/json");
				HttpEntity<Object> entity = new HttpEntity<Object>(headers);
				ResponseEntity<String> response = this.restTemplate.exchange("http://meru-zuul-gateway/api/meru-store-service/store/product/"+product.getId(), HttpMethod.GET, entity, String.class);
				stores = response.getBody();
				System.out.println(stores);
			}
			catch(Exception ex) {
				stores = "";
			}
			if(stores.length() > 0) {
				JSONArray jsonStores = (JSONArray) jsonParser.parse(stores);
				List<String> store = new ArrayList<>();
				for(int i = 0; i < jsonStores.size(); i++) {
					JSONObject obj = (JSONObject) jsonStores.get(i);
					store.add(String.valueOf(obj.get("storeName")));
				}
				pps.setStores(store);
			}
			else
				pps.setStores(null);
			list.add(pps);
		}
		
		return new ResponseEntity<List<ProductPromotionStore>>(list, HttpStatus.OK);
	}

	@GetMapping("/id/{productId}")
	public ResponseEntity<ProductPromotionStore> getProductById(@PathVariable("productId") Long id) throws ResourceNotFoundException, ParseException {
		ProductPromotionStore pps = new ProductPromotionStore();
		JSONParser jsonParser=new JSONParser();
		Product product = productService.findProductById(id);
		
		pps.setId(product.getId());
		pps.setName(product.getName());
		pps.setCategory(product.getCategory());
		pps.setDescription(product.getDescription());
		pps.setPrice(product.getPrice());
		
		String offers = "";
		try {
			offers = this.restTemplate.getForObject("http://meru-zuul-gateway/api/meru-promotion-service/promotion/category/"+product.getCategory(), String.class);
		}
		catch(Exception ex) {
			offers = "";
		}
		if(offers.length() > 0) {
			JSONArray jsonOffers = (JSONArray) jsonParser.parse(offers);
			List<String> offer = new ArrayList<>();
			for(int i = 0; i < jsonOffers.size(); i++) {
				JSONObject obj = (JSONObject) jsonOffers.get(i);
				offer.add(String.valueOf(obj.get("discount")));
			}
			pps.setOffers(offer);
		}
		else
			pps.setOffers(null);
		
		String stores = "";
		try {
			stores = this.restTemplate.getForObject("http://meru-zuul-gateway/api/meru-store-service/store/product/"+product.getId(), String.class);
			System.out.println(stores);
		}
		catch(Exception ex) {
			stores = "";
		}
		if(stores.length() > 0) {
			JSONArray jsonStores = (JSONArray) jsonParser.parse(stores);
			List<String> store = new ArrayList<>();
			for(int i = 0; i < jsonStores.size(); i++) {
				JSONObject obj = (JSONObject) jsonStores.get(i);
				store.add(String.valueOf(obj.get("storeName")));
			}
			pps.setStores(store);
		}
		else
			pps.setStores(null);
		
		return new ResponseEntity<ProductPromotionStore>(pps, HttpStatus.OK);
	}

	@GetMapping("/name/{productName}")
	public ResponseEntity<ProductPromotionStore> getProductByName(@PathVariable("productName") String name) throws ResourceNotFoundException, ParseException {
		ProductPromotionStore pps = new ProductPromotionStore();
		JSONParser jsonParser=new JSONParser();
		Product product = productService.findProductByName(name);
		
		pps.setId(product.getId());
		pps.setName(product.getName());
		pps.setCategory(product.getCategory());
		pps.setDescription(product.getDescription());
		pps.setPrice(product.getPrice());
		
		String offers = "";
		try {
			offers = this.restTemplate.getForObject("http://meru-zuul-gateway/api/meru-promotion-service/promotion/category/"+product.getCategory(), String.class);
		}
		catch(Exception ex) {
			offers = "";
		}
		if(offers.length() > 0) {
			JSONArray jsonOffers = (JSONArray) jsonParser.parse(offers);
			List<String> offer = new ArrayList<>();
			for(int i = 0; i < jsonOffers.size(); i++) {
				JSONObject obj = (JSONObject) jsonOffers.get(i);
				offer.add(String.valueOf(obj.get("discount")));
			}
			pps.setOffers(offer);
		}
		else
			pps.setOffers(null);
		
		String stores = "";
		try {
			stores = this.restTemplate.getForObject("http://meru-zuul-gateway/api/meru-store-service/store/product/"+product.getId(), String.class);
			System.out.println(stores);
		}
		catch(Exception ex) {
			stores = "";
		}
		if(stores.length() > 0) {
			JSONArray jsonStores = (JSONArray) jsonParser.parse(stores);
			List<String> store = new ArrayList<>();
			for(int i = 0; i < jsonStores.size(); i++) {
				JSONObject obj = (JSONObject) jsonStores.get(i);
				store.add(String.valueOf(obj.get("storeName")));
			}
			pps.setStores(store);
		}
		else
			pps.setStores(null);
		
		return new ResponseEntity<ProductPromotionStore>(pps, HttpStatus.OK);
	}

	@GetMapping("/category/{byCategory}")
	public ResponseEntity<List<ProductPromotionStore>> getProductByCategory(@PathVariable("byCategory") String category) throws ResourceNotFoundException, ParseException {
		List<ProductPromotionStore> list = new ArrayList<>();
		List<Product> products = productService.findProductByCategory(category);
		
		for(Product product : products) {
			ProductPromotionStore pps = new ProductPromotionStore();
			JSONParser jsonParser=new JSONParser();
			pps.setId(product.getId());
			pps.setName(product.getName());
			pps.setCategory(product.getCategory());
			pps.setDescription(product.getDescription());
			pps.setPrice(product.getPrice());
			
			String offers = "";
			try {
				offers = this.restTemplate.getForObject("http://meru-zuul-gateway/api/meru-promotion-service/promotion/category/"+product.getCategory(), String.class);
			}
			catch(Exception ex) {
				offers = "";
			}
			if(offers.length() > 0) {
				JSONArray jsonOffers = (JSONArray) jsonParser.parse(offers);
				List<String> offer = new ArrayList<>();
				for(int i = 0; i < jsonOffers.size(); i++) {
					JSONObject obj = (JSONObject) jsonOffers.get(i);
					offer.add(String.valueOf(obj.get("discount")));
				}
				pps.setOffers(offer);
			}
			else
				pps.setOffers(null);
			
			String stores = "";
			try {
				stores = this.restTemplate.getForObject("http://meru-zuul-gateway/api/meru-store-service/store/product/"+product.getId(), String.class);
				System.out.println(stores);
			}
			catch(Exception ex) {
				stores = "";
			}
			if(stores.length() > 0) {
				JSONArray jsonStores = (JSONArray) jsonParser.parse(stores);
				List<String> store = new ArrayList<>();
				for(int i = 0; i < jsonStores.size(); i++) {
					JSONObject obj = (JSONObject) jsonStores.get(i);
					store.add(String.valueOf(obj.get("storeName")));
				}
				pps.setStores(store);
			}
			else
				pps.setStores(null);
			list.add(pps);
		}
		
		return new ResponseEntity<List<ProductPromotionStore>>(list, HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<Product> addProduct(@RequestBody ProductInventory product_inventory) throws ResourceNotFoundException {
		Product pro = productService.createProduct(product_inventory.getProduct());
		Inventory inv = product_inventory.getInventory();
		inv.setProductId(pro.getId());
		boolean flag = false;
		try {
			this.restTemplate.postForObject("http://meru-zuul-gateway/api/meru-inventory-service/inventory/create", inv, String.class);
			flag = true;
		}
		catch(Exception e) {
			System.out.println(e.toString());
		}
		if(flag)
			return new ResponseEntity<Product>(pro, HttpStatus.CREATED); 
		else {
			pro = null;
			return new ResponseEntity<Product>(pro, HttpStatus.FORBIDDEN);
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product) throws ResourceNotFoundException {
		product.setId(id);
		return new ResponseEntity<Product>(productService.updateProduct(id, product), HttpStatus.CREATED);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id) throws ResourceNotFoundException {
		boolean flag = false;
		String exception_in_inventory = "";
		try {
			this.restTemplate.delete("http://meru-zuul-gateway/api/meru-inventory-service/inventory/delete/product/"+id, String.class);
			flag = true;
		}
		catch(Exception e) {
			exception_in_inventory = e.toString();
		}
		if(flag)
			return new ResponseEntity<String>(productService.deleteProduct(id), HttpStatus.NO_CONTENT);
		else {
			if(exception_in_inventory.length() > 0) 
				return new ResponseEntity<String>("Failure : Not able to delete the product. Exception in calling Inventory Service : "+exception_in_inventory, HttpStatus.FORBIDDEN);
			else
				return new ResponseEntity<String>("Failure : Not able to delete the product", HttpStatus.FORBIDDEN);
		}
	}
}