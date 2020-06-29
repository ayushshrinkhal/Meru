package com.meru.promotion.model;

import java.io.Serializable;

import javax.persistence.*;

import org.springframework.stereotype.Component;

@Entity
@Component
public class Promotion implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name="category")
    private String category;
    
    @Column(name="discount")
    private String discount;
   
    public Promotion() {
    }

    public Promotion(Long id,String discount,String category) {
    	super();
    	this.id = id;
        this.discount = discount;
        this.category=category;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
    
}
