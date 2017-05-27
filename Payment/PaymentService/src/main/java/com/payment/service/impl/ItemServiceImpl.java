package com.payment.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payment.domain.Item;
import com.payment.domain.ItemPriceDeatils;
import com.payment.mapper.PaymentMapper;
import com.payment.repositories.ItemCompanyRepository;
import com.payment.repositories.ItemPriceDetailsRepositoty;
import com.payment.repositories.ItemRepository;
import com.payment.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

  private static final Logger log = LoggerFactory.getLogger(ItemServiceImpl.class);

  @Autowired
  private ItemRepository itemRepository;

  @Autowired
  private ItemCompanyRepository itemCompanyRepository;

  @Autowired
  private ItemPriceDetailsRepositoty itemPriceDetailsRepositoty;

  @Autowired
  private PaymentMapper paymentMapper;

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

  /**
   * get All Items from DB
   * 
   * @return All items as {@link List} of {@link Item}
   */
  @Override
  public List<Item> getAllItems() {
    return itemRepository.findAll();
  }

  /**
   * Find items, which item name starts with given name.
   * 
   * @param searchItemName
   *          item name to be search
   * @return matched items as {@link List} of {@link Item}
   */
  @Override
  public List<Item> findItemsLike(String searchItemName) {
    return itemRepository.findByItemNameStartingWith(searchItemName.toLowerCase());
  }

  /**
   * Update Item price details.
   * 
   * @return true, if operation success.<br>
   *         else false.
   */
  @Override
  @Transactional
  public boolean updateItemDetails(ItemPriceDeatils itemPriceDeatils) {
    log.info("Trying to update item price details with id: " + itemPriceDeatils.getId());
    ItemPriceDeatils itemPriceDeatilsDb = itemPriceDetailsRepositoty
        .findOne(itemPriceDeatils.getId());   
    if (itemPriceDeatilsDb != null) {
      // update details
      itemPriceDeatilsDb.setCapacity(itemPriceDeatils.getCapacity());
      itemPriceDeatilsDb.setQuantity(itemPriceDeatils.getQuantity());
      itemPriceDeatilsDb.setPrice(itemPriceDeatils.getPrice());
    } else {
      log.info("Item price details not found in DB with id: " + itemPriceDeatils.getId());
      return false;
    }
    // No need to save object manually, Transaction will be auto commit the object.
    return true;
  }

  /**
   * Add new Item price details.
   * 
   * @return Id of object saved.
   */
  @Override
  @Transactional
  public Long addNewItemDetails(ItemPriceDeatils itemPriceDeatils) {
    log.info("Trying to save new item price details");
    paymentMapper.findAndMapItemPricedetails(itemPriceDeatils, itemPriceDeatils.getPrice());
    return itemPriceDetailsRepositoty.save(itemPriceDeatils).getId();
  }
}
