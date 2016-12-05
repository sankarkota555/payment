package com.payment.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.payment.domain.Item;
import com.payment.domain.ItemCompany;
import com.payment.service.CompanyService;

@RestController
public class CompanyController {

  @Autowired
  private CompanyService companyService;
  
  @RequestMapping(value = "/getAllCompanies", method = RequestMethod.GET)
  public Iterable<ItemCompany> getAllCompanies() {
    // log.info("item received: "+ item.getItemName());
    return companyService.getAllCompanies();

  }
  
}
