package com.payment.controllers;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {

  private Logger log = Logger.getLogger(ItemController.class);


  @RequestMapping(value = "/addItem", method = RequestMethod.POST)
  public boolean addItem() {
  
    return false;

  }

  @RequestMapping(value = "/getAllItems", method = RequestMethod.GET)
  public String getAllItems() {
    // log.info("item received: "+ item.getItemName());
    return null;

  }

}
