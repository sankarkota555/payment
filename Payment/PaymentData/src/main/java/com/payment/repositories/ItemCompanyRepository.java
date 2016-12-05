package com.payment.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.payment.domain.ItemCompany;

public interface ItemCompanyRepository extends PagingAndSortingRepository<ItemCompany, Long>{

}
