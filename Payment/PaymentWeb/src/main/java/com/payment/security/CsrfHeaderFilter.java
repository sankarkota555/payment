package com.payment.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import com.payment.gzip.PaymentGzipResponseWrapper;
import com.payment.utils.DateUtils;

@Component
public class CsrfHeaderFilter extends OncePerRequestFilter {

  private static final Logger log = LoggerFactory.getLogger(CsrfHeaderFilter.class);

  private static final String RESOURCE_CACHE_PATH = "javax.faces.resource";
  // cache content for 7 days
  private static final int CACHE_DAYS = 7;

  private static final String CACHE_MAX_AGE = "max-age=" + (CACHE_DAYS * 24 * 60 * 60);
  private static final String CACHE_CONTROL = "Cache-Control";
  private static final String EXPIRES = "Expires";

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
    if (csrf != null) {
      Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
      String token = csrf.getToken();
      log.info("csrf token: {}", token);
      if (cookie == null || token != null && !token.equals(cookie.getValue())) {
        cookie = new Cookie("XSRF-TOKEN", token);
        cookie.setPath(request.getContextPath());
        response.addCookie(cookie);
      }
    }

    if (request.getRequestURI().contains(RESOURCE_CACHE_PATH)) {
      response.setHeader(CACHE_CONTROL, CACHE_MAX_AGE);
      response.setDateHeader(EXPIRES,
          DateUtils.addDaysToDate(DateUtils.getCurrentdate(), CACHE_DAYS).getTime());

      filterChain.doFilter(request, new PaymentGzipResponseWrapper(response));
    } else {
      filterChain.doFilter(request, response);
    }

  }

}
