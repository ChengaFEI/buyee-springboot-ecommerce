package com.chengfei.buyee.admin.category.export;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import com.chengfei.buyee.admin.AbstractExporter;
import com.chengfei.buyee.common.entity.Category;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

public class CategoryPdfExporter extends AbstractExporter {
    public void export(List<Category> listCategories, HttpServletResponse response) throws DocumentException, IOException {
	super.setResponseHeader(response, "application/pdf", "categories_", ".pdf");
	Document document = new Document(PageSize.A4);
	PdfWriter.getInstance(document, response.getOutputStream());

	document.open();

	Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	font.setSize(18);
	Paragraph paragraph = new Paragraph("List of Categories", font);
	paragraph.setAlignment(Paragraph.ALIGN_CENTER);
	document.add(paragraph);

	PdfPTable table = new PdfPTable(4);
	table.setWidthPercentage(100f);
	table.setSpacingBefore(10);
	writeTableHeader(table);
	writeTableData(table, listCategories);
	document.add(table);

	document.close();
    }

    private void writeTableHeader(PdfPTable table) {
	PdfPCell cell = new PdfPCell();
	cell.setBackgroundColor(Color.BLACK);
	cell.setPadding(5);
	Font font = FontFactory.getFont(FontFactory.HELVETICA);
	font.setColor(Color.WHITE);
	cell.setPhrase(new Phrase("ID", font));
	table.addCell(cell);
	cell.setPhrase(new Phrase("Name", font));
	table.addCell(cell);
	cell.setPhrase(new Phrase("Alias", font));
	table.addCell(cell);
	cell.setPhrase(new Phrase("Enabled", font));
	table.addCell(cell);
    }

    private void writeTableData(PdfPTable table, List<Category> listCategories) {
	for (Category category : listCategories) {
	    PdfPCell cell = new PdfPCell();
	    cell.setPadding(5);
	    Font font = FontFactory.getFont(FontFactory.HELVETICA);
	    cell.setPhrase(new Phrase(String.valueOf(category.getId()), font));
	    table.addCell(cell);
	    cell.setPhrase(new Phrase(category.getName(), font));
	    table.addCell(cell);
	    cell.setPhrase(new Phrase(category.getAlias().toString(), font));
	    table.addCell(cell);
	    cell.setPhrase(new Phrase(String.valueOf(category.isEnabled()), font));
	    table.addCell(cell);

	}
    }
}
