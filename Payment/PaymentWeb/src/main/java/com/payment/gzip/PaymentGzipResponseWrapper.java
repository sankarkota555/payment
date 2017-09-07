package com.payment.gzip;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaymentGzipResponseWrapper extends HttpServletResponseWrapper {

  private static final Logger log = LoggerFactory.getLogger(PaymentGzipResponseWrapper.class);

  private Set<String> mimetypes;

  private OutputStream outputStream;

  private boolean compressResponse = false;

  private static final String CONTENT_ENCODING = "Content-Encoding";

  private static final String GZIP = "gzip";

  /**
   * <b>No Gzip</b> compression will be applied.
   * 
   * @param response
   *          {@link HttpServletResponse}
   */
  public PaymentGzipResponseWrapper(HttpServletResponse response) {
    super(response);
  }

  /**
   * Gzip compression will be applied when compressResponse is true.
   * 
   * @param response
   *          {@link HttpServletResponse}
   * @param compressResponse
   *          <code>true</code> if Gzip compression is required. <br>
   *          <code>false</code> if Gzip compression not required.
   */
  public PaymentGzipResponseWrapper(HttpServletResponse response, boolean compressResponse) {
    super(response);
    this.compressResponse = compressResponse;
  }

  /**
   * If response contains given mimetypes then only Gzip compression will be applied otherwise Gzip
   * is not applied.
   * 
   * @param response
   *          {@link HttpServletResponse}
   * @param mimetypes
   *          mimetypes for which Gzip compression required.
   */
  public PaymentGzipResponseWrapper(HttpServletResponse response, Set<String> mimetypes) {
    super(response);
    this.mimetypes = mimetypes;
  }

  @Override
  public ServletOutputStream getOutputStream() throws IOException {
    // create, implement and return ServletOutputStream
    return new ServletOutputStream() {

      @Override
      public void write(int b) throws IOException {
        verifyAndCreateGzipOutputStream().write(b);
      }

      @Override
      public void setWriteListener(WriteListener arg0) {
        log.info("setWriteListener(WriteListener arg0) called - NO implementation");
      }

      @Override
      public void write(byte[] bytes) throws IOException {
        verifyAndCreateGzipOutputStream().write(bytes);
      }

      @Override
      public void write(byte[] bytes, int offset, int length) throws IOException {
        verifyAndCreateGzipOutputStream().write(bytes, offset, length);
      }

      @Override
      public void flush() throws IOException {
        verifyAndCreateGzipOutputStream().flush();
      }

      @Override
      public void close() throws IOException {
        verifyAndCreateGzipOutputStream().close();
      }

      @Override
      public boolean isReady() {
        return false;
      }
    };
  }

  @Override
  public void flushBuffer() throws IOException {
    super.flushBuffer();
    if (outputStream != null) {
      outputStream.flush();
    }
  }

  /**
   * Close the response body. This closes any created writer or output stream.
   * 
   * @throws IOException
   *           When an I/O error occurs.
   */
  public void close() throws IOException {
    if (outputStream != null) {
      outputStream.close();
    }
  }

  private OutputStream verifyAndCreateGzipOutputStream() throws IOException {
    if (outputStream == null) {
      String contentType = getContentType();
      // verify Gzip compression required or not based on mimetypes.
      if (compressResponse
          || (mimetypes != null && contentType != null && mimetypes.contains(contentType))) {
        // set content encoding to gzip, otherwise data will shown as inverted question marks.
        setHeader(CONTENT_ENCODING, GZIP);
        outputStream = new GZIPOutputStream(createOutputStream());
      } else {
        outputStream = createOutputStream();
      }
    }
    return outputStream;

  }

  private OutputStream createOutputStream() throws IOException {
    HttpServletResponse originalResponse = (HttpServletResponse) getResponse();
    return originalResponse.getOutputStream();
  }
}
