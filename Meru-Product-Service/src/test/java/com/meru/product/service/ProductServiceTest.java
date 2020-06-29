package com.meru.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.meru.product.exception.ResourceNotFoundException;
import com.meru.product.model.Product;
import com.meru.product.repository.ProductRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProductServiceTest {

	@Autowired
	private ProductService productService;
	
	@MockBean
	private ProductRepository productRepository;
	
	@Test
	void testCreateProduct() throws ResourceNotFoundException{
		Product product=new Product();
		product.setId(Long.valueOf(1));
		product.setName("Iphone 11");
		product.setCategory("Electronics");
		product.setDescription("8 gb Ram, 5000 mah Battery,64 mp camera");
		product.setPrice(Long.valueOf(65000));
		Mockito.when(productRepository.save(product)).thenReturn(product);
		assertThat(productService.createProduct(product)).isEqualTo(product);
	} 
	
	@Test
	void testGetAllProduct() throws ResourceNotFoundException{
		List<Product> list_of_Product = new ArrayList<>();
		Product product=new Product();
		product.setId(Long.valueOf(2));
		product.setName("Realme XT");
		product.setCategory("Electronics");
		product.setDescription("6 gb Ram, 4000 mah Battery,48 mp camera");
		product.setPrice(Long.valueOf(25000));
		list_of_Product.add(product);
		Product product1=new Product();
		product1.setId(Long.valueOf(3));
		product1.setName("Carol Engineered Wood King Bed");
		product1.setCategory("Furniture");
		product1.setDescription("Depth 205cm, Width 190cm, Height 80cm");
		product1.setPrice(Long.valueOf(10990));
		list_of_Product.add(product1);
		Mockito.when(productRepository.findAll()).thenReturn(list_of_Product);
		assertThat(productService.getAllProduct()).isEqualTo(list_of_Product);
	}
	
	@Test
	void testFindproductById() throws ResourceNotFoundException{
		Product product=new Product();
		product.setId(Long.valueOf(4));
		product.setName("Mi Note 10");
		product.setCategory("Electronics");
		product.setDescription("4 gb Ram, 4700 mah Battery,32 mp camera");
		product.setPrice(Long.valueOf(12000));
		Mockito.when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
		assertThat(productService.findProductById(product.getId())).isEqualTo(product);
	}
	
	@Test
	void testFindproductByName() throws ResourceNotFoundException{
		Product product = new Product();
		product.setId(Long.valueOf(5));
		product.setName("Lenovo K8 Plus");
		product.setCategory("Electronics");
		product.setDescription("3 gb Ram, 4700 mah Battery,16 mp camera");
		product.setPrice(Long.valueOf(8000));
		
		List<Product> productList = new ArrayList<>();
		productList.add(product);
		
		Mockito.when(productRepository.findAll()).thenReturn(productList);
		assertThat(productService.findProductByName(product.getName())).isEqualTo(product);
	}
	
	@Test
	void testFindproductByCategory() throws ResourceNotFoundException{
		Product product=new Product();
		product.setId(Long.valueOf(6));
		product.setName("Samsung Note 10");
		product.setCategory("Electronics");
		product.setDescription("3 gb Ram, 4700 mah Battery,16 mp camera");
		product.setPrice(Long.valueOf(8000));
		
		List<Product> productList = new ArrayList<>();
		productList.add(product);
		
		Mockito.when(productRepository.findAll()).thenReturn(productList);
		assertThat(productService.findProductByCategory(product.getCategory())).isEqualTo(productList);
	}
	
	@Test
	void testUpdateProduct() throws ResourceNotFoundException{
		Product product=new Product();
		product.setId(Long.valueOf(7));
		product.setName("Carol Engineered Wood King Bed");
		product.setCategory("Furniture");
		product.setDescription("Depth 205cm, Width 190cm, Height 80cm");
		product.setPrice(Long.valueOf(8000));
		
		Product product1=new Product();
		product1.setId(Long.valueOf(7));
		product1.setName("Carol Engineered Wood King Bed");
		product1.setCategory("Furniture");
		product1.setDescription("Depth 205cm, Width 190cm, Height 80cm");
		product1.setPrice(Long.valueOf(15000));
		
		Mockito.when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
		Mockito.when(productRepository.save(product)).thenReturn(product1);
		assertThat(productService.updateProduct(product.getId(), product).getPrice()).isEqualTo(product1.getPrice());	
	}
	
	@Test
	void testDeleteProductById() throws ResourceNotFoundException {
		Product product=new Product();
		product.setId(Long.valueOf(1));
		product.setName("Samsung Note 10");
		product.setCategory("Electronics");
		product.setDescription("3 gb Ram, 4700 mah Battery,16 mp camera");
		product.setPrice(Long.valueOf(9000));
		Mockito.when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
		productService.deleteProduct(product.getId());
		verify(productRepository, times(1)).deleteById(product.getId());
	} 

}
