package com.chengfei.buyee.admin.category.export;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.chengfei.buyee.admin.AbstractExporter;
import com.chengfei.buyee.common.entity.Category;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

public class CategoryExcelExporter extends AbstractExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public CategoryExcelExporter() {
	workbook = new XSSFWorkbook();
    }

    public void export(List<Category> listCategories, HttpServletResponse response) throws IOException {
	super.setResponseHeader(response, "application/octet-stream ", "categories_", ".xlsx");
	writeHeaderLine();
	writeDataLines(listCategories);
	ServletOutputStream outputStream = response.getOutputStream();
	workbook.write(outputStream);
	workbook.close();
	outputStream.close();
    }

    private void writeHeaderLine() {
	sheet = workbook.createSheet("Categories");
	XSSFRow row = sheet.createRow(0);
	XSSFCellStyle cellStyle = workbook.createCellStyle();
	XSSFFont font = workbook.createFont();
	font.setBold(true);
	font.setFontHeight(16);
	cellStyle.setFont(font);
	createCell(row, 0, "ID", cellStyle);
	createCell(row, 1, "Name", cellStyle);
	createCell(row, 2, "Alias", cellStyle);
	createCell(row, 3, "Enabled", cellStyle);
    }

    private void writeDataLines(List<Category> listCategories) {
	int rowIndex = 1;
	for (Category category : listCategories) {
	    XSSFRow row = sheet.createRow(rowIndex++);
	    XSSFCellStyle cellStyle = workbook.createCellStyle();
	    XSSFFont font = workbook.createFont();
	    font.setBold(false);
	    font.setFontHeight(12);
	    cellStyle.setFont(font);
	    createCell(row, 0, category.getId(), cellStyle);
	    createCell(row, 1, category.getName(), cellStyle);
	    createCell(row, 2, category.getAlias().toString(), cellStyle);
	    createCell(row, 3, category.isEnabled(), cellStyle);
	}
    }

    private void createCell(XSSFRow row, int index, Object value, CellStyle cellstyle) {
	XSSFCell cell = row.createCell(index);
	sheet.autoSizeColumn(index);
	if (value instanceof Integer) {
	    cell.setCellValue((Integer) value);
	} else if (value instanceof Boolean) {
	    cell.setCellValue((Boolean) value);
	} else {
	    cell.setCellValue((String) value);
	}
	cell.setCellStyle(cellstyle);
    }
}
