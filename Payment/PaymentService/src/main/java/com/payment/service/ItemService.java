package com.payment.service;

import java.util.List;

import com.payment.domain.Item;
import com.payment.domain.ItemPriceDetails;

public interface ItemService {

  /**
   * Find items, which item name starts with given name.
   * 
   * @param searchItemName
   *          item name to be search
   * @return matched items as {@link List} of {@link Item}
   */
  List<Item> findItemsLike(String searchItemName);

  /**
   * get All Items from DB
   * 
   * @return All items as {@link List} of {@link Item}
   */
  List<Item> getAllItems();

  /**
   * Update Item price details.
   * 
   * @return true, if operation success.<br>
   *         else false.
   */
  boolean updateItemDetails(ItemPriceDetails itemPriceDetails);

  /**
   * Add new Item price details.
   * 
   * @return Id of object saved.
   */
  Long addNewItemDetails(ItemPriceDetails itemPriceDetails);

}
