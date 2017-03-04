package com.payment.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * For handling logout success.
 */
// @Component
public class CustomLogoutHandler extends SimpleUrlLogoutSuccessHandler {

  private static final Logger log = LoggerFactory.getLogger(CustomLogoutHandler.class);

  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    response.sendRedirect("pymt/logoutSuccess");
    log.info("Successfully logged out, user: " + authentication.getName());
    super.onLogoutSuccess(request, response, authentication);
  }

}
