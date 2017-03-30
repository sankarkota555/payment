package com.payment.controllers;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.payment.domain.Bill;
import com.payment.service.BillingService;

@RestController
public class BillingController {

  private static Logger log = LoggerFactory.getLogger(BillingController.class);

  @Autowired
  private BillingService billingService;

  @RequestMapping(value = "/saveBill", method = RequestMethod.POST, consumes = {
      MediaType.APPLICATION_JSON_VALUE })
  public Long saveBill(@RequestBody Bill bill) {
    return billingService.saveBill(bill);

  }

  @RequestMapping(value = "/getCustomerBills", method = RequestMethod.GET, consumes = {
      MediaType.APPLICATION_JSON_VALUE })
  public List<Bill> getCustomerBills(@RequestParam Long customerId) {
    return billingService.getCustomerBills(customerId);
  }

  @RequestMapping(value = "/getBillsBasedOnDates", method = RequestMethod.POST, consumes = {
      MediaType.APPLICATION_FORM_URLENCODED_VALUE })
  public List<Bill> getCustomerBills(Date fromDate, Date toDate) {
    return billingService.getBillsBetweebDates(fromDate, toDate);
  }

}
