package com.payment.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.payment.domain.Bill;

public interface BillRepository extends PagingAndSortingRepository<Bill, Long> {

  List<Bill> findByGeneratedDateBetween(Date frmDate, Date toDate);

}
