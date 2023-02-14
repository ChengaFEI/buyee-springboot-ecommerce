package com.chengfei.buyee.admin.category;
import com.chengfei.buyee.common.entity.Category;
public class CategoryServiceUtil {
    static Category fullCopyCategory(Category category) {
	return new Category(category.getId(), category.getName(), category.getAlias(), 
			    category.getImage(), category.isEnabled(), category.getLevel(), 
			    category.getParent(), category.getChildren());
    }
}
