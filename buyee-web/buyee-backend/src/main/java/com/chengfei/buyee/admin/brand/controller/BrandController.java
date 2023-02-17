package com.chengfei.buyee.admin.brand.controller;
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
import com.chengfei.buyee.admin.brand.export.BrandCsvExporter;
import com.chengfei.buyee.admin.brand.export.BrandExcelExporter;
import com.chengfei.buyee.admin.brand.export.BrandPdfExporter;
import com.chengfei.buyee.admin.category.CategoryService;
import com.chengfei.buyee.common.entity.Brand;
import com.chengfei.buyee.common.entity.Category;
import com.chengfei.buyee.common.exception.BrandNotFoundException;
import com.chengfei.buyee.common.exception.UserNotFoundException;

import jakarta.servlet.http.HttpServletResponse;
@Controller
public class BrandController {
    @Autowired private BrandService brandService;
    @Autowired private CategoryService categoryService;
    // Create Tasks
    @GetMapping("/brands/new")
    public String createBrand(Model model) {
	Brand brand = new Brand();
	List<Category> listCategories = categoryService.readCategoriesInForm();
	model.addAttribute("brand", brand);
	model.addAttribute("listCategories", listCategories);
	model.addAttribute("pageTitle", "Create Brand");
	return "/webpages/brands/brands_form";
    }
    @PostMapping("/brands/save")
    public String saveBrand(
	    Brand brand, RedirectAttributes redirectAttributes,
	    @RequestParam("imageFile") MultipartFile multipartFile) throws IOException {
	if (!multipartFile.isEmpty()) {
	    String fileName = brand.getName().toLowerCase().replace(" ", "_") + ".png";
	    brand.setLogo(fileName);
	    brandService.saveBrand(brand);
	    String folderName = "brand-logos/" + brand.getId();
	    AmazonS3Util.deleteFolder(folderName + "/");
	    AmazonS3Util.saveFile(folderName, fileName, multipartFile.getInputStream());
	} else {
	    if (brand.getLogo().isEmpty()) brand.setLogo(null);
	    brandService.saveBrand(brand);
	}
	redirectAttributes.addFlashAttribute("message", "Brand saved successfully!");
	return "redirect:/brands/page/1?keyword=" + brand.getName();
    }
    // Read Tasks
    @GetMapping("/brands")
    public String readAllBrands(Model model) {
	return readBrandsByPageNum(1, null, null, null, model);
    }
    @GetMapping("/brands/page/{pageNum}")
    public String readBrandsByPageNum(
	    @PathVariable(name = "pageNum") int pageNum, 
	    @Param("sortField") String sortField,
	    @Param("sortOrder") String sortOrder, 
	    @Param("keyword") String keyword, 
	    Model model) {
	Page<Brand> page = brandService.readBrandsByPageNum(pageNum, sortField, sortOrder, keyword);
	List<Brand> listBrands = page.getContent();
	for (Brand brand: listBrands)
	    brand.setCategories(categoryService.sortCategories(brand.getCategories()));
	long totalElements = page.getTotalElements();
	long startCount = (pageNum - 1) * BrandService.BRANDS_PER_PAGE + 1;
	long endCount = startCount + BrandService.BRANDS_PER_PAGE - 1;
	endCount = Math.min(totalElements, endCount);
	model.addAttribute("pageNum", pageNum);
	model.addAttribute("listBrands", listBrands);
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
	return "/webpages/brands/brands";
    }
    // Update Tasks
    @GetMapping("/brands/edit/{id}")
    public String updateBrandById(
	    @PathVariable(name = "id") Integer id, 
	    Model model, RedirectAttributes redirectAttributes) {
	try {
	    Brand brand = brandService.readBrandById(id);
	    List<Category> listCategories = categoryService.readCategoriesInForm();
	    model.addAttribute("brand", brand);
	    model.addAttribute("listCategories", listCategories);
	    model.addAttribute("pageTitle", "Update Brand (ID: " + id + ")");
	    return "/webpages/brands/brands_form";
	} catch (BrandNotFoundException e) {
	    redirectAttributes.addFlashAttribute("message", e.getMessage());
	    return "redirect:/brands";
	}
    }
    // Delete Tasks
    @GetMapping("/brands/delete/{id}")
    public String deleteBrandById(
	    @PathVariable(name = "id") Integer id, 
	    RedirectAttributes redirectAttributes) {
	try {
	    brandService.deleteBrandById(id);
	    AmazonS3Util.deleteFolder("brand-logos/" + id + "/");
	    redirectAttributes.addFlashAttribute("message", "Successfully delete user with ID " + id + "!");
	} catch (UserNotFoundException e) {
	    redirectAttributes.addFlashAttribute("message", e.getMessage());
	}
	return "redirect:/brands";
    }
    // Export Tasks
    @GetMapping("/brands/export/csv")
    public void exportToCsv(HttpServletResponse response) throws IOException {
	List<Brand> listBrands = brandService.readAllBrands();
	for (Brand brand: listBrands) 
	    brand.setCategories(categoryService.sortCategories(brand.getCategories()));
	BrandCsvExporter exporter = new BrandCsvExporter();
	exporter.export(listBrands, response);
    }
    @GetMapping("/brands/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
	List<Brand> listBrands = brandService.readAllBrands();
	for (Brand brand: listBrands) 
	    brand.setCategories(categoryService.sortCategories(brand.getCategories()));
	BrandExcelExporter exporter = new BrandExcelExporter();
	exporter.export(listBrands, response);
    }
    @GetMapping("/brands/export/pdf")
    public void exportToPdf(HttpServletResponse response) throws IOException {
	List<Brand> listBrands = brandService.readAllBrands();
	for (Brand brand: listBrands) 
	    brand.setCategories(categoryService.sortCategories(brand.getCategories()));
	BrandPdfExporter exporter = new BrandPdfExporter();
	exporter.export(listBrands, response);
    }
}
