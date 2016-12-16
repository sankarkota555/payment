package com.payment.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.payment.domain.Bill;
import com.payment.service.BillingService;

@RestController
public class BillingController {

  private static Logger log = Logger.getLogger(BillingController.class);

  @Autowired
  private BillingService billingService;

  @RequestMapping(name = "/saveBill", method = RequestMethod.POST, consumes= {"application/json"})
  public void saveBill(@RequestBody Bill bill) {
    billingService.saveBill(bill);
    
  }

}
