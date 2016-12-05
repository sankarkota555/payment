package com.payment.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.payment.domain.Item;

public interface ItemRepository extends PagingAndSortingRepository<Item, Long>{

}
