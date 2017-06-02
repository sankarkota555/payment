package com.payment.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.payment.domain.ItemCompany;

public interface ItemCompanyRepository extends PagingAndSortingRepository<ItemCompany, Long> {

  public ItemCompany findByCompanyName(String companyName);
  
  public List<ItemCompany> findByCompanyNameStartingWith(String companyName);

}
