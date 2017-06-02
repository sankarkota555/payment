package com.payment.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payment.domain.ItemCompany;
import com.payment.repositories.ItemCompanyRepository;
import com.payment.service.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService{
  
  @Autowired
  private ItemCompanyRepository itemCompanyRepository;

  /**
   * Gives all companies
   * 
   * @return {@link Iterable} of {@link ItemCompany}
   */
  @Override
  @Transactional
  public Iterable<ItemCompany> getAllCompanies(){
    return  itemCompanyRepository.findAll();
  }
  
  /**
   * Gives companies whose nams starts with given name.
   * @param companyName name to be search.
   * @return {@link List} of {@link ItemCompany}
   */
  @Override
  @Transactional
  public List<ItemCompany> findCompaniesLike(String companyName){
    return  itemCompanyRepository.findByCompanyNameStartingWith(companyName);
  }
}
