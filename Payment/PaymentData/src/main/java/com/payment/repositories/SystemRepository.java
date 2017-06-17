package com.payment.repositories;

import org.springframework.data.repository.CrudRepository;

import com.payment.domain.PaymentSystem;

public interface SystemRepository extends CrudRepository<PaymentSystem, Long> {

  PaymentSystem findBySystemName(String systemName);

}
