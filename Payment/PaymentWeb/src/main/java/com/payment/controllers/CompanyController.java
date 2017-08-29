package com.payment.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.payment.domain.ItemCompany;
import com.payment.service.CompanyService;

@RestController
public class CompanyController {

  private static final Logger log = LoggerFactory.getLogger(CompanyController.class);

  @Autowired
  private CompanyService companyService;

  @RequestMapping(value = "/getAllCompanies", method = RequestMethod.GET)
  public Iterable<ItemCompany> getAllCompanies() {
    // log.info("item received: "+ item.getItemName());
    return companyService.getAllCompanies();
  }

  @RequestMapping(value = "/findCompaniesLike", method = RequestMethod.POST)
  public List<ItemCompany> findCompaniesLike(String companyName) {
    log.info("companyName for searching: {}", companyName);
    return companyService.findCompaniesLike(companyName);

  }

}
