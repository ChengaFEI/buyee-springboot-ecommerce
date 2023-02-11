package com.chengfei.buyee.admin.brand.export;

import java.io.IOException;
import java.util.List;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.chengfei.buyee.admin.AbstractExporter;
import com.chengfei.buyee.common.entity.Brand;

import jakarta.servlet.http.HttpServletResponse;

public class BrandCsvExporter extends AbstractExporter {
    public void export(List<Brand> listBrands, HttpServletResponse response) throws IOException {
	super.setResponseHeader(response, "text/csv", "brands_", ".csv");
	ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
	String[] csvHeader = { "ID", "Name", "Categories" };
	String[] fieldMapping = { "id", "name", "categories" };
	csvWriter.writeHeader(csvHeader);
	for (Brand brand : listBrands) csvWriter.write(brand, fieldMapping); 
	csvWriter.close();
    }
}
