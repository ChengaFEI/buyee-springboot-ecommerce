package com.chengfei.buyee.admin.setting;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.chengfei.buyee.common.entity.Setting;
import com.chengfei.buyee.common.entity.SettingCategory;
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class SettingRepositoryTests {
    @Autowired private SettingRepository repo;
    @Test
    public void testCreateGeneralSettings() {
	Setting siteName = new Setting("SITE_NAME", "Buyee", SettingCategory.GENERAL);
	Setting siteLogo = new Setting("SITE_LOGO", "buyee.png", SettingCategory.GENERAL);
	Setting copyright = new Setting("COPYRIGHT", "Copyright (C) 2022 Buyee Ltd.", SettingCategory.GENERAL);
	repo.save(siteName);
	repo.save(siteLogo);
	repo.save(copyright);
    }
    @Test
    public void testCreateCurrencySettings() {
	Setting currencyId = new Setting("CURRENCY_ID", "1", SettingCategory.CURRENCY);
	Setting currencySymbol= new Setting("CURRENCY_SYMBOL", "$", SettingCategory.CURRENCY);
	Setting currencySymbolPosition = new Setting("CURRENCY_SYMBOL_POSITION", "before", SettingCategory.CURRENCY);
	Setting decimalPointType = new Setting("DECIMAL_POINT_TYPE", "POINT", SettingCategory.CURRENCY);
	Setting decimalDigits = new Setting("DECIMAL_DIGITS", "2", SettingCategory.CURRENCY);
	Setting thousandsPointType = new Setting("THOUSANDS_POINT_TYPE", "COMMA", SettingCategory.CURRENCY);
	repo.save(currencyId);
	repo.save(currencySymbol);
	repo.save(currencySymbolPosition);
	repo.save(decimalPointType);
	repo.save(decimalDigits);
	repo.save(thousandsPointType);
    }
}
