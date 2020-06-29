package com.meru.product.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.meru.product.model.Product;

@RunWith(SpringRunner.class)
@DataJpaTest
class ProductRepositoryTest {

	@Autowired
	private ProductRepository productRepository;

	@SuppressWarnings("deprecation")
	@Test
	void testSaveProduct() {
		Product product=new Product();
		product.setId(Long.valueOf(1));
		product.setName("Iphone 11");
		product.setCategory("Electronics");
		product.setDescription("8 gb Ram, 5000 mah Battery,64 mp camera");
		product.setPrice(Long.valueOf(65000));

		Product saveInDB=productRepository.save(product);
		Product getFromDB = productRepository.findById(saveInDB.getId()).get(); 
		assertThat(getFromDB.getId()).isEqualTo(saveInDB.getId());
		assertThat(saveInDB.getName(), containsString(getFromDB.getName()));
		assertThat(saveInDB.getDescription(), containsString(getFromDB.getDescription()));
		assertThat(saveInDB.getCategory(), containsString(getFromDB.getCategory()));
		assertThat(getFromDB.getPrice()).isEqualTo(saveInDB.getPrice());
	}

	@Test
	void testFindAll() {
		Product product=new Product();
		product.setId(Long.valueOf(2));
		product.setName("Realme XT");
		product.setCategory("Electronics");
		product.setDescription("6 gb Ram, 4000 mah Battery,48 mp camera");
		product.setPrice(Long.valueOf(25000));
		productRepository.save(product);
		List<Product> productList = new ArrayList<Product>(); 
		productRepository.findAll().forEach(productList :: add);

		assertThat(productList.size()).isEqualTo(1);
	}

	@SuppressWarnings("deprecation")
	@Test
	void testFindById() {
		Product product=new Product();
		product.setId(Long.valueOf(3));
		product.setName("Carol Engineered Wood King Bed");
		product.setCategory("Furniture");
		product.setDescription("Depth 205cm, Width 190cm, Height 80cm");
		product.setPrice(Long.valueOf(10990));
		Product saveInDB=productRepository.save(product);
		Product getFromDB = productRepository.findById(saveInDB.getId()).get(); 
		assertThat(getFromDB.getId()).isEqualTo(saveInDB.getId());
		assertThat(saveInDB.getName(), containsString(getFromDB.getName()));
		assertThat(saveInDB.getDescription(), containsString(getFromDB.getDescription()));
		assertThat(saveInDB.getCategory(), containsString(getFromDB.getCategory()));
		assertThat(getFromDB.getPrice()).isEqualTo(saveInDB.getPrice());
		productRepository.deleteById(product.getId());
	}

}
