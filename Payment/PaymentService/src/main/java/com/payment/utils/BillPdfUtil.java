package com.payment.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.Scanner;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;


public class BillPdfUtil {
	
	private static Font boldFont = PaymentFonts.boldFont;
	private static Font textFont = PaymentFonts.textFont;

	public static void main(String[] args) {
		System.out.println("creating PDF ");
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter qr code text: ");

		String text;
		// text = scanner.nextLine();
		text = "cutomer name: name of customer \n Amount: 200";
		createPdf(text);

		scanner.close();
	}

	private static void createPdf(String text) {
		String filePath = "e:/first.pdf";
		Document doc = new Document();
		PdfWriter docWriter = null;

		try {

			docWriter = PdfWriter.getInstance(doc, new FileOutputStream(filePath));
			doc.addAuthor("This is author");
			doc.addCreationDate();
			doc.addProducer();
			doc.setPageSize(PageSize.A4);

			// docWriter.setPageEvent(new PaymentHeaderAndFooter());

			doc.open();

			// add header table
			doc.add(getHeaderTable());

			// add line seperator
			doc.add(getLineSeperator(-10));

			doc.add(getCustomerTable(20));

			doc.add(getPurchaseDetailsTable(20));

			doc.add(getLineSeperator(-20));

			doc.add(getFooter(20));

			BarcodeQRCode barcodeQRCode = new BarcodeQRCode(PaymentConstantNames.shopName, 120, 120, null);
			Image barCodeImage = barcodeQRCode.getImage();
			barCodeImage.setAbsolutePosition(450, 620);
			// barCodeImage.setAlignment();

			doc.add(barCodeImage);
			doc.addHeader("Header", "header content");
			doc.addSubject("document subject");

			// Add a rectangle under the image
			/*
			 * PdfContentByte under = docWriter.getDirectContentUnder();
			 * under.saveState(); under.setRGBColorFill(0xFF, 0xD7, 0x00);
			 * 
			 * under.rectangle(5, 5, PageSize.POSTCARD.getWidth() - 10,
			 * PageSize.POSTCARD.getHeight() - 10); under.fill();
			 * under.restoreState();
			 */

			doc.close();
			// setHeader(docWriter);

		} catch (Exception e) {
			e.printStackTrace();

		}
		if (doc != null) {
			doc.close();
			System.out.println("closed  document");
		}
		if (docWriter != null) {
			docWriter.close();
			System.out.println("closed  docWriter");
		}

		System.out.println("PDF genereation completed");
	}

	private static PdfPTable getHeaderTable() throws DocumentException {

		final int imgeHeight = 50;

		try {
			// image path:
			String imageFilePathRight = "C:/Users/sai/Desktop/black and white imagess/lakshmi.jpg";     //"C:/Users/Public/Pictures/Sample Pictures/Tulips.jpg";
			String imageFilePathLeft = "C:/Users/sai/Desktop/black and white imagess/ganapathi3.jpg";     //ganapathi1.png";
			Image imageLeft = Image.getInstance(imageFilePathLeft);
			imageLeft.setWidthPercentage(20);
			imageLeft.setScaleToFitHeight(true);
			
			Image imageRight = Image.getInstance(imageFilePathRight);
			imageRight.setWidthPercentage(20);
			imageRight.setScaleToFitHeight(true);

			float [] tableWidths = new float[] {10f,55f,10f};
			PdfPTable table = new PdfPTable(3);
			table.setWidths(tableWidths);
			table.setWidthPercentage(100);

			PdfPCell pdfCell = new PdfPCell(imageLeft, true);
			pdfCell.setBorder(Rectangle.NO_BORDER);
			pdfCell.setFixedHeight(imgeHeight);
			pdfCell.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPTable innerTable = new PdfPTable(1);
			innerTable.setWidthPercentage(100);
			Paragraph heading = new Paragraph(PaymentConstantNames.shopName, PaymentFonts.headingFont);

			PdfPCell textCell = new PdfPCell();
			textCell.setColspan(2);
			textCell.setPhrase(heading);
			textCell.setBorder(Rectangle.NO_BORDER);
			textCell.setHorizontalAlignment(Element.ALIGN_CENTER);

			// add name in center
			innerTable.addCell(textCell);
			
			PdfPCell addressCell = new PdfPCell();
			addressCell.setPaddingTop(10f);
			addressCell.setBorder(Rectangle.NO_BORDER);
			addressCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			addressCell.setPhrase(new Phrase(PaymentConstantNames.shopAddress,PaymentFonts.footerFont));
			innerTable.addCell(addressCell);

			PdfPCell imageCellRight = new PdfPCell(imageRight, true);
			imageCellRight.setBorder(Rectangle.NO_BORDER);
			imageCellRight.setFixedHeight(imgeHeight);
			imageCellRight.setHorizontalAlignment(Element.ALIGN_RIGHT);

			// add left side image
			table.addCell(pdfCell);
			PdfPCell tebleCell= new PdfPCell(innerTable);
			tebleCell.setBorder(Rectangle.NO_BORDER);
			
			table.addCell(tebleCell);

			// add right side image
			table.addCell(imageCellRight);

			return table;
		} catch (BadElementException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}

	private static PdfPTable getCustomerTable(int spaceTop) throws DocumentException {

		PdfPTable customerTable = new PdfPTable(2);

		customerTable.setSpacingBefore(spaceTop);

		float[] columnWidths = new float[] { 6f, 20f };
		customerTable.setWidths(columnWidths);
		customerTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

		Font boldFont = new Font(FontFamily.TIMES_ROMAN, 14, Font.BOLDITALIC);
		Font textFont = new Font(FontFamily.COURIER, 14);

		// customer name
		customerTable.addCell(new Phrase("Name :", boldFont));

		customerTable.addCell(new Phrase("Name of cutomer", textFont));

		customerTable.addCell(new Phrase("Phone :", boldFont));

		customerTable.addCell(new Phrase("857348534584", textFont));

		customerTable.addCell(new Phrase("Address :", boldFont));

		customerTable.addCell(new Phrase("10tm ward\npoolapalli\npalakol mandal", textFont));

		customerTable.addCell(new Phrase("Date :", boldFont));

		customerTable.addCell(new Phrase("11-Dec-2016", textFont));

		return customerTable;
	}

	private static PdfPTable getPurchaseDetailsTable(int spaceTop) throws DocumentException {

		PdfPTable detailsTable = new PdfPTable(5);

		detailsTable.setSpacingBefore(spaceTop);

		float[] columnWidths = new float[] { 6f, 20f };
		// detailsTable.setWidths(columnWidths);
		detailsTable.getDefaultCell().setBorder(Rectangle.BOX);
		detailsTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

		// customer name
		detailsTable.addCell(new Phrase("Item Name", boldFont));
		detailsTable.addCell(new Phrase("Company", boldFont));
		detailsTable.addCell(new Phrase("Price", boldFont));
		detailsTable.addCell(new Phrase("Quantity", boldFont));
		detailsTable.addCell(new Phrase("Amount", boldFont));

		for (int i = 0; i < 20; i++) {

			detailsTable.addCell(new Phrase("pendrive", textFont));
			detailsTable.addCell(new Phrase("Sony", textFont));
			detailsTable.addCell(new Phrase("300", textFont));
			detailsTable.addCell(new Phrase("1", textFont));
			detailsTable.addCell(new Phrase("400", textFont));

		}
		
		PdfPCell totalAmount = new PdfPCell();
		totalAmount.setColspan(4);
		totalAmount.setHorizontalAlignment(Element.ALIGN_CENTER);
		totalAmount.setPhrase((new Phrase("Total Amount", boldFont)));
		
		detailsTable.addCell(totalAmount);
		detailsTable.addCell(new Phrase("1000", boldFont));

		return detailsTable;
	}

	private static PdfPTable getFooter(int spaceTop) {

		PdfPTable footerTable = new PdfPTable(2);

		footerTable.setSpacingBefore(spaceTop);
		footerTable.setWidthPercentage(100);

		PdfPCell cell = new PdfPCell(new Phrase(new Date().toString(), PaymentFonts.footerFont));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.NO_BORDER);
		footerTable.addCell(cell);

		PdfPCell cellTwo = new PdfPCell(new Phrase("Thank You", PaymentFonts.footerFont));
		cellTwo.setBorder(Rectangle.NO_BORDER);
		cellTwo.setHorizontalAlignment(Element.ALIGN_RIGHT);
		footerTable.addCell(cellTwo);

		return footerTable;

	}

	private static Chunk getLineSeperator(int offSet) {
		LineSeparator lineSeparator = new LineSeparator();
		lineSeparator.setAlignment(Element.ALIGN_BOTTOM);
		lineSeparator.setOffset(offSet);
		return new Chunk(lineSeparator);
	}


}
