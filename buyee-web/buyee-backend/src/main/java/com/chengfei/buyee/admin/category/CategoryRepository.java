package com.chengfei.buyee.admin.category;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.chengfei.buyee.common.entity.Category;
public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer>, 
					    CrudRepository<Category, Integer> {
    // Read Tasks
    public Long countCategoryById(Integer id);
    public Category findByName(String name);
    public Category findByAlias(String alias);
    @Query("SELECT c FROM Category c WHERE c.parent.id is NULL")
    public List<Category> readRootCategories();
    @Query("SELECT c FROM Category c WHERE c.parent.id is NULL")
    public List<Category> readRootCategories(Sort sort);
    @Query("SELECT c FROM Category c WHERE CONCAT(c.id, ' ', c.name, ' ', c.alias) LIKE %?1%")
    public Page<Category> readCategoriesByKeyword(String keyword, Pageable pageable);
    // Update Tasks
    @Modifying
    @Query("UPDATE Category c SET c.enabled = ?2 WHERE c.id = ?1")
    public void updateCategoryEnabledStatus(Integer id, boolean enabled);
}
