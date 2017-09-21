package com.payment.views;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.web.servlet.view.AbstractView;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * 
 * This abstract class is to support pdf generation using itext library. Spring directly not
 * supporting Itext pdf view, so we are creating this abstract class.
 * 
 * (spring will support old version of itext which is deprecated may be)
 *
 */
public abstract class AbstractItextPdfView extends AbstractView {

  public AbstractItextPdfView() {
    setContentType(MediaType.APPLICATION_PDF_VALUE);
  }

  @Override
  protected boolean generatesDownloadContent() {
    return true;
  }

  @Override
  protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    // IE workaround: write into byte array first.
    ByteArrayOutputStream baos = createTemporaryOutputStream();

    // Apply preferences and build metadata.
    Document document = newDocument();
    PdfWriter writer = newWriter(document, baos);
    prepareWriter(writer);
    // Build PDF document.
    document.open();
    buildPdfDocument(model, document, writer, request, response);
    document.close();

    // Flush to HTTP response.
    writeToResponse(response, baos);

  }

  protected Document newDocument() {
    return new Document(PageSize.A4);
  }

  protected PdfWriter newWriter(Document document, OutputStream os) throws DocumentException {
    return PdfWriter.getInstance(document, os);
  }

  protected void prepareWriter(PdfWriter writer) {
    writer.setViewerPreferences(getViewerPreferences());
  }

  protected int getViewerPreferences() {
    return PdfWriter.ALLOW_PRINTING | PdfWriter.PageLayoutSinglePage;
  }

  protected abstract void buildPdfDocument(Map<String, Object> model, Document document,
      PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
      throws DocumentException;

}
