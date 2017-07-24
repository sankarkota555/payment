package com.payment.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.payment.domain.Item;

public interface ItemRepository extends PagingAndSortingRepository<Item, Long> {

  List<Item> findByItemNameStartingWithIgnoreCase(String searchItemName);

  Item findByItemNameIgnoreCase(String itemName);

  @Override
  public List<Item> findAll(Sort sort);

}
