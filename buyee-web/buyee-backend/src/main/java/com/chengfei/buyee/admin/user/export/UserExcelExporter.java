package com.chengfei.buyee.admin.user.export;

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
import com.chengfei.buyee.common.entity.User;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

public class UserExcelExporter extends AbstractExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public UserExcelExporter() {
	workbook = new XSSFWorkbook();
    }

    public void export(List<User> listUsers, HttpServletResponse response) throws IOException {
	super.setResponseHeader(response, "application/octet-stream ", "users_", ".xlsx");
	writeHeaderLine();
	writeDataLines(listUsers);
	ServletOutputStream outputStream = response.getOutputStream();
	workbook.write(outputStream);
	workbook.close();
	outputStream.close();
    }

    private void writeHeaderLine() {
	sheet = workbook.createSheet("Users");
	XSSFRow row = sheet.createRow(0);
	XSSFCellStyle cellStyle = workbook.createCellStyle();
	XSSFFont font = workbook.createFont();
	font.setBold(true);
	font.setFontHeight(16);
	cellStyle.setFont(font);
	createCell(row, 0, "ID", cellStyle);
	createCell(row, 1, "Email", cellStyle);
	createCell(row, 2, "First Name", cellStyle);
	createCell(row, 3, "Last Name", cellStyle);
	createCell(row, 4, "Roles", cellStyle);
	createCell(row, 5, "Enabled", cellStyle);
    }

    private void writeDataLines(List<User> listUsers) {
	int rowIndex = 1;
	for (User user : listUsers) {
	    XSSFRow row = sheet.createRow(rowIndex++);
	    XSSFCellStyle cellStyle = workbook.createCellStyle();
	    XSSFFont font = workbook.createFont();
	    font.setBold(false);
	    font.setFontHeight(12);
	    cellStyle.setFont(font);
	    createCell(row, 0, user.getId(), cellStyle);
	    createCell(row, 1, user.getEmail(), cellStyle);
	    createCell(row, 2, user.getFirstName(), cellStyle);
	    createCell(row, 3, user.getLastName(), cellStyle);
	    createCell(row, 4, user.getRoles().toString(), cellStyle);
	    createCell(row, 5, user.isEnabled(), cellStyle);
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
