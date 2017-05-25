package com.payment.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.payment.domain.Item;

public interface ItemRepository extends PagingAndSortingRepository<Item, Long> {

  List<Item> findByItemNameStartingWith(String searchItemName);

  Item findByItemName(String itemName);

  @Override
  public List<Item> findAll();

}
