package com.payment.security;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.header.HeaderWriter;
import org.springframework.stereotype.Component;

import com.payment.utils.DateUtils;

@Component
public class PaymentHeaderWriter implements HeaderWriter {

  private static final Logger log = LoggerFactory.getLogger(PaymentHeaderWriter.class);

  // cache content for 7 days
  private static final int CACHE_DAYS = 7;

  private static final String CACHE_MAX_AGE = "max-age=" + (CACHE_DAYS * 24 * 60 * 60);

  @Override
  public void writeHeaders(HttpServletRequest request, HttpServletResponse response) {
    log.info("---------url: {}", request.getRequestURI());

    Collection<String> heads = response.getHeaderNames();

    for (String hd : heads) {
      log.info("hd:{} value:{} ", hd, response.getHeader(hd));
    }

    response.setHeader("Cache-Control", CACHE_MAX_AGE);
    response.setDateHeader("Expires",
        DateUtils.addDaysToDate(DateUtils.getCurrentdate(), CACHE_DAYS).getTime());

  }

}