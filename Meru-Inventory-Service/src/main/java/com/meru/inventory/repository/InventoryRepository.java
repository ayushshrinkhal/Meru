package com.meru.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meru.inventory.model.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long>{
    
	Inventory findByProductId(Long productId);
	
}
