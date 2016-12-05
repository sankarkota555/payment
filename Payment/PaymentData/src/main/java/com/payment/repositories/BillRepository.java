package com.payment.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.payment.domain.Bill;

public interface BillRepository extends PagingAndSortingRepository<Bill, Long> {

}
