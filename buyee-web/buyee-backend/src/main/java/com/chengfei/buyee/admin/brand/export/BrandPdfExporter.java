package com.chengfei.buyee.admin.brand.export;
import java.awt.Color;
import java.io.IOException;
import java.util.List;
import com.chengfei.buyee.admin.AbstractExporter;
import com.chengfei.buyee.common.entity.Brand;
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
public class BrandPdfExporter extends AbstractExporter {
    public void export(List<Brand> listBrands, HttpServletResponse response) throws DocumentException, IOException {
	super.setResponseHeader(response, "application/pdf", "brands_", ".pdf");
	Document document = new Document(PageSize.A4);
	PdfWriter.getInstance(document, response.getOutputStream());
	document.open();
	Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	font.setSize(18);
	Paragraph paragraph = new Paragraph("List of Brands", font);
	paragraph.setAlignment(Paragraph.ALIGN_CENTER);
	document.add(paragraph);
	PdfPTable table = new PdfPTable(3);
	table.setWidthPercentage(100f);
	table.setSpacingBefore(10);
	writeTableHeader(table);
	writeTableData(table, listBrands);
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
	cell.setPhrase(new Phrase("Categories", font));
	table.addCell(cell);
    }
    private void writeTableData(PdfPTable table, List<Brand> listBrands) {
	for (Brand brand : listBrands) {
	    PdfPCell cell = new PdfPCell();
	    cell.setPadding(5);
	    Font font = FontFactory.getFont(FontFactory.HELVETICA);
	    cell.setPhrase(new Phrase(String.valueOf(brand.getId()), font));
	    table.addCell(cell);
	    cell.setPhrase(new Phrase(brand.getName(), font));
	    table.addCell(cell);
	    cell.setPhrase(new Phrase(brand.getCategories().toString(), font));
	    table.addCell(cell);

	}
    }
}
