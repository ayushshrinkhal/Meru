package com.meru.promotion.controller;

import com.meru.promotion.exception.ResourceNotFoundException;
import com.meru.promotion.model.Promotion;
import com.meru.promotion.service.PromotionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RefreshScope
@CrossOrigin()
@RequestMapping("/promotion")
public class PromotionController {
    
	@Autowired
    private PromotionService promService;
    
    @GetMapping("/")
    public ResponseEntity<List<Promotion>> getPromotions() throws ResourceNotFoundException{
        return new ResponseEntity<List<Promotion>>(promService.getAllPromotions(), HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Promotion> findPromotion(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return new ResponseEntity<Promotion>(promService.getPromotionById(id), HttpStatus.OK);
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Promotion>> findPromotion(@PathVariable("category") String category) throws ResourceNotFoundException {
        return new ResponseEntity<List<Promotion>>(promService.getPromotionByCategory(category), HttpStatus.OK);
    }
    
    @PostMapping("/create")
    public ResponseEntity<Promotion> createEmployee(@RequestBody Promotion promModel){
        return new ResponseEntity<Promotion>(promService.addPromotion(promModel), HttpStatus.CREATED); 
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Promotion> updatePromotion(@PathVariable("id") Long id, @RequestBody Promotion promModel) throws ResourceNotFoundException{
        return new ResponseEntity<Promotion>(promService.updatePromotion(id, promModel), HttpStatus.CREATED);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) throws Exception {
    	return new ResponseEntity<String>(promService.deletePromotion(id), HttpStatus.NO_CONTENT);
    }
}
