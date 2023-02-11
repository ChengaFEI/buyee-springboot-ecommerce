package com.chengfei.buyee.admin.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.chengfei.buyee.common.entity.Brand;
import com.chengfei.buyee.common.entity.Category;
import com.chengfei.buyee.common.entity.Product;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ProductRepositoryTests {
    @Autowired
    private ProductRepository repo;
    
    @Autowired
    private TestEntityManager manager;
    
    // Create Tasks
    
    @Test
    public void testCreateProduct() {
	Brand brand = manager.find(Brand.class, 1); 
	Category category = manager.find(Category.class, 15);
	Product product = new Product.ProductBuilder("Samsung Galaxy A31", 
						     "samsung_galaxy_a31", 
						     "A good smartphone from Samsung", 
						     "This is a very good smartphone")
				     .setBrand(brand)
				     .setCategory(category)
				     .setPrice(456)
				     .setCreatedTime(new Date())
				     .setUpdatedTime(new Date())
				     .build();
	Product savedProduct = repo.save(product);
	assertNotNull(savedProduct);
	assertThat(savedProduct.getId()).isGreaterThan(0);
    }
    
    // Read Tasks
    
    @Test
    public void testReadAllProducts() {
	Iterable<Product> products = repo.findAll();
	products.forEach(System.out::println);
    }
    
    @Test
    public void testReadProductById() {
	Integer id =  1;
	Product product = repo.findById(id).get();
	assertNotNull(product);
    }
    
    // Update Tasks
    
    @Test
    public void testUpdateProductPrice() {
	Integer id = 1;
	float price = 1000;
	Product product = repo.findById(id).get();
	product.setPrice(price);
	repo.save(product);
	Product updatedProduct = manager.find(Product.class, 1);
	assertEquals(updatedProduct.getPrice(), price);
    }
    
    @Test
    public void testUpdateProductImages() {
	Integer id = 2;
	Product product = repo.findById(id).get();
	product.setMainImage("main_image.png");
	product.addExtraImage("extraImage1.png");
	product.addExtraImage("extra_image_2.png");
	product.addExtraImage("extra -image-3.png");
	Product savedProduct = repo.save(product);
	assertEquals(savedProduct.getImages().size(), 3);
    }
    
    // Delete Tasks
    @Test
    public void testDeleteProduct() {
	Integer id = 1;
	repo.deleteById(id);
	Optional<Product> product = repo.findById(id);
	assertFalse(product.isPresent());
    }
}
