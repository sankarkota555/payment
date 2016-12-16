package com.payment.controllers;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.payment.domain.Item;
import com.payment.domain.ItemCompany;
import com.payment.dto.ItemDTO;
import com.payment.service.impl.ItemServiceImpl;

@RestController
public class ItemController {

  private Logger log = Logger.getLogger(ItemController.class);

  @Autowired
  private ItemServiceImpl itemService;

  @RequestMapping(value = "/addItem", method = RequestMethod.POST)
  public boolean addItem(@RequestBody ItemDTO itemDto) {
    log.info("item received: " + itemDto.getItemName());
    itemService.addItem(itemDto);
    return false;

  }

  @RequestMapping(value = "/getAllItems", method = RequestMethod.GET)
  public Iterable<Item> getAllItems() {
    // log.info("item received: "+ itemService.getAllItems().);
    Iterable<Item> iterable = itemService.getAllItems();

    return itemService.getAllItems();

  }

}
