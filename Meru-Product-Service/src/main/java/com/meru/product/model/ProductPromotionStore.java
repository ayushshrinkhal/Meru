package com.meru.product.model;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ProductPromotionStore {
	
	private Long id;	
	private String name;
	private String category;
	private String description;
	private Long price;
	private List<String> offers;
	private List<String> stores;
	
	public ProductPromotionStore() {
	}

	public ProductPromotionStore(Long id, String name, String category, String description, Long price, List<String> offers, List<String> stores) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
		this.description = description;
		this.price = price;
		this.offers = offers;
		this.stores = stores;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public List<String> getOffers() {
		return offers;
	}

	public void setOffers(List<String> offers) {
		this.offers = offers;
	}

	public List<String> getStores() {
		return stores;
	}

	public void setStores(List<String> stores) {
		this.stores = stores;
	}

}
