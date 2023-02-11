package com.chengfei.buyee.admin.category;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.chengfei.buyee.common.entity.Category;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CategoryRepositoryTests {
    @Autowired
    private CategoryRepository repo;
    
    // Create Tasks
    
    @Test
    public void testCreateRootCategory() {
	Category category1 = new Category("Computers");
	Category category2 = new Category("Electronics");
	Category savedCategory1 = repo.save(category1);
	Category savedCategory2 = repo.save(category2);
	assertThat(savedCategory1.getId()).isGreaterThan(0);
	assertThat(savedCategory2.getId()).isGreaterThan(0);
    }
    
    @Test
    public void testCreateSubCategory() {
	Category parent1 = new Category(1);
	Category parent2 = new Category(2);
	Category category1 = new Category("Desktops", parent1);
	Category category2 = new Category("Laptops", parent1);
	Category category3 = new Category("Computer Components", parent1);
	Category category4 = new Category("Cameras", parent2);
	Category category5 = new Category("Smartphones", parent2);
	Category savedCategory1 = repo.save(category1);
	Category savedCategory2 = repo.save(category2);
	Category savedCategory3 = repo.save(category3);
	Category savedCategory4 = repo.save(category4);
	Category savedCategory5 = repo.save(category5);
	assertThat(savedCategory1.getId()).isGreaterThan(0);
	assertThat(savedCategory2.getId()).isGreaterThan(0);
	assertThat(savedCategory3.getId()).isGreaterThan(0);
	assertThat(savedCategory4.getId()).isGreaterThan(0);
	assertThat(savedCategory5.getId()).isGreaterThan(0);
	
	Category parent3 = new Category(5);
	Category category6 = new Category("Memory", parent3);
	Category savedCategory6 = repo.save(category6);
	assertThat(savedCategory6.getId()).isGreaterThan(0);
	
	Category parent4 = new Category(4);
	Category category7 = new Category("Gaming Laptops", parent4);
	Category savedCategory7 = repo.save(category7);
	assertThat(savedCategory7.getId()).isGreaterThan(0);
	
	Category parent5 = new Category(7);
	Category category8 = new Category("iPhone", parent5);
	Category savedCategory8 = repo.save(category8);
	assertThat(savedCategory8.getId()).isGreaterThan(0);
    }
    
    // Read Tasks
    
    @Test
    public void readAllCategoryById () {
	Category category = repo.findById(2).get();
	assertThat(category).isNotNull();
	assertEquals(category.getName(), "Electronics");
	Set<Category> children = category.getChildren();
	assertThat(children.size()).isGreaterThan(0);
    }
    
    @Test
    public void testPrintHierarchicalCategories() {
	Iterable<Category> categories = repo.findAll();
	for (Category category : categories) {
	    if (category.getParent() == null) printCategory(category, 0);
	}
    }
    
    private void printCategory(Category category, int level) {
	if (category == null || category.getName() == null) return;
	for (int i = 0; i < level-1; i++) System.out.printf("  ");
	if (level > 0) System.out.printf("| ");
	System.out.println(category.getName());
	Set<Category> children = category.getChildren();
	for (Category subcategory : children) printCategory(subcategory, level+1);
    }
    
    @Test
    public void testFindByName() {
	String name1 = "Computers";
	Category category1 = repo.findByName(name1);
	assertThat(category1).isNotNull();
	assertEquals(category1.getName(), name1);
	String name2 = "Computer";
	Category category2 = repo.findByName(name2);
	assertThat(category2).isNull();
    }
    
    @Test
    public void testFindByAlias() {
	String alias1 = "Computers";
	Category category1 = repo.findByAlias(alias1);
	assertThat(category1).isNotNull();
	assertEquals(category1.getAlias(), alias1);
	String alias2 = "Computer2";
	Category category2 = repo.findByAlias(alias2);
	assertThat(category2).isNull();
    }
}
