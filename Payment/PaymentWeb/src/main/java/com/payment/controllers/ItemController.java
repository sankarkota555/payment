package com.payment.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.payment.domain.Item;
import com.payment.domain.ItemPriceDetails;
import com.payment.service.ItemService;

@RestController
public class ItemController {

  private static final Logger log = LoggerFactory.getLogger(ItemController.class);

  @Autowired
  private ItemService itemService;


  @RequestMapping(value = "/getAllItems", method = RequestMethod.GET)
  public Iterable<Item> getAllItems() {
    return itemService.getAllItems();
  }

  @RequestMapping(value = "/findItems", method = RequestMethod.POST)
  public List<Item> findItemsLike(String itemName) {
    return itemService.findItemsLike(itemName);
  }

  @RequestMapping(value = "/updateItemDetails", method = RequestMethod.POST)
  public boolean updateItemDetails(@RequestBody ItemPriceDetails itemPriceDetails) {
    return itemService.updateItemDetails(itemPriceDetails);
  }

  @RequestMapping(value = "/addItemDetails", method = RequestMethod.POST)
  public Long addItemDetails(@RequestBody ItemPriceDetails itemPriceDetails) {
    return itemService.addNewItemDetails(itemPriceDetails);
  }

}
