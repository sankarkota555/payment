package com.payment.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.payment.service.BillingService;

@Controller
public class BillPdfController {

  @Autowired
  private BillingService billingService;

  @RequestMapping(value = "/generateBillPdf", method = RequestMethod.GET, produces={"application/pdf"})
  public ModelAndView generateBillPdf(@RequestParam Long billId) {
    return new ModelAndView("billPdfView", "bill", billingService.getBillById(billId));
  }

}
