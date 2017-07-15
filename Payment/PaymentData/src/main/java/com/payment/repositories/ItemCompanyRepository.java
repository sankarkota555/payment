package com.payment.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.payment.domain.ItemCompany;

public interface ItemCompanyRepository extends PagingAndSortingRepository<ItemCompany, Long> {

  public ItemCompany findByCompanyNameIgnoreCase(String companyName);
  
  public List<ItemCompany> findByCompanyNameStartingWithIgnoreCase(String companyName);

}
