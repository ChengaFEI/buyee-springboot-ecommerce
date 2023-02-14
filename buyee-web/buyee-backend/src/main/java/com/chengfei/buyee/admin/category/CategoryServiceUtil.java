package com.chengfei.buyee.admin.category;
import com.chengfei.buyee.common.entity.Category;
public class CategoryServiceUtil {
    static Category fullCopyCategory(Category category) {
	return new Category(category.getId(), category.getName(), category.getAlias(), 
			    category.getImage(), category.isEnabled(), category.getLevel(), 
			    category.getParent(), category.getChildren());
    }
    static void setSelfAndSubAllParentIds(Category category) {
	// Set self's allParentsIds
	if (category == null) return;
	Category parent = category.getParent();
	if (parent == null) category.setAllParentIds(null);
	else {
	    String allParentIds = parent.getAllParentIds() == null ? "-" : parent.getAllParentIds();
	    allParentIds += String.valueOf(parent.getId()) + "-";
	    category.setAllParentIds(allParentIds);
	}
	// Set sub-categories' allParentIds
	for (Category subCategory: category.getChildren())
	    setSelfAndSubAllParentIds(subCategory);
    }
    static void setSelfAndSubLevel(Category category) {
	// Set self's level
	if (category == null) return;
	Category parent = category.getParent();
	if (parent == null) category.setLevel(0);
	else category.setLevel(parent.getLevel()+1);
	// Set sub-categories' level
	for (Category subCategory: category.getChildren()) 
	    setSelfAndSubLevel(subCategory);
    }
}
