package com.payment.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.payment.domain.Bill;

public interface BillRepository extends PagingAndSortingRepository<Bill, Long> {
  
  @Query("From Bill bill where bill.generatedDate BETWEEN :frmDate AND :toDate")
  List<Bill> getBillsBetweenDates(@Param("frmDate") Date frmDate,@Param("toDate") Date toDate);

}
