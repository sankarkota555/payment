package com.payment.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.payment.domain.ItemCompany;
import com.payment.service.impl.CompanyServiceImpl;

@RestController
public class CompanyController {

  
  private static final Logger log = LoggerFactory.getLogger(CompanyController.class);


  @Autowired
  private CompanyServiceImpl companyService;

  @RequestMapping(value = "/getAllCompanies", method = RequestMethod.GET)
  public Iterable<ItemCompany> getAllCompanies() {
    // log.info("item received: "+ item.getItemName());

    Iterable<ItemCompany> companies = companyService.getAllCompanies();
    return companies;

  }

}
