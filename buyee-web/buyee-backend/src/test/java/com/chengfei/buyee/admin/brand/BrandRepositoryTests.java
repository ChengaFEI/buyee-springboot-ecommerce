package com.chengfei.buyee.admin.brand;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.chengfei.buyee.common.entity.Brand;
import com.chengfei.buyee.common.entity.Category;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class BrandRepositoryTests {
    @Autowired
    private BrandRepository repo;
    
    // Create Tasks
    
    @Test
    public void testCreateBrand() {
	Brand brand1 = new Brand("Apple");
	Brand savedBrand1 = repo.save(brand1);
	assertNotNull(savedBrand1);
	Brand brand2 = new Brand("Microsoft", "microsoft.png");
	Brand savedBrand2 = repo.save(brand2);
	assertNotNull(savedBrand2);
	Brand brand3 = new Brand("Nvidia");
	Brand savedBrand3 = repo.save(brand3);
	assertEquals(savedBrand3.getId(), 6);
    }
    
    @Test
    public void testSetCategories() {
	Set<Category> categories = new HashSet<>();
	Category category1 = new Category(1);
	categories.add(category1);
	Brand brand1 = new Brand("Samsung");
	brand1.setCategories(categories);
	Brand savedBrand1 = repo.save(brand1);
	assertNotNull(savedBrand1.getCategories());
	assertThat(savedBrand1.getCategories().size()).isGreaterThan(0);
    }
    
    // Read Tasks
    
    @Test
    public void testReadAllBrands() {
	List<Brand> brands = (List<Brand>) repo.findAll();
	assertNotNull(brands);
	for (Brand brand: brands) {
	    System.out.println(brand);
	}
    }
    
    @Test
    public void testReadAllBrandsIdNameAscByName() {
	List<Brand> listBrands = repo.readAllBrandsIdNameAscByName();
	assertNotNull(listBrands);
	assertEquals(listBrands.size(), 54);
	listBrands.forEach(System.out::println);
    }
    
    // Update Tasks
    
    @Test
    public void testUpdateBrandName() {
	Brand brand1 = new Brand(1);
	String name = "Apple Inc.";
	brand1.setName(name);
	Brand savedBrand1 = repo.save(brand1);
	assertNotNull(savedBrand1);
	assertEquals(savedBrand1.getName(), name);
    }
    
    // Delete Tasks
    @Test
    public void testDeleteBrand() {
	Integer id1 = 3;
	repo.deleteById(id1);
	assertThat(repo.findById(id1).isEmpty());
    }
}
