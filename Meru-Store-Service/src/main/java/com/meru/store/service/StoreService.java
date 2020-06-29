package com.meru.store.service;

import java.util.List;

import com.meru.store.exception.ResourceNotFoundException;
import com.meru.store.model.Store;

public interface StoreService {
	
	Store findStoreById(Long id) throws ResourceNotFoundException;
	Store findStoreByName(String storeName) throws ResourceNotFoundException;
	List<Store> findAllStore() throws ResourceNotFoundException;
	Store saveStore(Store store);
	Store updateStore(Long storeId, Store store) throws ResourceNotFoundException;
	String deleteStoreById(Long id) throws ResourceNotFoundException;
	String deleteAllStore();
	List<Store> findByProduct(Long productId) throws ResourceNotFoundException;
	public Store addProductToStore(Long storeId, Long productId) throws ResourceNotFoundException;
}
