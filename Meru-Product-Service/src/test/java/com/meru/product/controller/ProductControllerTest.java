package com.meru.product.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meru.product.model.Inventory;
import com.meru.product.model.Product;
import com.meru.product.model.ProductInventory;
import com.meru.product.model.ProductPromotionStore;
import com.meru.product.service.ProductService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ProductController.class)
@ImportAutoConfiguration(RefreshAutoConfiguration.class)
class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductService productService;
	
	@Test
	void testGetAllproduct() throws Exception{
		List<Product> list_of_Product = new ArrayList<>();
		Product product=new Product();
		product.setId(Long.valueOf(2));
		product.setName("Realme XT");
		product.setCategory("Electronics");
		product.setDescription("6 gb Ram, 4000 mah Battery,48 mp camera");
		product.setPrice(Long.valueOf(25000));
		list_of_Product.add(product);
		
		Mockito.when(productService.getAllProduct()).thenReturn(list_of_Product);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/product/");

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		List<ProductPromotionStore> list_of_Product_Returned = new ArrayList<>();
		ProductPromotionStore pps = new ProductPromotionStore();
		pps.setId(Long.valueOf(2));
		pps.setName("Realme XT");
		pps.setCategory("Electronics");
		pps.setDescription("6 gb Ram, 4000 mah Battery,48 mp camera");
		pps.setPrice(Long.valueOf(25000));
		pps.setStores(null);
		pps.setOffers(null);
		list_of_Product_Returned.add(pps);
		
		String expectedResponse = this.mapToJson(list_of_Product_Returned);
		String outputResponse = result.getResponse().getContentAsString();

		assertThat(expectedResponse).isEqualTo(outputResponse);

		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}
	
	@Test
	void testAddproduct() throws Exception{
		Product product=new Product();
		product.setId(Long.valueOf(1));
		product.setName("Mi Note 10");
		product.setCategory("Electronics");
		product.setDescription("4 gb Ram, 4700 mah Battery,32 mp camera");
		product.setPrice(Long.valueOf(12000));
		
		Inventory inventory = new Inventory();
		inventory.setOfflineProductQuantity(String.valueOf(5));
		inventory.setOnlineProductQuantity(String.valueOf(10));
		
		ProductInventory pi = new ProductInventory();
		pi.setProduct(product);
		pi.setInventory(inventory);
		
		ObjectMapper mapper=new ObjectMapper();
		String expectedResponse = mapper.writeValueAsString(pi);
		
		Mockito.when(productService.createProduct(pi.getProduct())).thenReturn(product);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/product/create")
				.content(expectedResponse)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		expectedResponse = mapper.writeValueAsString(pi.getProduct());
		String outputResponse = result.getResponse().getContentAsString();
		System.out.println(outputResponse);
		System.out.println(expectedResponse);
		assertThat(outputResponse).isEqualTo(expectedResponse);
		assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
	}
	
	@Test
	void testGetProductById() throws Exception{
		Product product=new Product();
		product.setId(Long.valueOf(4));
		product.setName("Mi Note 10");
		product.setCategory("Electronics");
		product.setDescription("4 gb Ram, 4700 mah Battery,32 mp camera");
		product.setPrice(Long.valueOf(12000));
		Mockito.when(productService.findProductById(Mockito.anyLong())).thenReturn(product);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/product/id/"+product.getId());

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		ObjectMapper mapper=new ObjectMapper();
		String expectedResponse = mapper.writeValueAsString(product);

		String outputResponse = result.getResponse().getContentAsString();

		assertThat(outputResponse).isEqualTo(expectedResponse);
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}
	
	@Test
	void testGetProductByName() throws Exception{
		Product product=new Product();
		product.setId(Long.valueOf(4));
		product.setName("Mi Note 10");
		product.setCategory("Electronics");
		product.setDescription("4 gb Ram, 4700 mah Battery,32 mp camera");
		product.setPrice(Long.valueOf(12000));
		Mockito.when(productService.findProductByName(Mockito.anyString())).thenReturn(product);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/product/name/"+product.getName());

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		ObjectMapper mapper=new ObjectMapper();
		String expectedResponse = mapper.writeValueAsString(product);

		String outputResponse = result.getResponse().getContentAsString();

		assertThat(outputResponse).isEqualTo(expectedResponse);
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}
	
	@Test
	void testGetProductByCategory() throws Exception{
		Product product=new Product();
		product.setId(Long.valueOf(4));
		product.setName("Mi Note 10");
		product.setCategory("Electronics");
		product.setDescription("4 gb Ram, 4700 mah Battery,32 mp camera");
		product.setPrice(Long.valueOf(12000));
		Mockito.when(productService.findProductByName(Mockito.anyString())).thenReturn(product);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/product/category/"+product.getCategory());

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		ObjectMapper mapper=new ObjectMapper();
		String expectedResponse = mapper.writeValueAsString(product);

		String outputResponse = result.getResponse().getContentAsString();

		assertThat(outputResponse).isEqualTo(expectedResponse);
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}
	
	@Test
	void testUpdateProduct() throws Exception{
		Product product=new Product();
		product.setId(Long.valueOf(5));
		product.setName("Mi Note 10");
		product.setCategory("Electronics");
		product.setDescription("4 gb Ram, 4700 mah Battery,32 mp camera");
		product.setPrice(Long.valueOf(12000));
		ObjectMapper mapper=new ObjectMapper();
		String expectedResponse = mapper.writeValueAsString(product);
		Mockito.when(productService.updateProduct(Mockito.anyLong(), Mockito.any(Product.class))).thenReturn(product);

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/product/update/"+product.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.content(expectedResponse);

		MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();

		String outputResponse = result.getResponse().getContentAsString();

		assertThat(outputResponse).isEqualTo(expectedResponse);
		assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	void testDeleteProductById() throws Exception{
		Mockito.when(productService.deleteProduct(Mockito.anyLong())).thenReturn("Success");

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/product/delete/5");

		MvcResult result = this.mockMvc.perform(requestBuilder).andReturn();

		String outputResponse = result.getResponse().getContentAsString();

		assertThat(outputResponse, containsString("Failure"));
		assertEquals(HttpStatus.FORBIDDEN.value(), result.getResponse().getStatus());
	}

	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
	
}
