package com.payment.repositories;

import org.springframework.data.repository.CrudRepository;

import com.payment.domain.ItemPriceDetails;

public interface ItemPriceDetailsRepositoty extends CrudRepository<ItemPriceDetails, Long> {

}
