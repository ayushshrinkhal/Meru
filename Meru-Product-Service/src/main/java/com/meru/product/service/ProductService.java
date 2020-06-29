package com.meru.product.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meru.product.exception.ResourceNotFoundException;
import com.meru.product.model.Product;
import com.meru.product.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public Product createProduct(Product product) {
		Product pro = productRepository.save(product);
		return pro;
	}

	public List<Product> getAllProduct() throws ResourceNotFoundException {
		List<Product> product = new ArrayList<Product>();
		productRepository.findAll().forEach(product :: add);  
	    
	    if(product.size() == 0)
	        throw  new ResourceNotFoundException("No Product Found");
	    
		return product;
	}

	public Product findProductById(Long id) throws ResourceNotFoundException {
		Product product =  productRepository.findById(id).get();  

		if (product == null)
			throw new ResourceNotFoundException("Product Not Found");

		return product;
	}

	public Product findProductByName(String name) throws ResourceNotFoundException {
		Product product = null; 
		List<Product> allProduct = new ArrayList<Product>();
		
		try {
			allProduct.addAll(productRepository.findAll());  

			if(allProduct.size() == 0)
				throw  new ResourceNotFoundException("No Product Found");
		}
		catch(Exception e) {
			throw  new ResourceNotFoundException("Exception occur in product Service");
		}
		 
		for(Product pro : allProduct) {
			if(pro.getName().equalsIgnoreCase(name))
				product = pro;
		}

		if (product == null)
			throw new ResourceNotFoundException("Product Not Found");

		return product;
	}

	public List<Product> findProductByCategory(String category) throws ResourceNotFoundException {
		List<Product> product = new ArrayList<Product>();
		List<Product> allProduct = new ArrayList<Product>();
		
		try {
			allProduct.addAll(productRepository.findAll());  

			if(allProduct.size() == 0)
				throw  new ResourceNotFoundException("No Product Found");
		}
		catch(Exception e) {
			throw  new ResourceNotFoundException("Exception occur in product Service");
		}

		for(Product pro : allProduct) {
			if(pro.getCategory().equalsIgnoreCase(category))
				product.add(pro);
		}

		return product;
	}

	public Product updateProduct(Long id, Product product) throws ResourceNotFoundException {

		Product pro = findProductById(id);

		if(pro == null)
			throw new ResourceNotFoundException("Product Not Found");
		else { 
			pro.setId(id);
			pro.setCategory(product.getCategory());
			pro.setDescription(product.getDescription());
			pro.setName(product.getName());
			pro.setPrice(product.getPrice());
			pro = productRepository.save(pro);
		}

		return pro;
	}

	public String deleteProduct(Long id) throws ResourceNotFoundException {
		Product product = findProductById(id);

		if(product == null)
			throw new ResourceNotFoundException("Product Not Found");
		else
			productRepository.deleteById(id);

		return "Success";
	}

}