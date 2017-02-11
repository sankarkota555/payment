package com.payment.service;

import java.util.List;

import com.payment.domain.Item;

public interface ItemService {
  
  List<Item> findItemsLike(String searchItemName);
  
  List<Item> getAllItems();

}
