package com.chengfei.buyee.admin.product.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.chengfei.buyee.admin.product.ProductService;
@RestController
public class ProductRestController {
    @Autowired ProductService service;
    // Validate Tasks
    @PostMapping("/products/check_name")
    public String checkDuplicateName(@Param("id") Integer id, @Param("name") String name) {
	return service.isNameUnique(id, name) ? "OK" : "Duplicate";
    }
}
