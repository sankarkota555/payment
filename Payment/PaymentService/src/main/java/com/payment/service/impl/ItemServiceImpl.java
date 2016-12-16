package com.payment.service.impl;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payment.domain.Item;
import com.payment.dto.ItemDTO;
import com.payment.repositories.ItemCompanyRepository;
import com.payment.repositories.ItemRepository;

@Service
public class ItemServiceImpl {

  private Logger log = Logger.getLogger(ItemServiceImpl.class);

  @Autowired
  private ItemRepository itemRepository;

  @Autowired
  private ItemCompanyRepository itemCompanyRepository;

  @Transactional
  public boolean addItem(ItemDTO itemDTO) {
    /*log.info("item in service: " + itemDTO.getItemName());
    Item item = new Item();
    item.setItemName(itemDTO.getItemName());
    List<ItemCompany> companies = new ArrayList<>();
    companies.add(itemCompanyRepository.findOne(itemDTO.getCompanyId()));
    item.setItemCompanies(companies);
    itemRepository.save(item);*/
    return true;
  }

  @Transactional
  public Iterable<Item> getAllItems() {
    return itemRepository.findAll();
  }

}
