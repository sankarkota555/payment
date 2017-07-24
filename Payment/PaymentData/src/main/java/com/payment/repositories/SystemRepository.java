package com.payment.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.payment.domain.PaymentSystem;

public interface SystemRepository extends PagingAndSortingRepository<PaymentSystem, Long> {

  PaymentSystem findBySystemNameIgnoreCase(String systemName);

}
