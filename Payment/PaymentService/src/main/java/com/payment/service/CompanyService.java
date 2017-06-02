package com.payment.service;

import java.util.List;

import com.payment.domain.ItemCompany;

public interface CompanyService {
  /**
   * Gives assl companies
   * 
   * @return {@link Iterable} of {@link ItemCompany}
   */
  Iterable<ItemCompany> getAllCompanies();

  /**
   * Gives companies whose nams starts with given name.
   * @param companyName name to be search.
   * @return {@link List} of {@link ItemCompany}
   */
  List<ItemCompany> findCompaniesLike(String companyName);

}
