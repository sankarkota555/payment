package com.payment.controllers;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.payment.domain.Item;
import com.payment.service.ItemService;

@RestController
public class ItemController {

  private Logger log = Logger.getLogger(ItemController.class);

  @Autowired
  private ItemService itemService;

/*  @RequestMapping(value = "/addItem", method = RequestMethod.POST)
  public boolean addItem(@RequestBody ItemDTO itemDto) {
    log.info("item received: " + itemDto.getItemName());
    itemService.addItem(itemDto);
    return false;

  }*/

 @RequestMapping(value = "/getAllItems", method = RequestMethod.GET)
  public Iterable<Item> getAllItems() {
    return itemService.getAllItems();
  }
  
  @RequestMapping(value = "/findItems", method = RequestMethod.GET)
  public List<Item> findItemsLike(@RequestParam String searchText) {
    return itemService.findItemsLike(searchText);
  }

}
