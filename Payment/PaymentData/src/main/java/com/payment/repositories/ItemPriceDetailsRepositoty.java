package com.payment.repositories;

import org.springframework.data.repository.CrudRepository;

import com.payment.domain.ItemPriceDeatils;

public interface ItemPriceDetailsRepositoty extends CrudRepository<ItemPriceDeatils, Long> {

}
