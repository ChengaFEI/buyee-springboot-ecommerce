package com.chengfei.buyee.admin.user.controller;

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
import com.chengfei.buyee.admin.user.UserNotFoundException;
import com.chengfei.buyee.admin.user.UserService;
import com.chengfei.buyee.admin.user.export.UserCsvExporter;
import com.chengfei.buyee.admin.user.export.UserExcelExporter;
import com.chengfei.buyee.admin.user.export.UserPdfExporter;
import com.chengfei.buyee.common.entity.Role;
import com.chengfei.buyee.common.entity.User;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UserController {
    @Autowired
    private UserService service;

    // Create Tasks

    @GetMapping("/users/new")
    public String createUser(Model model) {
	List<Role> listRoles = service.readAllRoles();
	User user = new User();
	user.setEnabled(true);
	model.addAttribute("user", user);
	model.addAttribute("listRoles", listRoles);
	model.addAttribute("pageTitle", "Create User");
	return "users/users_form";
    }

    @PostMapping("/users/save")
    public String submitUser(User user, RedirectAttributes redirectAttributes,
	    @RequestParam("image") MultipartFile multipartFile) throws IOException {
	if (!multipartFile.isEmpty()) {
	    String fileName = user.getFirstName().toLowerCase() + "_" + user.getLastName().toLowerCase() + ".png";
	    user.setPhoto(fileName);
	    User savedUser = service.saveUser(user);
	    String folderName = "user-photos/" + savedUser.getId();
	    AmazonS3Util.deleteFolder(folderName + "/");
	    AmazonS3Util.saveFile(folderName, fileName, multipartFile.getInputStream());
	} else {
	    if (user.getPhoto().isEmpty())
		user.setPhoto(null);
	    service.saveUser(user);
	}

	redirectAttributes.addFlashAttribute("message", "User saved successfully!");
	String email = user.getEmail();
	return "redirect:/users/page/1?keyword=" + email;
    }

    // Read Tasks
    
    @GetMapping("/users")
    public String readUsersInFirstPage(Model model) {
	return readUsersByPageNum(1, null, null, null, model);
    }

    @GetMapping("/users/page/{pageNum}")
    public String readUsersByPageNum(@PathVariable(name = "pageNum") int pageNum, @Param("sortField") String sortField,
	    @Param("sortOrder") String sortOrder, @Param("keyword") String keyword, Model model) {
	Page<User> page = service.readUsersByPageNum(pageNum, sortField, sortOrder, keyword);
	List<User> listUsers = page.getContent();
	long totalElements = page.getTotalElements();
	long startCount = (pageNum - 1) * UserService.USERS_PER_PAGE + 1;
	long endCount = startCount + UserService.USERS_PER_PAGE - 1;
	endCount = Math.min(totalElements, endCount);

	model.addAttribute("pageNum", pageNum);
	model.addAttribute("listUsers", listUsers);
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

	return "users/users";
    }

    // Update Tasks

    @GetMapping("/users/edit/{id}")
    public String updateUserById(@PathVariable(name = "id") Integer id, Model model,
	    RedirectAttributes redirectAttributes) {
	try {
	    User user = service.readUserById(id);
	    List<Role> listRoles = service.readAllRoles();
	    model.addAttribute("user", user);
	    model.addAttribute("listRoles", listRoles);
	    model.addAttribute("pageTitle", "Update User (ID: " + id + ")");
	    return "users/users_form";
	} catch (UserNotFoundException e) {
	    redirectAttributes.addFlashAttribute("message", e.getMessage());
	    return "redirect:/users";
	}
    }

    @GetMapping("/users/{id}/enabled/{status}")
    public String updateUserEnabledStatus(@PathVariable(name = "id") Integer id,
	    @PathVariable(name = "status") boolean status, @Param("pageNum") int pageNum,
	    @Param("keyword") String keyword, @Param("sortField") String sortField, 
	    @Param("sortOrder") String sortOrder, RedirectAttributes redirectAttributes) {
	service.updateUserEnabledStatus(id, status);
	String enabledStr = status ? "enable" : "disable";
	redirectAttributes.addFlashAttribute("message", "Successfully " + enabledStr + " user with ID " + id + ".");
	String redirectURL = "redirect:/users/page/" + pageNum + "?";
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

    @GetMapping("/users/delete/{id}")
    public String deleteUserById(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
	try {
	    service.deleteUserById(id);
	    AmazonS3Util.deleteFolder("user-photos/" + id + "/");
	    redirectAttributes.addFlashAttribute("message", "Successfully delete user with ID " + id + "!");
	} catch (UserNotFoundException e) {
	    redirectAttributes.addFlashAttribute("message", e.getMessage());
	}
	return "redirect:/users";
    }

    // Export Tasks

    @GetMapping("/users/export/csv")
    public void exportToCsv(HttpServletResponse response) throws IOException {
	List<User> listUsers = service.readAllUsers();
	UserCsvExporter exporter = new UserCsvExporter();
	exporter.export(listUsers, response);
    }

    @GetMapping("/users/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
	List<User> listUsers = service.readAllUsers();
	UserExcelExporter exporter = new UserExcelExporter();
	exporter.export(listUsers, response);
    }

    @GetMapping("/users/export/pdf")
    public void exportToPdf(HttpServletResponse response) throws IOException {
	List<User> listUsers = service.readAllUsers();
	UserPdfExporter exporter = new UserPdfExporter();
	exporter.export(listUsers, response);
    }
}
