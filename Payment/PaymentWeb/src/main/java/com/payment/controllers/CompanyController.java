package com.payment.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyController {


  
  @RequestMapping(value = "/getAllCompanies", method = RequestMethod.GET)
  public String getAllCompanies() {
    // log.info("item received: "+ item.getItemName());
    //return companyService.getAllCompanies();
   return null;
  }
  
}
