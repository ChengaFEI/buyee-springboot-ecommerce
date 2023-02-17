package com.chengfei.buyee.product.controller;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.chengfei.buyee.category.CategoryService;
import com.chengfei.buyee.common.entity.Category;
import com.chengfei.buyee.common.entity.Product;
import com.chengfei.buyee.common.exception.CategoryNotFoundException;
import com.chengfei.buyee.product.ProductService;
@Controller
public class ProductController {
    @Autowired private CategoryService categoryService;
    @Autowired private ProductService productService;
    // Read Tasks
    @GetMapping("/c/{category_alias}")
    public String readProductsByCategoryInFirstPage(
	    Model model,
	    @PathVariable("category_alias") String alias) {
	return readProductsByCategoryAndPageNum(model, alias, 1);
    }
    @GetMapping("/c/{category_alias}/page/{pageNum}")
    public String readProductsByCategoryAndPageNum(
	    Model model,
	    @PathVariable("category_alias") String alias,
	    @PathVariable("pageNum") int pageNum) {
	Category category;
	try {
	    category = categoryService.readEnabledCategoryByAlias(alias);
	    String categoryAlias = category.getAlias();
	    List<Category> listCategoryChildren = new ArrayList<>(category.getChildren());
	    listCategoryChildren.sort(Comparator.comparing(Category::getName));
	    List<Category> listCategoryParents = categoryService.readCategoryParents(category);
	    Page<Product> pageProducts = productService.readProductsByCategory(pageNum, category.getId());
	    List<Product> listProducts = pageProducts.getContent();
	    long totalElements = pageProducts.getTotalElements();
	    long totalPages = pageProducts.getTotalPages();
	    long startCount = (pageNum - 1) * ProductService.PRODUCTS_PER_PAGE + 1;
	    long endCount = startCount + ProductService.PRODUCTS_PER_PAGE - 1;
	    endCount = Math.min(totalElements, endCount);
	    model.addAttribute("categoryAlias", categoryAlias);
	    model.addAttribute("category", category);
	    model.addAttribute("listCategoryChildren", listCategoryChildren);
	    model.addAttribute("listCategoryParents", listCategoryParents);
	    model.addAttribute("listProducts", listProducts);
	    model.addAttribute("pageNum", pageNum);
	    model.addAttribute("totalElements", totalElements);
	    model.addAttribute("totalPages", totalPages);
	    model.addAttribute("startCount", startCount);
	    model.addAttribute("endCount", endCount);
	    model.addAttribute("pageTitle", category.getName());
	    return "webpages/products_by_category";
	} catch (CategoryNotFoundException e) {return "error/404";}
    }
}
