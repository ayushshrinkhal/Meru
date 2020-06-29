package com.meru.inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
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

import com.meru.inventory.exception.ResourceNotFoundException;
import com.meru.inventory.model.Inventory;
import com.meru.inventory.service.InventoryService;

@RestController
@RefreshScope
@CrossOrigin()
@RequestMapping("/inventory")
public class InventoryController {
	
	@Autowired
	InventoryService inventoryService;
	
	@GetMapping("/")
	public ResponseEntity<List<Inventory>> getAllInventory() throws ResourceNotFoundException {
	    return new ResponseEntity<List<Inventory>>(inventoryService.getAllInventory(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Inventory> getInventoryById(@PathVariable("id") Long id) throws ResourceNotFoundException {
		return new ResponseEntity<Inventory>(inventoryService.findInventoryById(id), HttpStatus.OK);
	}
	
	@GetMapping("/store/{productId}")
	public ResponseEntity<Inventory> getInventoryByProductId(@PathVariable("productId") Long productId) throws ResourceNotFoundException {
		return new ResponseEntity<Inventory>(inventoryService.findInventoryByProductId(productId), HttpStatus.OK);
	}
	
	@PostMapping("/create")
	public ResponseEntity<Inventory> addInventory(@RequestBody Inventory Inventory) {
		return new ResponseEntity<Inventory>(inventoryService.createInventory(Inventory), HttpStatus.CREATED); 
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<Inventory> updateInventory(@PathVariable("id") Long id, @RequestBody Inventory Inventory) throws ResourceNotFoundException {
		return new ResponseEntity<Inventory>(inventoryService.updateInventory(id, Inventory), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteInventoryById(@PathVariable("id") Long id) throws ResourceNotFoundException {
		return new ResponseEntity<String>(inventoryService.deleteInventoryById(id), HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/delete/product/{id}")
	public ResponseEntity<String> deleteInventoryByProductId(@PathVariable("id") Long id) throws ResourceNotFoundException {
		return new ResponseEntity<String>(inventoryService.deleteInventoryByProductId(id), HttpStatus.NO_CONTENT);
	}
	
}
