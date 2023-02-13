package com.chengfei.buyee.admin;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class MainController {
    @GetMapping("/")
    public String viewHomePage() {
	return "/webpages/index";
    }
    @GetMapping("/login")
    public String viewLoginPage() {
	return "/webpages/login";
    }
}
