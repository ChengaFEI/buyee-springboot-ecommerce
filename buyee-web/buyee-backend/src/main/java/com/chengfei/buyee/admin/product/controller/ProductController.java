package com.chengfei.buyee.admin.product.controller;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chengfei.buyee.admin.AmazonS3Util;
import com.chengfei.buyee.admin.brand.BrandService;
import com.chengfei.buyee.admin.category.CategoryService;
import com.chengfei.buyee.admin.product.ProductNotFoundException;
import com.chengfei.buyee.admin.product.ProductService;
import com.chengfei.buyee.common.entity.Brand;
import com.chengfei.buyee.common.entity.Category;
import com.chengfei.buyee.common.entity.Product;
@Controller
public class ProductController {
    @Autowired private ProductService productService;
    @Autowired private BrandService brandService;
    @Autowired private CategoryService categoryService;
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
   	model.addAttribute("numExistingExtraImages", 0);
   	return "/webpages/products/products_form";
    }	
    @PostMapping("/products/save")
    public String submitProduct(
	    Product product, RedirectAttributes redirectAttributes, 
	    // Images Section
	    @RequestParam("imageFile") MultipartFile mainImageMultipart,
	    @RequestParam(name = "extraImageFile", required = false) MultipartFile[] extraImageMultiparts,
	    @RequestParam(name = "extraImageIds", required = false) String[] existingExtraImageIds,
	    @RequestParam(name = "extraImageNames", required = false) String[] existingExtraImageNames,
	    // Details Section
	    @RequestParam(name = "detailIds", required = false) String[] detailIds,
	    @RequestParam(name = "detailNames", required = false) String[] detailNames,
	    @RequestParam(name = "detailValues", required = false) String[] detailValues
	    ) throws IOException {
	// Save Images in Database
	ProductControllerSaveUtil.setMainImageName(product, mainImageMultipart);
	ProductControllerSaveUtil.setExistingExtraImageNames(product, existingExtraImageIds, existingExtraImageNames);
	ProductControllerSaveUtil.setNewExtraImageNames(product, extraImageMultiparts);
	// Save Details in Database
	ProductControllerSaveUtil.setDetails(product, detailIds, detailNames, detailValues);
	Product savedProduct = productService.saveProduct(product);
	// Process Images in Amazon S3
	ProductControllerSaveUtil.uploadImages(savedProduct, mainImageMultipart, extraImageMultiparts);
	ProductControllerSaveUtil.deleteRemovedExtraImages(savedProduct);
	// Redirect Messages back
	redirectAttributes.addFlashAttribute("message", "Product saved successfully!");
	return "redirect:/products/page/1?keyword=" + product.getName();
    }
    // Read Tasks
    @GetMapping("/products")
    public String readProductsInFirstPage(Model model) {
	return readProductsByPageNum(1, null, null, null, model);
    }
    @GetMapping("/products/page/{pageNum}")
    public String readProductsByPageNum(
	    @PathVariable(name = "pageNum") int pageNum, 
	    @Param("sortField") String sortField,
	    @Param("sortOrder") String sortOrder, 
	    @Param("keyword") String keyword,
	    Model model) {
	Page<Product> page = productService.readProductsByPageNum(pageNum, sortField, sortOrder, keyword);
	List<Product> listProducts = page.getContent();
	long totalElements = page.getTotalElements();
	long totalPages = page.getTotalPages();
	long startCount = (pageNum - 1) * ProductService.PRODUCTS_PER_PAGE + 1;
	long endCount = startCount + ProductService.PRODUCTS_PER_PAGE - 1;
	endCount = Math.min(totalElements, endCount);
	List<Category> listCategories = categoryService.readCategoriesInForm();
	model.addAttribute("pageNum", pageNum);
	model.addAttribute("listProducts", listProducts);
	model.addAttribute("totalElements", totalElements);
	model.addAttribute("totalPages", totalPages);
	model.addAttribute("startCount", startCount);
	model.addAttribute("endCount", endCount);
	model.addAttribute("listCategories", listCategories);
	if (sortField != null && sortOrder != null) {
	    String reverseOrder = sortOrder.equals("asc") ? "desc" : "asc";
	    model.addAttribute("sortField", sortField);
	    model.addAttribute("sortOrder", sortOrder);
	    model.addAttribute("reverseOrder", reverseOrder);
	}
	if (keyword != null) {
	    model.addAttribute("keyword", keyword);
	}
	return "/webpages/products/products";
    }
    @GetMapping("/products/view/{id}")
    public String viewProductById(
	    @PathVariable(name = "id") Integer id,
	    Model model, RedirectAttributes redirectAttributes) {
	try {
	    Product product = productService.readProductById(id);
	    model.addAttribute("product", product);
	    return "/webpages/products/products_modal";
	} catch (ProductNotFoundException e) {
	    redirectAttributes.addFlashAttribute("message", e.getMessage());
	    return "redirect:/products";
	}
    }
    // Update Tasks 
    @GetMapping("/products/edit/{id}")
    public String updateProductById(
	    @PathVariable(name = "id") Integer id, 
	    Model model, RedirectAttributes redirectAttributes) {
	try {
	    Product product = productService.readProductById(id);
	    List<Brand> listBrands = brandService.readAllBrandsIdNameAscByName();
	    Integer numExistingExtraImages = product.getImages().size();
	    model.addAttribute("product", product);
	    model.addAttribute("listBrands", listBrands);
	    model.addAttribute("numExistingExtraImages", numExistingExtraImages);
	    model.addAttribute("pageTitle", "Update Product (ID: " + id + ")");
	    return "/webpages/products/products_form";
	} catch (ProductNotFoundException e) {
	    redirectAttributes.addFlashAttribute("message", e.getMessage());
	    return "redirect:/products";
	}
    }
    @GetMapping("/products/{id}/enabled/{status}")
    public String updateProductEnabledStatus(
	    @PathVariable(name = "id") Integer id,
	    @PathVariable(name = "status") boolean status, 
	    @Param("pageNum") int pageNum,
	    @Param("keyword") String keyword, 
	    @Param("sortField") String sortField, 
	    @Param("sortOrder") String sortOrder, 
	    RedirectAttributes redirectAttributes) {
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
	    AmazonS3Util.deleteFolder("product-images/" + id + "/extras");
	    AmazonS3Util.deleteFolder("product-images/" + id + "/");
	    redirectAttributes.addFlashAttribute("message", "Successfully delete product with ID " + id + "!");
	} catch (ProductNotFoundException e) {
	    redirectAttributes.addFlashAttribute("message", e.getMessage());
	}
	return "redirect:/products";
    }
}
