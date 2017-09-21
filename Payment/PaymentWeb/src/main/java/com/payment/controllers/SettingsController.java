package com.payment.controllers;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.payment.service.SettingsService;

@RestController
public class SettingsController {

  private static final Logger log = LoggerFactory.getLogger(SettingsController.class);

  @Autowired
  private SettingsService settingsService;

  @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
  public int updateItemDetails(String currentPassword, String newpassword,
      final Principal principal) {
    log.info("Chanage password requesting for user:{}", principal.getName());
    return settingsService.chagePassword(currentPassword, newpassword, principal.getName());
  }

}
