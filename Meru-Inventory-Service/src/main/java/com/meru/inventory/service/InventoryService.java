package com.meru.inventory.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meru.inventory.exception.ResourceNotFoundException;
import com.meru.inventory.model.Inventory;
import com.meru.inventory.repository.InventoryRepository;

@Service
public class InventoryService {
	
	@Autowired
	InventoryRepository inventoryRepository;
	
	public Inventory createInventory(Inventory inventory) {
		Inventory inv = inventoryRepository.save(inventory);
		return inv;
	}
	
	public List<Inventory> getAllInventory() throws ResourceNotFoundException {
		List<Inventory> inventory = new ArrayList<Inventory>(); 
	    inventoryRepository.findAll().forEach(inventory :: add);  
	    
	    if(inventory.size() == 0)
	        throw  new ResourceNotFoundException("Inventory Table is Empty");
	    
	    return inventory;
	}
	
	public Inventory findInventoryById(Long id) throws ResourceNotFoundException {
		Inventory inventory = inventoryRepository.findById(id).get(); 
	    
		if (inventory == null)
	        throw new ResourceNotFoundException("Inventory Not Found");
	    
	    return inventory;
	}
	
	public Inventory findInventoryByProductId(Long productId) throws ResourceNotFoundException {
		Inventory inventory = inventoryRepository.findByProductId(productId);  
	    
		if (inventory == null)
	        throw new ResourceNotFoundException("Product Not Found in Inventory");
	    
	    return inventory;
	}
	
	public Inventory updateInventory(Long id, Inventory Inventory) throws ResourceNotFoundException {
		Inventory inventory = findInventoryById(id);
		
		if(inventory == null)
			throw new ResourceNotFoundException("Inventory Not Found");
		else
			inventory = inventoryRepository.save(Inventory);
		
		return inventory;
	}
	
	public String deleteInventoryById(Long id) throws ResourceNotFoundException {
		Inventory inventory = findInventoryById(id);
		
		if(inventory == null)
			throw new ResourceNotFoundException("Inventory Not Found");
		else
			inventoryRepository.deleteById(id);
		
		return "Success";
	}
	
	public String deleteInventoryByProductId(Long id) throws ResourceNotFoundException {
		Inventory inventory = findInventoryByProductId(id);
		
		if(inventory != null)
			inventoryRepository.delete(inventory);
		
		return "Success";
	}
}
