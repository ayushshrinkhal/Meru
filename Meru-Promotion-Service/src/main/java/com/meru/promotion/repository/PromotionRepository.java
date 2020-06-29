package com.meru.promotion.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.meru.promotion.model.Promotion;

@Repository
public interface PromotionRepository extends CrudRepository<Promotion, Long>{

}




