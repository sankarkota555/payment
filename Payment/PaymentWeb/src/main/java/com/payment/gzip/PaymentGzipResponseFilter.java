package com.payment.gzip;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Filter for Gzip response. Init parameter name is <b><code>mimetypes</code></b>.<br/>
 * By default compression mimetypes are ->
 * <code>"text/plain", "text/html","text/xml", "text/css", "text/javascript", "text/csv", "text/rtf", "application/xml",
 *    "application/xhtml+xml", "application/javascript", "application/json", "image/svg+xml"</code>
 *
 */
public class PaymentGzipResponseFilter implements Filter {

  private static final String INIT_PARAM_MIMETYPES = "mimetypes";

  private static final Set<String> DEFAULT_MIMETYPES = createSet("text/plain", "text/html",
      "text/xml", "text/css", "text/javascript", "text/csv", "text/rtf", "application/xml",
      "application/xhtml+xml", "application/javascript", "application/json", "image/svg+xml");

  private Set<String> mimetypes = DEFAULT_MIMETYPES;

  /**
   * Initializes the filter parameters.
   */
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    String mimetypesParam = filterConfig.getInitParameter(INIT_PARAM_MIMETYPES);
    if (mimetypesParam != null) {
      mimetypes = new HashSet(Arrays.asList(mimetypesParam.split("\\s*,\\s*")));
    }
  }

  /**
   * Perform the filtering job. Only if the client accepts GZIP based on the request headers, then
   * wrap the response in a {@link GzipHttpServletResponse} and pass it through the filter chain.
   */
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {
    /* verify gzip header presence */
    if (verifyGzip(request)) {
      PaymentGzipResponseWrapper gzipResponse = new PaymentGzipResponseWrapper(
          (HttpServletResponse) response, mimetypes);
      filterChain.doFilter(request, gzipResponse);
      gzipResponse.close(); // Mandatory for the case the threshold limit hasn't been reached.
    } else {
      filterChain.doFilter(request, response);
    }
  }

  @Override
  public void destroy() {
    // Do nothing.
  }

  private static Set<String> createSet(String... strings) {
    Set<String> set = new HashSet();
    for (String str : strings) {
      set.add(str);
    }
    return set;
  }

  private boolean verifyGzip(ServletRequest request) {
    return Arrays.asList(((HttpServletRequest) request).getHeader("Accept-Encoding").split(","))
        .stream().anyMatch(value -> value.equals("gzip"));
  }

}
