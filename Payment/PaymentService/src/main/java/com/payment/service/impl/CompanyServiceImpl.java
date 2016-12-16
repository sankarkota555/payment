package com.payment.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payment.domain.ItemCompany;
import com.payment.repositories.ItemCompanyRepository;

@Service
public class CompanyServiceImpl {
  
  @Autowired
  private ItemCompanyRepository itemCompanyRepository;

  @Transactional
  public Iterable<ItemCompany> getAllCompanies(){
    return  itemCompanyRepository.findAll();
  }
}
