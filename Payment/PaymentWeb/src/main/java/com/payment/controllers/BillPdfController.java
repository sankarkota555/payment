package com.payment.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.payment.service.BillingService;
import com.payment.utils.PaymentConstantNames;

@Controller
public class BillPdfController {

  @Autowired
  private BillingService billingService;
  
  @Autowired
  private PaymentConstantNames paymentConstantNames ;

  @RequestMapping(value = "/generateBillPdf", method = RequestMethod.GET, produces={MediaType.APPLICATION_PDF_VALUE})
  public ModelAndView generateBillPdf(@RequestParam Long billId) {
    Map<String, Object> model = new HashMap();
    model.put("bill", billingService.getBillById(billId));
    model.put("paymentConstants",paymentConstantNames );
    return new ModelAndView("billPdfView", model);
  }

}
