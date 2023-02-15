package com.chengfei.buyee.admin.category.controller;
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
import com.chengfei.buyee.admin.category.CategoryNotFoundException;
import com.chengfei.buyee.admin.category.CategoryService;
import com.chengfei.buyee.admin.category.export.CategoryCsvExporter;
import com.chengfei.buyee.admin.category.export.CategoryExcelExporter;
import com.chengfei.buyee.admin.category.export.CategoryPdfExporter;
import com.chengfei.buyee.common.entity.Category;
import jakarta.servlet.http.HttpServletResponse;
@Controller
public class CategoryController {
    private static final int CATEGORIES_PER_PAGE = 5;
    @Autowired private CategoryService service;
    // Create Tasks
    @GetMapping("/categories/new")
    public String createCategory(Model model) {
	List<Category> listCategories = service.readCategoriesInForm();
	Category category = new Category();
	category.setEnabled(true);
	model.addAttribute("category", category);
	model.addAttribute("listCategories", listCategories);
	model.addAttribute("pageTitle", "Create Category");
	return "/webpages/categories/categories_form";
    }
    @PostMapping("/categories/save")
    public String saveCategory(
	    Category category, RedirectAttributes redirectAttributes,
	    @RequestParam("imageFile") MultipartFile multipartFile) throws IOException {
	if (!multipartFile.isEmpty()) {
	    String fileName = category.getAlias() + ".png";
	    category.setImage(fileName);
	    Category savedCategory = service.saveCategory(category);
	    String folderName = "category-images/" + savedCategory.getId();
	    AmazonS3Util.deleteFolder(folderName + "/");
	    AmazonS3Util.saveFile(folderName, fileName, multipartFile.getInputStream());
	} else {
	    if (category.getImage().isEmpty()) category.setImage(null);
	    service.saveCategory(category);
	}
	redirectAttributes.addFlashAttribute("message", "Category saved successfully!");
	return "redirect:/categories/page/1?keyword=" + category.getName();
    }
    // Read Tasks
    @GetMapping("/categories")
    public String readAllCategories(Model model) {
	return readCategoriesByPageNum(1, "name", "asc", null, model);
    }   
    @GetMapping("/categories/page/{pageNum}")
    public String readCategoriesByPageNum(
	    @PathVariable(name = "pageNum") int pageNum, 
	    @Param("sortField") String sortField, 
	    @Param("sortOrder") String sortOrder, 
	    @Param("keyword") String keyword, 
	    Model model) {
	if (keyword == null && (sortField == null || sortField.equals("name"))) {
	    model.addAttribute("hierarchy", true);
	    List<Category> listCategories = service.readCategoriesFullData(sortField, sortOrder);
	    model.addAttribute("listCategories", listCategories);
	    model.addAttribute("pageNum", pageNum);
	    if (sortField != null && sortOrder != null) {
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortOrder", sortOrder);
		model.addAttribute("reverseOrder", sortOrder.equals("asc") ? "desc" : "asc");
	    }
	} else {
	    model.addAttribute("hierarchy", false);
	    Page<Category> page = service.readCategoriesByPageNum(pageNum, CATEGORIES_PER_PAGE, 
		    						  sortField, sortOrder, keyword);
	    List<Category> listCategories = page.getContent();
	    long totalElements = page.getTotalElements();
	    long startCount = (pageNum - 1) * CATEGORIES_PER_PAGE + 1;
	    long endCount = startCount + CATEGORIES_PER_PAGE - 1;
	    endCount = Math.min(totalElements, endCount);
	    model.addAttribute("listCategories", listCategories);
	    model.addAttribute("keyword", keyword);
	    model.addAttribute("pageNum", pageNum);
	    model.addAttribute("totalElements", totalElements);
	    model.addAttribute("totalPages", page.getTotalPages());
	    model.addAttribute("startCount", startCount);
	    model.addAttribute("endCount", endCount);
	    if (sortField != null && sortOrder != null) {
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortOrder", sortOrder);
		model.addAttribute("reverseOrder", sortOrder.equals("asc") ? "desc" : "asc");
	    }
	}
	return "/webpages/categories/categories";
    }
    // Update Tasks
    @GetMapping("/categories/edit/{id}")
    public String updateCategoryById(
	    @PathVariable(name = "id") Integer id, 
	    RedirectAttributes redirectAttributes, Model model) {
	try {
	    Category category = service.readCategoryById(id);
	    List<Category> listCategories = service.readCategoriesInForm();
	    model.addAttribute("category", category);
	    model.addAttribute("listCategories", listCategories);
	    model.addAttribute("pageTitle", "Update Category (ID: " + id + ")");
	    return "/webpages/categories/categories_form";
	} catch (CategoryNotFoundException e) {
	    redirectAttributes.addFlashAttribute("message", e.getMessage());
	    return "redirect:/categories";
	}
    }
    @GetMapping("/categories/{id}/enabled/{status}")
    public String updateUserEnabledStatus(
	    @PathVariable(name = "id") Integer id,
	    @PathVariable(name = "status") boolean status, 
	    @Param("pageNum") int pageNum,
	    @Param("keyword") String keyword,
	    @Param("sortField") String sortField, 
	    @Param("sortOrder") String sortOrder, 
	    RedirectAttributes redirectAttributes) {
	service.updateCategoryEnabledStatus(id, status);
	String enabledStr = status ? "enable" : "disable";
	redirectAttributes.addFlashAttribute("message", "Successfully " + enabledStr + " category with ID " + id + ".");
	String redirectURL = "redirect:/categories/page/" + pageNum + "?";
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
    @GetMapping("/categories/delete/{id}")
    public String deleteCategoryById(@PathVariable(name = "id") Integer id, 
	    RedirectAttributes redirectAttributes) {
	try {
	    service.deleteCategoryById(id);
	    AmazonS3Util.deleteFolder("category-images/" + id + "/");
	    redirectAttributes.addFlashAttribute("message", "Successfully delete category with ID " + id + "!");
	} catch (CategoryNotFoundException e) {
	    redirectAttributes.addFlashAttribute("message", e.getMessage());
	}
	return "redirect:/categories";
    }
    // Export Tasks
    @GetMapping("/categories/export/csv")
    public void exportToCsv(HttpServletResponse response) throws IOException {
	List<Category> listCategories = service.readAllCategories();
	CategoryCsvExporter exporter = new CategoryCsvExporter();
	exporter.export(listCategories, response);
    }
    @GetMapping("/categories/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
	List<Category> listCategories = service.readAllCategories();
	CategoryExcelExporter exporter = new CategoryExcelExporter();
	exporter.export(listCategories, response);
    }
    @GetMapping("/categories/export/pdf")
    public void exportToPdf(HttpServletResponse response) throws IOException {
	List<Category> listCategories = service.readAllCategories();
	CategoryPdfExporter exporter = new CategoryPdfExporter();
	exporter.export(listCategories, response);
    }
}
