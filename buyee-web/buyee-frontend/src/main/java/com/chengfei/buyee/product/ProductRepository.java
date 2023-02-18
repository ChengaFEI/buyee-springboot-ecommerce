package com.chengfei.buyee.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.chengfei.buyee.common.entity.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Integer>, 
					   CrudRepository<Product, Integer> {
    // Read Tasks
    @Query("SELECT p FROM Product p WHERE p.enabled = true " + 
	   "AND (p.category.id = ?1 " + 
	   "OR p.category.allParentIds LIKE %?2%) " + 
	   "ORDER BY p.name ASC")
    public Page<Product> readProductsByCategory(Integer categoryId, String categoryIdMatch, Pageable pageable);
    public Product readProductByAlias(String alias);
    @Query(value= "SELECT * FROM products WHERE enabled = true " +
	    	  "AND MATCH(name, short_description, full_description) AGAINST (?1)",
	   nativeQuery = true)
    public Page<Product> readProductsByKeyword(String keyword, Pageable pageable);
}
