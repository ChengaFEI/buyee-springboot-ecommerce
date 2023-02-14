package com.chengfei.buyee.admin.category.export;
import java.io.IOException;
import java.util.List;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;
import com.chengfei.buyee.admin.AbstractExporter;
import com.chengfei.buyee.common.entity.Category;
import jakarta.servlet.http.HttpServletResponse;
public class CategoryCsvExporter extends AbstractExporter {
    public void export(List<Category> listCategories, HttpServletResponse response) throws IOException {
	super.setResponseHeader(response, "text/csv", "categories_", ".csv");
	ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
	String[] csvHeader = { "ID", "Name", "Alias", "Enabled" };
	String[] fieldMapping = { "id", "Name", "Alias", "Enabled" };
	csvWriter.writeHeader(csvHeader);
	for (Category category : listCategories) {
	    category.setName(category.getName().replace("····", "  "));
	    csvWriter.write(category, fieldMapping); 
	}
	csvWriter.close();
    }
}
