package com.chengfei.buyee.admin.brand.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chengfei.buyee.admin.brand.BrandService;
import com.chengfei.buyee.common.entity.Brand;
import com.chengfei.buyee.common.entity.Category;
import com.chengfei.buyee.common.exception.BrandNotFoundException;
@RestController
public class BrandRestController {
    @Autowired BrandService service;
    // Read Tasks
    @GetMapping("/brands/{id}/categories")
    public List<CategoryDTO> readCategoriesById(@PathVariable(name = "id") Integer id) throws BrandNotFoundRestException {
	List<CategoryDTO> listCategories = new ArrayList<>(); 
	try {
	    Brand brand = service.readBrandById(id);
	    Set<Category> categories = brand.getCategories();
	    for (Category category: categories) {
		CategoryDTO dto = new CategoryDTO(category.getId(), category.getName());
		listCategories.add(dto);
	    }
	    listCategories.sort((c1, c2) -> {
		return c1.getName().compareToIgnoreCase(c2.getName());
	    });
	    return listCategories;
	} catch (BrandNotFoundException e) {
	    throw new BrandNotFoundRestException();
	}
    }
    // Validate Tasks
    @PostMapping("/brands/check_name")
    public String checkDuplicateName(@Param("id") Integer id, @Param("name") String name) {
	return service.isNameUnique(id, name) ? "OK" : "Duplicate";
    }
}
