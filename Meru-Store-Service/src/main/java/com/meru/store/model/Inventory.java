package com.meru.store.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

@Entity
@Component
public class Inventory implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="onlineProductQuantity")
	private String onlineProductQuantity;
	
	@Column(name="offlineProductQuantity")
	private String offlineProductQuantity;
	
	@Column(name="productId")
	private Long productId;
	
	public Inventory() {	
	}
	
	public Inventory(Long id, String onlineProductQuantity, String offlineProductQuantity, Long productId) {
		super();
		this.id = id;
		this.onlineProductQuantity = onlineProductQuantity;
		this.offlineProductQuantity = offlineProductQuantity;
		this.productId = productId;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOnlineProductQuantity() {
		return onlineProductQuantity;
	}

	public void setOnlineProductQuantity(String onlineProductQuantity) {
		this.onlineProductQuantity = onlineProductQuantity;
	}

	public String getOfflineProductQuantity() {
		return offlineProductQuantity;
	}

	public void setOfflineProductQuantity(String offlineProductQuantity) {
		this.offlineProductQuantity = offlineProductQuantity;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
}
