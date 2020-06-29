package com.meru.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meru.store.model.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long>{

	Store findByStoreName(String storeName);
    
}
