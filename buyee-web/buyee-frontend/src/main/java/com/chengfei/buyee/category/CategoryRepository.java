package com.chengfei.buyee.category;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.chengfei.buyee.common.entity.Category;
public interface CategoryRepository extends CrudRepository<Category, Integer> {
    // Read Tasks
    @Query("SELECT c FROM Category c WHERE c.enabled = true ORDER BY c.name ASC")
    public List<Category> readEnabledCategories();
    @Query("SELECT c FROM Category c WHERE c.enabled = true AND c.alias = ?1")
    public Category readEnabledCategoryByAlias(String alias);
    @Query("SELECT c FROM Category c WHERE c.parent = null")
    public List<Category> readRootCategories();
}
