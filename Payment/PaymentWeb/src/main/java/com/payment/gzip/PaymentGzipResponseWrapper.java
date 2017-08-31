package com.payment.gzip;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaymentGzipResponseWrapper extends HttpServletResponseWrapper {

  private static final Logger log = LoggerFactory.getLogger(PaymentGzipResponseWrapper.class);

  /*
   * private ServletOutputStream output; private PrintWriter writer;
   */
  private GZIPOutputStream gzipOutputStream;

  private static final String CONTENT_ENCODING = "Content-Encoding";

  private static final String GZIP = "gzip";

  public PaymentGzipResponseWrapper(HttpServletResponse response) {
    super(response);
    // set content encoding to gzip, otherwise data will shown as inverted question marks.
    response.setHeader(CONTENT_ENCODING, GZIP);
  }

  @Override
  public ServletOutputStream getOutputStream() throws IOException {
    //create, implement and return ServletOutputStream
    return new ServletOutputStream() {

      @Override
      public void write(int b) throws IOException {
        createGzipOutputStream().write(b);
      }

      @Override
      public void setWriteListener(WriteListener arg0) {
        log.info("setWriteListener(WriteListener arg0) called - NO implementation");
      }

      @Override
      public void write(byte[] bytes) throws IOException {
        createGzipOutputStream().write(bytes);
      }

      @Override
      public void write(byte[] bytes, int offset, int length) throws IOException {
        createGzipOutputStream().write(bytes, offset, length);
      }

      @Override
      public void flush() throws IOException {
        createGzipOutputStream().flush();
      }

      @Override
      public void close() throws IOException {
        createGzipOutputStream().close();
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
    if (gzipOutputStream != null) {
      gzipOutputStream.flush();
    }
  }

  /**
   * Close the response body. This closes any created writer or output stream.
   * 
   * @throws IOException
   *           When an I/O error occurs.
   */
  public void close() throws IOException {
    if (gzipOutputStream != null) {
      gzipOutputStream.close();
    }
  }

  private OutputStream createGzipOutputStream() throws IOException {
    if (gzipOutputStream == null) {
      gzipOutputStream = new GZIPOutputStream(createOutputStream());
    }
    return gzipOutputStream;

  }
  
  private OutputStream createOutputStream() throws IOException {
    HttpServletResponse originalResponse = (HttpServletResponse) getResponse();
    return originalResponse.getOutputStream();
  }
}
