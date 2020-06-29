package com.meru.product.model;

import org.springframework.stereotype.Component;

@Component
public class ProductInventory {

	private Product product;
	
	private Inventory inventory;

	public ProductInventory() {
	}

	public ProductInventory(Product product, Inventory inventory) {
		super();
		this.product = product;
		this.inventory = inventory;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	
}
