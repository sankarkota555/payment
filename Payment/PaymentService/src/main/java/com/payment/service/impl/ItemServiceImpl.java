package com.payment.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payment.domain.Item;
import com.payment.domain.ItemPriceDetails;
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
    return itemRepository.findByItemNameStartingWithIgnoreCase(searchItemName.toLowerCase());
  }

  /**
   * Update Item price details.
   * 
   * @return true, if operation success.<br>
   *         else false.
   */
  @Override
  @Transactional
  public boolean updateItemDetails(ItemPriceDetails itemPriceDetails) {
    log.info("Trying to update item price details with id: " + itemPriceDetails.getId());
    ItemPriceDetails itemPriceDetailsDb = itemPriceDetailsRepositoty
        .findOne(itemPriceDetails.getId());
    if (itemPriceDetailsDb != null) {
      // update details
      itemPriceDetailsDb.setCapacity(itemPriceDetails.getCapacity());
      itemPriceDetailsDb.setQuantity(itemPriceDetails.getQuantity());
      itemPriceDetailsDb.setPrice(itemPriceDetails.getPrice());
    } else {
      log.info("Item price details not found in DB with id: " + itemPriceDetails.getId());
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
  public Long addNewItemDetails(ItemPriceDetails itemPriceDetails) {
    log.info("Trying to save new item price details");
    // mapping is not for bill, so send false.
    boolean isForBill = false;
    paymentMapper.findAndMapItemPricedetails(itemPriceDetails, itemPriceDetails.getPrice(),
        isForBill);
    return itemPriceDetailsRepositoty.save(itemPriceDetails).getId();
  }

  public ItemRepository getItemRepository() {
    return itemRepository;
  }

  public void setItemRepository(ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }

  public ItemCompanyRepository getItemCompanyRepository() {
    return itemCompanyRepository;
  }

  public void setItemCompanyRepository(ItemCompanyRepository itemCompanyRepository) {
    this.itemCompanyRepository = itemCompanyRepository;
  }

  public ItemPriceDetailsRepositoty getItemPriceDetailsRepositoty() {
    return itemPriceDetailsRepositoty;
  }

  public void setItemPriceDetailsRepositoty(ItemPriceDetailsRepositoty itemPriceDetailsRepositoty) {
    this.itemPriceDetailsRepositoty = itemPriceDetailsRepositoty;
  }

  public PaymentMapper getPaymentMapper() {
    return paymentMapper;
  }

  public void setPaymentMapper(PaymentMapper paymentMapper) {
    this.paymentMapper = paymentMapper;
  }
  
  
}
