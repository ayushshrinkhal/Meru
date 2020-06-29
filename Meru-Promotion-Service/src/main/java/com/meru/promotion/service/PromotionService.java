package com.meru.promotion.service;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.meru.promotion.exception.ResourceNotFoundException;
import com.meru.promotion.model.Promotion;
import com.meru.promotion.repository.PromotionRepository;

@Service
public class PromotionService  {

    @Autowired
    private PromotionRepository promRepository;

    public List<Promotion> getAllPromotions() throws ResourceNotFoundException{
    	List<Promotion> promotions = new ArrayList<Promotion>(); 
    	promRepository.findAll().forEach(promotions :: add);  
	    
	    if(promotions.size() == 0)
	        throw  new ResourceNotFoundException("Promotion Table is Empty");
	    
	    return promotions;
    }
    
    public Promotion getPromotionById(Long id) throws ResourceNotFoundException{
    	Promotion prom = promRepository.findById(id).get();  
	    
		if (prom == null)
	        throw new ResourceNotFoundException("Promotion Not Found");
	    
	    return prom;
    }
    
    public List<Promotion> getPromotionByCategory(String category) throws ResourceNotFoundException{
    	List<Promotion> prom = new ArrayList<>();
    	List<Promotion> promotions = getAllPromotions();  
	    
    	for(Promotion promotion : promotions) {
    		if(promotion.getCategory().equalsIgnoreCase(category))
    			prom.add(promotion);
    	}
    	
		if (prom.size() == 0)
	        throw new ResourceNotFoundException("No Promotion Found for Category : "+category);
	    
	    return prom;
    }
    
    public Promotion addPromotion(Promotion promModel){
    	Promotion prom = promRepository.save(promModel);
		return prom;
    }
    
    public Promotion updatePromotion(Long id, Promotion promModel) throws ResourceNotFoundException{
    	Promotion prom = getPromotionById(id);
		
		if(prom == null)
			throw new ResourceNotFoundException("Promotion Not Found");
		else
			prom = promRepository.save(promModel);
		
		return prom;
    }
    
    public String deletePromotion(Long id) throws ResourceNotFoundException{
    	Promotion inventory = getPromotionById(id);
		
		if(inventory == null)
			throw new ResourceNotFoundException("Promotion Not Found");
		else
			promRepository.deleteById(id);
		
		return "Success";
    }
}
