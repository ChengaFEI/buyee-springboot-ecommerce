package com.chengfei.buyee.admin.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.chengfei.buyee.common.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Integer>, 
					   PagingAndSortingRepository<Product, Integer> {
    // Read Tasks
    
    public Long countProductById(Integer id);
    
    public Product findByName(String name);
    
    @Query("SELECT p FROM Product p WHERE CONCAT(p.id, ' ', p.name, ' ', p.alias, ' ', p.brand.name, ' ', p.category.name) LIKE %?1%")
    public Page<Product> readProductsByKeyword(String keyword, Pageable pageable);

    
    // Update Tasks

    @Modifying
    @Query("UPDATE Product p SET p.enabled = ?2 WHERE p.id = ?1")
    public void updateProductEnabledStatus(Integer id, boolean enabled);
}
