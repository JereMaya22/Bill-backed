package com.gbill.createfinalconsumerbill.service;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gbill.createfinalconsumerbill.model.BillItem;
import com.gbill.createfinalconsumerbill.model.FinalConsumerBill;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class PdfInvoiceService {
    @Value("${invoices.output-dir}")
    private String invoicesOutputDir;
	public Path generateInvoicePdf(FinalConsumerBill bill) {
		try {
            String yyyyMM = bill.getBillGenerationDate().format(DateTimeFormatter.ofPattern("yyyyMM"));
            String safeGen = bill.getGenerationCode().replaceAll("[^a-zA-Z0-9_-]", "_");
            String fileName = yyyyMM + "-" + safeGen + ".pdf";
			Path dir = Paths.get(invoicesOutputDir);
			Files.createDirectories(dir);
			Path output = dir.resolve(fileName);

			try (FileOutputStream fos = new FileOutputStream(output.toFile())) {
				Document document = new Document();
				PdfWriter.getInstance(document, fos);
				document.open();

				Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
				Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
				Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 11);

				// Título
				Paragraph title = new Paragraph("Factura Consumidor Final", titleFont);
				title.setAlignment(Element.ALIGN_CENTER);
				document.add(title);

				document.add(new Paragraph(" "));
				
				// Información general
				PdfPTable infoTable = new PdfPTable(2);
				infoTable.setWidthPercentage(100);

				addCell(infoTable, "Número de control:", headerFont);
				addCell(infoTable, bill.getControlNumber(), normalFont);

				addCell(infoTable, "Código de generación:", headerFont);
				addCell(infoTable, bill.getGenerationCode(), normalFont);

				addCell(infoTable, "Fecha de emisión:", headerFont);
				addCell(infoTable, bill.getBillGenerationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), normalFont);

				document.add(infoTable);
				document.add(new Paragraph(" "));

				// Emisor
				Paragraph emisorH = new Paragraph("Emisor", headerFont);
				document.add(emisorH);
				PdfPTable emisor = new PdfPTable(1);
				emisor.setWidthPercentage(100);
				addCell(emisor, bill.getCompanyName(), normalFont);
				addCell(emisor, "NRC/NIT: " + nullSafe(bill.getCompanyDocument()), normalFont);
				addCell(emisor, "Dirección: " + nullSafe(bill.getCompanyAddress()), normalFont);
				addCell(emisor, "Email: " + nullSafe(bill.getCompanyEmail()) + "  Tel: " + nullSafe(bill.getCompanyPhone()), normalFont);
				document.add(emisor);

				document.add(new Paragraph(" "));

				// Receptor
				Paragraph receptorH = new Paragraph("Receptor", headerFont);
				document.add(receptorH);
				PdfPTable receptor = new PdfPTable(1);
				receptor.setWidthPercentage(100);
				addCell(receptor, bill.getCustomerName(), normalFont);
				addCell(receptor, "Documento: " + nullSafe(bill.getCustomerDocument()), normalFont);
				addCell(receptor, "Dirección: " + nullSafe(bill.getCustomerAddress()), normalFont);
				addCell(receptor, "Email: " + nullSafe(bill.getCustomerEmail()) + "  Tel: " + nullSafe(bill.getCustomerPhone()), normalFont);
				document.add(receptor);

				document.add(new Paragraph(" "));

				// Detalle de productos
				PdfPTable items = new PdfPTable(5);
				items.setWidthPercentage(100);
				items.setWidths(new float[]{4f, 1.3f, 1.5f, 1.5f, 1.7f});

				addHeaderCell(items, "Producto", headerFont);
				addHeaderCell(items, "Cant.", headerFont);
				addHeaderCell(items, "Precio", headerFont);
				addHeaderCell(items, "SubTotal", headerFont);
				addHeaderCell(items, "ID Prod.", headerFont);

				for (BillItem it : bill.getProducts()) {
					addCell(items, nullSafe(it.getName()), normalFont);
					addCell(items, String.valueOf(it.getRequestedQuantity()), normalFont);
					addCell(items, formatMoney(it.getPrice()), normalFont);
					addCell(items, formatMoney(it.getSubTotal()), normalFont);
					addCell(items, String.valueOf(it.getProductId()), normalFont);
				}
				document.add(items);

				document.add(new Paragraph(" "));

				// Totales
				PdfPTable totals = new PdfPTable(2);
				totals.setWidthPercentage(50);
				totals.setHorizontalAlignment(Element.ALIGN_RIGHT);

				addCell(totals, "Ventas no sujetas:", headerFont);
				addCellRight(totals, formatMoney(bill.getNonTaxedSales()), normalFont);

				addCell(totals, "Ventas exentas:", headerFont);
				addCellRight(totals, formatMoney(bill.getExemptSales()), normalFont);

				addCell(totals, "Ventas gravadas:", headerFont);
				addCellRight(totals, formatMoney(bill.getTaxedSales()), normalFont);

				addCell(totals, "IVA (13%):", headerFont);
				addCellRight(totals, formatMoney(bill.getPerceivedIva()), normalFont);

				addCell(totals, "Total a pagar:", headerFont);
				addCellRight(totals, formatMoney(bill.getTotalWithIva()), headerFont);

				document.add(totals);

				document.close();
			}

			return output;
		} catch (Exception e) {
			// No interrumpir el flujo de creación de la factura si el PDF falla
			return null;
		}
	}

	private static void addCell(PdfPTable table, String text, Font font) {
		PdfPCell cell = new PdfPCell(new Phrase(text == null ? "" : text, font));
		table.addCell(cell);
	}

	private static void addHeaderCell(PdfPTable table, String text, Font font) {
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);
	}

	private static void addCellRight(PdfPTable table, String text, Font font) {
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(cell);
	}

	private static String formatMoney(Double v) {
		if (v == null) return "$0.00";
		return String.format("$%.2f", v);
	}

	private static String nullSafe(String s) {
		return s == null ? "" : s;
	}
}