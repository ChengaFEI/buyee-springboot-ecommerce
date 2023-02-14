package com.chengfei.buyee.admin.user.export;
import java.awt.Color;
import java.io.IOException;
import java.util.List;
import com.chengfei.buyee.admin.AbstractExporter;
import com.chengfei.buyee.common.entity.User;
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
public class UserPdfExporter extends AbstractExporter {
    public void export(List<User> listUsers, HttpServletResponse response) throws DocumentException, IOException {
	super.setResponseHeader(response, "application/pdf", "users_", ".pdf");
	Document document = new Document(PageSize.A4);
	PdfWriter.getInstance(document, response.getOutputStream());
	document.open();
	Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	font.setSize(18);
	Paragraph paragraph = new Paragraph("List of Users", font);
	paragraph.setAlignment(Paragraph.ALIGN_CENTER);
	document.add(paragraph);
	PdfPTable table = new PdfPTable(6);
	table.setWidthPercentage(100f);
	table.setSpacingBefore(10);
	writeTableHeader(table);
	writeTableData(table, listUsers);
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
	cell.setPhrase(new Phrase("Email", font));
	table.addCell(cell);
	cell.setPhrase(new Phrase("First Name", font));
	table.addCell(cell);
	cell.setPhrase(new Phrase("Last Name", font));
	table.addCell(cell);
	cell.setPhrase(new Phrase("Roles", font));
	table.addCell(cell);
	cell.setPhrase(new Phrase("Enabled", font));
	table.addCell(cell);
    }
    private void writeTableData(PdfPTable table, List<User> listUsers) {
	for (User user : listUsers) {
	    PdfPCell cell = new PdfPCell();
	    cell.setPadding(5);
	    Font font = FontFactory.getFont(FontFactory.HELVETICA);
	    cell.setPhrase(new Phrase(String.valueOf(user.getId()), font));
	    table.addCell(cell);
	    cell.setPhrase(new Phrase(user.getEmail(), font));
	    table.addCell(cell);
	    cell.setPhrase(new Phrase(user.getFirstName(), font));
	    table.addCell(cell);
	    cell.setPhrase(new Phrase(user.getLastName(), font));
	    table.addCell(cell);
	    cell.setPhrase(new Phrase(user.getRoles().toString(), font));
	    table.addCell(cell);
	    cell.setPhrase(new Phrase(String.valueOf(user.isEnabled()), font));
	    table.addCell(cell);
	}
    }
}
