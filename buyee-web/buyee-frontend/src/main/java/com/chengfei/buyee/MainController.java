package com.chengfei.buyee;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.chengfei.buyee.category.CategoryService;
import com.chengfei.buyee.common.entity.Category;
@Controller
public class MainController {
    @Autowired private CategoryService service;
    // Read Tasks
    @GetMapping("/")
    public String viewHomePage(Model model) {
	List<Category> listNoChildrenCategories = service.readNoChildrenCategories();
	model.addAttribute("listNoChildrenCategories", listNoChildrenCategories);
	return "webpages/index";
    }
}
