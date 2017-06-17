package com.payment.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.payment.domain.PaymentSystem;
import com.payment.domain.PaymentSystemUsageDetails;

public interface SystemUsageDetailsRepository
    extends CrudRepository<PaymentSystemUsageDetails, Long> {

  @Query("SELECT details FROM PaymentSystemUsageDetails details WHERE details.paymentSystem = :system AND details.loginTime > :date ORDER BY details.loginTime DESC")
  List<PaymentSystemUsageDetails> getSystemUsageStatus(@Param("system") PaymentSystem system,@Param("date") Date date, Pageable pageable);

}
