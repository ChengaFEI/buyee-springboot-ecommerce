package com.chengfei.buyee.admin.setting.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.chengfei.buyee.admin.setting.CurrencyRepository;
import com.chengfei.buyee.admin.setting.SettingService;
import com.chengfei.buyee.common.entity.Currency;
import com.chengfei.buyee.common.entity.Setting;

@Controller
public class SettingController {
    @Autowired private SettingService service;
    @Autowired private CurrencyRepository currencyRepo;
    @GetMapping("/settings")
    public String readAllSettings(
	    Model model) {
	List<Setting> listSettings = service.readAllSettings();
	List<Currency> listCurrencies = currencyRepo.findAllByOrderByNameAsc();
	for (Setting setting: listSettings) {
	    model.addAttribute(setting.getKey(), setting.getValue());
	}
	model.addAttribute("listCurrencies", listCurrencies);
	model.addAttribute("pageTitle", "Settings");
	return "webpages/settings/settings";
    }
}
