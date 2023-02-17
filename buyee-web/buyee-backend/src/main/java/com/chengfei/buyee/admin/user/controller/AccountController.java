package com.chengfei.buyee.admin.user.controller;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.chengfei.buyee.admin.FileUploadUtil;
import com.chengfei.buyee.admin.security.BuyeeUserDetails;
import com.chengfei.buyee.admin.user.UserService;
import com.chengfei.buyee.common.entity.User;
import com.chengfei.buyee.common.exception.UserNotFoundException;
@Controller
public class AccountController {
    @Autowired private UserService service;
    // Read Tasks
    @GetMapping("/account")
    public String readAccountDetails(@AuthenticationPrincipal BuyeeUserDetails loggedUser, Model model) {
	String email = loggedUser.getUsername();
	try {
	    User user = service.readUserByEmail(email);
	    model.addAttribute("user", user);
	} catch (UserNotFoundException e) {
	    e.printStackTrace();
	    System.out.println(e.getMessage());
	}
	return "/webpages/users/account_form";
    }
    // Update Tasks
    @PostMapping("/account/update")
    public String updateAccountDetails(
	    User user, RedirectAttributes redirectAttributes, 
	    @AuthenticationPrincipal BuyeeUserDetails loggedUser, 
	    @RequestParam("image") MultipartFile multipartFile) throws IOException {
	if (!multipartFile.isEmpty()) {
	    String fileName = user.getFirstName().toLowerCase() + "_" + user.getLastName().toLowerCase() + ".png";
	    user.setPhoto(fileName);
	    User savedUser = service.updateAccount(user);
	    String folderName = "user-photos/" + savedUser.getId();
	    FileUploadUtil.cleanFolder(folderName);
	    FileUploadUtil.saveFile(folderName, fileName, multipartFile);
	} else {
	    if (user.getPhoto().isEmpty())
		user.setPhoto(null);
	    service.updateAccount(user);
	}
	loggedUser.setFirstName(user.getFirstName());
	loggedUser.setLastName(user.getLastName());
	redirectAttributes.addFlashAttribute("message", "Account updated successfully!");
	return "redirect:/";
    }
}
