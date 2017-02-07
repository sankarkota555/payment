package com.payment.repositories;

import org.springframework.data.repository.CrudRepository;

import com.payment.domain.PaymentUser;

public interface UserRepository extends CrudRepository<PaymentUser, Long>{
  
  public PaymentUser findByUserName(String userName);

}
