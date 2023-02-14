package com.chengfei.buyee.admin.category.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.chengfei.buyee.admin.category.CategoryService;
@RestController
public class CategoryRestController {
    @Autowired
    private CategoryService service;
    
    @PostMapping("/categories/check_namealias")
    public String checkDuplicateNameAlias(@Param("id") Integer id, @Param("name") String name, @Param("alias") String alias) {
	return service.isNameAliasUnique(id, name, alias);
    }
}
