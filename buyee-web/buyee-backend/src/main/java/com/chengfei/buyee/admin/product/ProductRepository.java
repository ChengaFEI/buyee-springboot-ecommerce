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
    @Query("SELECT p FROM Product p " + 
	   "WHERE CAST(p.id AS STRING) LIKE %?1% " + 
	   "OR p.name LIKE %?1% " + 
	   "OR p.alias LIKE %?1% " + 
	   "OR p.brand.name LIKE %?1% " + 
	   "OR p.category.name LIKE %?1% " +
	   "OR p.shortDescription LIKE %?1% " +
	   "OR p.fullDescription LIKE %?1%")
    public Page<Product> readProductsByKeyword(String keyword, Pageable pageable);
    @Query("SELECT p FROM Product p " + 
	   "WHERE p.category.id = ?1 " + 
	   "OR p.category.allParentIds LIKE %?2%")
    public Page<Product> readProductsByParentCategory(Integer categoryId, String categoryIdMatch, Pageable pageable);
    @Query("SELECT p FROM Product p " + 
	   "WHERE (CAST(p.id AS STRING) LIKE %?1% " + 
	   "OR p.name LIKE %?1% " + 
	   "OR p.alias LIKE %?1% " + 
	   "OR p.brand.name LIKE %?1% " + 
	   "OR p.category.name LIKE %?1% " +
	   "OR p.shortDescription LIKE %?1% " +
	   "OR p.fullDescription LIKE %?1%) " + 
	   "AND (p.category.id = ?2 " + 
	   "OR p.category.allParentIds LIKE %?3%)")
    public Page<Product> readProductsByKeywordParentCategory(
	    String keyword, Integer categoryId, String categoryIdMatch, Pageable pageable);
    // Update Tasks
    @Modifying
    @Query("UPDATE Product p SET p.enabled = ?2 WHERE p.id = ?1")
    public void updateProductEnabledStatus(Integer id, boolean enabled);
}
