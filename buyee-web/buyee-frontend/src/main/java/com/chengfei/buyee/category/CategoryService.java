package com.chengfei.buyee.category;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chengfei.buyee.common.entity.Category;
import com.chengfei.buyee.common.exception.CategoryNotFoundException;
@Service
public class CategoryService {
    @Autowired private CategoryRepository repo;
    // Read Tasks
    public List<Category> readNoChildrenCategories() {
	List<Category> listNoChildrenCategories = new ArrayList<>();
	List<Category> listEnabledCategories = repo.readEnabledCategories();
	listEnabledCategories.forEach(category -> {
	    Set<Category> children = category.getChildren();
	    if (children == null || children.size() == 0) 
		listNoChildrenCategories.add(category);
	});
	return listNoChildrenCategories;
    }
    public Category readEnabledCategoryByAlias(String alias) throws CategoryNotFoundException {
	Category category = repo.readEnabledCategoryByAlias(alias);
	if (category == null) throw new CategoryNotFoundException("Could not find any category with alias " + alias);
	return category;
    }
    public List<Category> readCategoryParents(Category child) {
	List<Category> listCategoryParents = new ArrayList<>();
	Category parent = child.getParent();
	while (parent != null) {
	    listCategoryParents.add(0, parent);
	    parent = parent.getParent();
	}
	listCategoryParents.add(child);
	return listCategoryParents;
    }
    public List<Category> readRootCategories() {
	return repo.readRootCategories();
    }
}
