package com.chengfei.buyee.category;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chengfei.buyee.common.entity.Category;
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
}
