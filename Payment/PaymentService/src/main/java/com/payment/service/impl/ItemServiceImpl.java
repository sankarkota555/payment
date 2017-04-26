package com.payment.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payment.domain.Item;
import com.payment.repositories.ItemCompanyRepository;
import com.payment.repositories.ItemRepository;
import com.payment.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

  private static final Logger log = LoggerFactory.getLogger(ItemServiceImpl.class);


  @Autowired
  private ItemRepository itemRepository;

  @Autowired
  private ItemCompanyRepository itemCompanyRepository;

  /*
   * @Transactional public boolean addItem(ItemDTO itemDTO) {
   * 
   * log.info("item in service: " + itemDTO.getItemName()); Item item = new Item();
   * item.setItemName(itemDTO.getItemName()); List<ItemCompany> companies = new ArrayList<>();
   * companies.add(itemCompanyRepository.findOne(itemDTO.getCompanyId()));
   * item.setItemCompanies(companies); itemRepository.save(item);
   * 
   * return true; }
   */

  
  @Transactional
  public List<Item> getAllItems() {
    return itemRepository.findAll();
  }
   

  @Override
  public List<Item> findItemsLike(String searchItemName) {
    return itemRepository.findByItemNameStartingWith(searchItemName.toLowerCase());
  }

}
