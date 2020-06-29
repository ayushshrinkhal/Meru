package com.meru.store.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name="StoreDetails")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Store implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	private String storeName;
	private String city;
	private String location;

	//@JsonIgnore
	@ElementCollection
	private List<Long> products;

	//	@JsonIgnore
	//	public List<Long> getProducts(){
	//		return products;
	//	}

	//	//@JsonProperty
	public void setProducts(List<Long> products) {
		this.products = products;
	}

}
