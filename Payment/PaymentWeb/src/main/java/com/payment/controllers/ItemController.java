package com.payment.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.payment.domain.Item;
import com.payment.domain.ItemPriceDeatils;
import com.payment.service.ItemService;

@RestController
public class ItemController {

  private static final Logger log = LoggerFactory.getLogger(ItemController.class);

  @Autowired
  private ItemService itemService;

  /*
   * @RequestMapping(value = "/addItem", method = RequestMethod.POST) public boolean
   * addItem(@RequestBody ItemDTO itemDto) { log.info("item received: " + itemDto.getItemName());
   * itemService.addItem(itemDto); return false;
   * 
   * }
   */

  @RequestMapping(value = "/getAllItems", method = RequestMethod.GET)
  public Iterable<Item> getAllItems() {
    return itemService.getAllItems();
  }

  @RequestMapping(value = "/findItems", method = RequestMethod.GET)
  public List<Item> findItemsLike(@RequestParam String searchText) {
    return itemService.findItemsLike(searchText);
  }

  @RequestMapping(value = "/updateItemDetails", method = RequestMethod.POST)
  public boolean updateItemDetails(@RequestBody ItemPriceDeatils itemPriceDeatils) {
    return itemService.updateItemDetails(itemPriceDeatils);
  }

  @RequestMapping(value = "/addItemDetails", method = RequestMethod.POST)
  public Long addItemDetails(@RequestBody ItemPriceDeatils itemPriceDeatils) {
    return itemService.addNewItemDetails(itemPriceDeatils);
  }

}
