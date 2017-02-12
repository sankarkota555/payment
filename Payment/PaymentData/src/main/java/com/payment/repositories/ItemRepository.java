package com.payment.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.payment.domain.Item;

public interface ItemRepository extends PagingAndSortingRepository<Item, Long> {

  public Item findByItemName(String itemName);
  
  @Query("From Item item where lower(item.itemName) like :itemName%")
  public List<Item> findItemsLike(@Param("itemName") String searchItemName);
  
  @Override
  public List<Item> findAll();

}
