
package com.payment.views;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.payment.domain.Bill;
import com.payment.domain.Customer;
import com.payment.domain.SoldItem;
import com.payment.utils.PaymentConstantNames;
import com.payment.utils.PaymentFonts;

public class BillPdfView extends AbstractItextPdfView {

  private static Font boldFont = PaymentFonts.boldFont;
  private static Font textFont = PaymentFonts.textFont;
  private static Font footerFont = PaymentFonts.footerFont;
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
  private static final SimpleDateFormat footerDateFormat = new SimpleDateFormat(
      "dd/MMM/yyyy EEEEEE h:mm a");

  private Date billDate = null;
  private String customerName = null;
  private String customerPhone = null;

  private static final Logger log = LoggerFactory.getLogger(BillPdfView.class);

  @Override
  protected void buildPdfDocument(Map<String, Object> model, Document doc, PdfWriter writer,
      HttpServletRequest request, HttpServletResponse response) throws Exception {

    Bill bill = (Bill) model.get("bill");
    billDate = bill.getGeneratedDate();
    doc.addAuthor(PaymentConstantNames.shopName);
    doc.addCreationDate();
    doc.addProducer();

    // set downloadable file name
    response.setHeader("Content-Disposition",
        "filename=" + getDownloadableFileName(bill.getBillId(), bill.getCustomer().getName()));

    // Add header table (office details)
    doc.add(getHeaderTable());

    // Add line separator
    doc.add(getLineSeparator(-10));

    // Add customer details
    doc.add(getCustomerTable(25, bill.getCustomer(), dateFormat.format(billDate)));

    // Add sold items
    doc.add(getPurchaseDetailsTable(25, bill.getSoldItems(), bill.getNetAmount()));

    // Add line separator
    doc.add(getLineSeparator(-26));

    // Add Footer table
    doc.add(getFooter(15));

    // Add notes to customer
    doc.add(getNotes(0));

    Image barCodeImage = getBarcodeImage(bill.getBillId(), bill.getNetAmount());
    barCodeImage.setAbsolutePosition(450, 625);
    // Add QR code image
    doc.add(barCodeImage);

    doc.addSubject("Bill receipt");

  }

  private Image getBarcodeImage(long billId, long totalAmount) {
    StringBuilder stringBuilder = new StringBuilder(PaymentConstantNames.shopName);
    stringBuilder.append("\n Customer Name:");
    stringBuilder.append(customerName);
    stringBuilder.append("\n Customer Phone:");
    stringBuilder.append(customerPhone);
    stringBuilder.append("\n Bill ID:");
    stringBuilder.append(billId);
    stringBuilder.append("\n Total Amount:");
    stringBuilder.append(totalAmount);
    stringBuilder.append("\n Date:");
    stringBuilder.append(billDate);
    BarcodeQRCode barcodeQRCode = new BarcodeQRCode(stringBuilder.toString(), 120, 120, null);
    try {
      return barcodeQRCode.getImage();
    } catch (BadElementException badElementException) {
      log.error("exception while creating QR code image,{}", badElementException);
    }
    return null;
  }

  private PdfPTable getHeaderTable() throws DocumentException {

    final int imgeHeight = 50;

    try {

      String folderPath = getFolderLocation();
      // image path:
      String imageFilePathRight = folderPath + "resources/images/lakshmi.jpg";
      // Pictures/Tulips.jpg";
      String imageFilePathLeft = folderPath + "resources/images/ganapathi.jpg";
      Image imageLeft = Image.getInstance(imageFilePathLeft);
      imageLeft.setWidthPercentage(20);
      imageLeft.setScaleToFitHeight(true);

      Image imageRight = Image.getInstance(imageFilePathRight);
      imageRight.setWidthPercentage(20);
      imageRight.setScaleToFitHeight(true);

      float[] tableWidths = new float[] { 10f, 55f, 10f };
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
      addressCell.setPhrase(new Phrase(PaymentConstantNames.shopAddress, footerFont));
      innerTable.addCell(addressCell);

      PdfPCell imageCellRight = new PdfPCell(imageRight, true);
      imageCellRight.setBorder(Rectangle.NO_BORDER);
      imageCellRight.setFixedHeight(imgeHeight);
      imageCellRight.setHorizontalAlignment(Element.ALIGN_RIGHT);

      // add left side image
      table.addCell(pdfCell);
      PdfPCell tebleCell = new PdfPCell(innerTable);
      tebleCell.setBorder(Rectangle.NO_BORDER);

      table.addCell(tebleCell);

      // add right side image
      table.addCell(imageCellRight);

      return table;
    } catch (BadElementException badElementException) {
      log.error("BadElementException while creating header table, {}", badElementException);
    } catch (MalformedURLException malformedURLException) {
      log.error("MalformedURLException while creating header table, {}", malformedURLException);
    } catch (IOException ioException) {
      log.error("IOException while creating header table, {}", ioException);
    }

    return null;

  }

  private static Chunk getLineSeparator(int offSet) {
    LineSeparator lineSeparator = new LineSeparator();
    lineSeparator.setAlignment(Element.ALIGN_BOTTOM);
    lineSeparator.setOffset(offSet);
    return new Chunk(lineSeparator);
  }

  private PdfPTable getCustomerTable(int spaceTop, Customer customer, String generatedDate)
      throws DocumentException {

    customerName = customer.getName();
    customerPhone = customer.getPhone();

    PdfPTable customerTable = new PdfPTable(2);

    customerTable.setSpacingBefore(spaceTop);

    float[] columnWidths = new float[] { 6f, 20f };
    customerTable.setWidths(columnWidths);
    // customerTable.setWidthPercentage(80);
    customerTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

    // customer name
    customerTable.addCell(new Phrase("Name :", boldFont));
    customerTable.addCell(new Phrase(customerName, textFont));

    // customer Phone
    customerTable.addCell(new Phrase("Phone :", boldFont));
    customerTable.addCell(new Phrase(customerPhone, textFont));

    // customer Address
    customerTable.addCell(new Phrase("Address :", boldFont));
    customerTable.addCell(new Phrase(customer.getAddress(), textFont));

    // bill date
    customerTable.addCell(new Phrase("Date :", boldFont));
    customerTable.addCell(new Phrase(generatedDate, textFont));

    return customerTable;
  }

  private PdfPTable getPurchaseDetailsTable(int spaceTop, List<SoldItem> soldItems, long netAmount)
      throws DocumentException {

    float amount;
    int numberOfCols = 5;
    float[] columnWidths = new float[] { 20f, 18f, 12f, 13f, 15f };
    // verify capacity need to mention in PDf
    for (SoldItem soldItem : soldItems) {
      if (soldItem.getItemPriceDetails().getCapacity() != null) {
        numberOfCols = 6;
        columnWidths = new float[] { 20f, 18f, 12f, 12f, 13f, 15f };
      }
    }

    PdfPTable detailsTable = new PdfPTable(numberOfCols);

    detailsTable.setSpacingBefore(spaceTop);

    detailsTable.setWidths(columnWidths);
    detailsTable.getDefaultCell().setBorder(Rectangle.BOX);
    detailsTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

    detailsTable.addCell(new Phrase("Item Name", boldFont));
    detailsTable.addCell(new Phrase("Company", boldFont));
    if (numberOfCols == 6) {
      detailsTable.addCell(new Phrase("Capacity", boldFont));
    }
    detailsTable.addCell(new Phrase("Price", boldFont));
    detailsTable.addCell(new Phrase("Quantity", boldFont));
    detailsTable.addCell(new Phrase("Amount", boldFont));

    for (SoldItem soldItem : soldItems) {

      detailsTable.addCell(new Phrase(
          soldItem.getItemPriceDetails().getItemDetails().getItem().getItemName(), textFont)); // itemName

      detailsTable.addCell(new Phrase(
          soldItem.getItemPriceDetails().getItemDetails().getItemCompany().getCompanyName(),
          textFont)); // company

      if (numberOfCols == 6) {
        detailsTable.addCell(new Phrase((soldItem.getItemPriceDetails().getCapacity() != null)
            ? soldItem.getItemPriceDetails().getCapacity() : "-", textFont)); // Capacity

      }

      detailsTable.addCell(new Phrase(String.valueOf(soldItem.getSoldPrice()), textFont)); // price
      detailsTable.addCell(new Phrase(String.valueOf(soldItem.getQuantity()), textFont)); // quantity
      amount = soldItem.getSoldPrice() * soldItem.getQuantity();
      detailsTable.addCell(new Phrase(String.valueOf(amount), textFont)); // amount

    }

    PdfPCell totalAmount = new PdfPCell();
    totalAmount.setColspan(numberOfCols - 1);
    totalAmount.setHorizontalAlignment(Element.ALIGN_CENTER);
    totalAmount.setPhrase((new Phrase("Total Amount", boldFont)));

    detailsTable.addCell(totalAmount);
    detailsTable.addCell(new Phrase(String.valueOf(netAmount), boldFont)); // total amount

    return detailsTable;
  }

  private PdfPTable getFooter(int spaceBefore) {

    PdfPTable footerTable = new PdfPTable(2);

    footerTable.setSpacingBefore(spaceBefore);
    footerTable.setWidthPercentage(100);

    PdfPCell cell = new PdfPCell(new Phrase(footerDateFormat.format(billDate), footerFont));
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    cell.setBorder(Rectangle.NO_BORDER);
    footerTable.addCell(cell);

    PdfPCell cellTwo = new PdfPCell(new Phrase("Thank You", footerFont));
    cellTwo.setBorder(Rectangle.NO_BORDER);
    cellTwo.setHorizontalAlignment(Element.ALIGN_RIGHT);
    footerTable.addCell(cellTwo);

    return footerTable;

  }

  private static Paragraph getNotes(float spacinngBefore) {
    Paragraph paragraph = new Paragraph();
    paragraph.setAlignment(Rectangle.ALIGN_CENTER);
    paragraph.setFont(footerFont);
    paragraph.add(PaymentConstantNames.note);
    paragraph.setSpacingBefore(spacinngBefore);
    return paragraph;
  }

  private String getFolderLocation() throws UnsupportedEncodingException {
    return URLDecoder.decode(
        this.getClass().getClassLoader().getResource("").getPath().split("WEB-INF/classes/")[0],
        "UTF-8");
  }

  private String getDownloadableFileName(long billId, String customerName) {
    return "LGC_bill_" + customerName + "_" + billId + ".pdf";
  }
}
