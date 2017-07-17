package com.payment.repositories;

import org.springframework.data.repository.CrudRepository;

import com.payment.domain.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
	
	public Customer findByNameIgnoreCase(String name);

	public Customer findByPhone(String phone);

}
