package com.payment.repositories;

import org.springframework.data.repository.CrudRepository;

import com.payment.domain.ItemDetails;

public interface ItemDetailsRepository extends CrudRepository<ItemDetails, Long>{

}
