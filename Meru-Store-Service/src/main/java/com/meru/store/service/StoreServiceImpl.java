package com.meru.store.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meru.store.exception.ResourceNotFoundException;
import com.meru.store.model.Store;
import com.meru.store.repository.StoreRepository;

@Service
public class StoreServiceImpl implements StoreService{

	@Autowired
	StoreRepository storeRepo;
	
	@Override
	public Store findStoreById(Long id) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		Store store = storeRepo.findById(id).get();
		
		if(store == null)
			throw new ResourceNotFoundException("Store not found");
		
		return store;
	}

	@Override
	public Store findStoreByName(String storeName) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		Store store = storeRepo.findByStoreName(storeName);
		
		if(store == null)
			throw new ResourceNotFoundException("Store not found");
		
		return store;
	}

	@Override
	public List<Store> findAllStore() throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		List<Store> allStores = storeRepo.findAll();
		
		if(allStores.size()==0)
			throw new ResourceNotFoundException("Store table is empty");
		
		return allStores;
	}

	@Override
	public Store saveStore(Store store) {
		// TODO Auto-generated method stub
		return storeRepo.save(store);
	}

	@Override
	public Store updateStore(Long storeId, Store store) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		Store updatedStore = storeRepo.findById(storeId).get();
		
		if(updatedStore == null)
			throw new ResourceNotFoundException("Store not found");
		else
			return storeRepo.save(store);
		
	}

	@Override
	public String deleteStoreById(Long id) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		try {
			storeRepo.deleteById(id);
			return "Store with id: "+id+" deleted";
		}
		catch(Exception e) {
			return "No store with id: "+id+" found!";
		}
	}

	@Override
	public String deleteAllStore() {
		// TODO Auto-generated method stub
		try{
			storeRepo.deleteAll();
			return "All store deleted";
		}
		catch(Exception e) {
			return "No stores found to delete";
		}
	}

	@Override
	public List<Store> findByProduct(Long productId) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		List<Store> stores = new ArrayList<>();
		stores = findAllStore();
		List<Store> storesWithProductAvailable = new ArrayList<>();
		stores.stream().forEach(i-> {
			if((i.getProducts()).contains(productId))
				storesWithProductAvailable.add(i);
		});
		
		return storesWithProductAvailable;
	}

	public Store addProductToStore(Long storeId, Long productId) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		Store store = storeRepo.findById(storeId).get();
		
		if(store == null)
			throw new ResourceNotFoundException("Store not found");
		
		else {
			List<Long> products = store.getProducts();
			products.add(productId);
			store.setProducts(products);
			return storeRepo.save(store);
		}
	}

}
