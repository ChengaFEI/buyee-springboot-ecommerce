package com.chengfei.buyee.admin.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chengfei.buyee.admin.brand.BrandService;
import com.chengfei.buyee.admin.product.ProductNotFoundException;
import com.chengfei.buyee.admin.product.ProductService;
import com.chengfei.buyee.common.entity.Brand;
import com.chengfei.buyee.common.entity.Product;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    
    @Autowired
    private BrandService brandService;
    
    // Create Tasks
    
    @GetMapping("/products/new")
    public String createProduct(Model model) {
	Product product = new Product();
	product.setEnabled(true);
	product.setInStock(true);
   	List<Brand> listBrands = brandService.readAllBrandsIdNameAscByName();
   	model.addAttribute("product", product);
   	model.addAttribute("listBrands", listBrands);
   	model.addAttribute("pageTitle", "Create Product");
   	return "products/products_form";
    }	
    
    @PostMapping("/products/save")
    public String submitProduct(Product product, RedirectAttributes redirectAttributes) {
	productService.saveProduct(product);
	redirectAttributes.addFlashAttribute("message", "Product saved successfully!");
	return "redirect:/products";
    }
    
    // Read Tasks
    
    @GetMapping("/products")
    public String readProductsInFirstPage(Model model) {
	return readProductsByPageNum(1, null, null, null, model);
    }

    @GetMapping("/products/page/{pageNum}")
    public String readProductsByPageNum(@PathVariable(name = "pageNum") int pageNum, @Param("sortField") String sortField,
	    @Param("sortOrder") String sortOrder, @Param("keyword") String keyword, Model model) {
	Page<Product> page = productService.readProductsByPageNum(pageNum, sortField, sortOrder, keyword);
	List<Product> listProducts = page.getContent();
	long totalElements = page.getTotalElements();
	long startCount = (pageNum - 1) * ProductService.PRODUCTS_PER_PAGE + 1;
	long endCount = startCount + ProductService.PRODUCTS_PER_PAGE - 1;
	endCount = Math.min(totalElements, endCount);

	model.addAttribute("pageNum", pageNum);
	model.addAttribute("listProducts", listProducts);
	model.addAttribute("totalElements", totalElements);
	model.addAttribute("totalPages", page.getTotalPages());
	model.addAttribute("startCount", startCount);
	model.addAttribute("endCount", endCount);
	if (sortField != null && sortOrder != null) {
	    model.addAttribute("sortField", sortField);
	    model.addAttribute("sortOrder", sortOrder);
	    String reverseOrder = sortOrder.equals("asc") ? "desc" : "asc";
	    model.addAttribute("reverseOrder", reverseOrder);
	}
	if (keyword != null) {
	    model.addAttribute("keyword", keyword);
	}

	return "products/products";
    }
    
    // Update Tasks
    
    @GetMapping("/products/{id}/enabled/{status}")
    public String updateProductEnabledStatus(@PathVariable(name = "id") Integer id,
	    @PathVariable(name = "status") boolean status, @Param("pageNum") int pageNum,
	    @Param("keyword") String keyword, @Param("sortField") String sortField, 
	    @Param("sortOrder") String sortOrder, RedirectAttributes redirectAttributes) {
	productService.updateProductEnabledStatus(id, status);
	String enabledStr = status ? "enable" : "disable";
	redirectAttributes.addFlashAttribute("message", "Successfully " + enabledStr + " product with ID " + id + ".");
	String redirectURL = "redirect:/products/page/" + pageNum + "?";
	boolean urlHasParam = false;
	if (keyword != null) {
	    urlHasParam = true;
	    redirectURL += "keyword=" + keyword;
	}
	if (sortField != null && !urlHasParam) {
	    urlHasParam = true;
	    redirectURL += "sortField=" + sortField;
	} else if (sortField != null && urlHasParam) redirectURL += "&sortField=" + sortField;
	if (sortOrder != null && !urlHasParam) {
	    urlHasParam = true;
	    redirectURL += "sortOrder=" + sortOrder;
	} else if (sortOrder != null && urlHasParam) redirectURL += "&sortOrder=" + sortOrder;
	return redirectURL;
    }

    // Delete Tasks

    @GetMapping("/products/delete/{id}")
    public String deleteProductById(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
	try {
	    productService.deleteProductById(id);
//	    AmazonS3Util.deleteFolder("product-images/" + id + "/");
	    redirectAttributes.addFlashAttribute("message", "Successfully delete product with ID " + id + "!");
	} catch (ProductNotFoundException e) {
	    redirectAttributes.addFlashAttribute("message", e.getMessage());
	}
	return "redirect:/products";
    }
}
