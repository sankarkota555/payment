package com.payment.mapper;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.domain.Bill;
import com.payment.domain.Item;
import com.payment.domain.ItemCompany;
import com.payment.domain.ItemDetails;
import com.payment.domain.ItemPriceDeatils;
import com.payment.domain.SoldItem;
import com.payment.dto.BillDTO;
import com.payment.dto.BillItemDTO;
import com.payment.repositories.ItemCompanyRepository;
import com.payment.repositories.ItemRepository;

@Component
public class PaymentMapperImpl implements PaymentMapper {

  @Autowired
  private ItemRepository itemRepository;

  @Autowired
  private ItemCompanyRepository itemCompanyRepository;

  private static final Logger log = LoggerFactory.getLogger(PaymentMapperImpl.class);

  /**
   * Maps given {@link Bill} to {@link BillDTO}
   * 
   * @param bill
   *          {@link Bill} object
   * @return {@link BillDTO} object
   */
  @Override
  public BillDTO mapBillToDto(Bill bill) {
    return mapBillToBillDto(bill);
  }

  /**
   * Maps given list of {@link Bill} to {@link BillDTO}
   * 
   * @param bills
   *          list of {@link Bill} object
   * @return List of {@link BillDTO} object
   */
  @Override
  public List<BillDTO> mapBillToDto(List<Bill> bills) {
    List<BillDTO> billsDtoList = new ArrayList<>();
    for (Bill bill : bills) {
      billsDtoList.add(mapBillToBillDto(bill));
    }
    return billsDtoList;
  }

  private BillDTO mapBillToBillDto(Bill bill) {
    BillDTO billDto;
    billDto = new BillDTO();
    billDto.setBillId(bill.getBillId());
    billDto.setGeneratedDate(bill.getGeneratedDate());
    billDto.setCustomer(bill.getCustomer());
    billDto.setTotalAmount(bill.getNetAmount());
    BillItemDTO billItemDto;
    List<BillItemDTO> billItemsList = new ArrayList<>();
    String capacity = null;
    for (SoldItem soldItem : bill.getSoldItems()) {
      billItemDto = new BillItemDTO();

      billItemDto.setCapacity(soldItem.getItemPriceDeatils().getCapacity());
      billItemDto.setCompanyId(
          soldItem.getItemPriceDeatils().getItemDetails().getItemCompany().getCompanyId());
      billItemDto.setCompanyName(
          soldItem.getItemPriceDeatils().getItemDetails().getItemCompany().getCompanyName());
      billItemDto.setItemId(soldItem.getItemPriceDeatils().getItemDetails().getItem().getItemId());
      billItemDto
          .setItemName(soldItem.getItemPriceDeatils().getItemDetails().getItem().getItemName());
      billItemDto.setPrice(soldItem.getSoldPrice());
      billItemDto.setQuantity(soldItem.getQuantity());
      capacity = (soldItem.getItemPriceDeatils().getCapacity() != null)
          ? soldItem.getItemPriceDeatils().getCapacity() : "-";
      billItemDto.setCapacity(capacity);

      billItemsList.add(billItemDto);

    }
    billDto.setBillItems(billItemsList);
    return billDto;
  }

  /**
   * Maps given itemPriceDeatils into DB format.<br>
   * Finds items and companies if present in DB or else creates new ones.
   * 
   * @param itemPriceDeatils
   *          {@link ItemDetails} to be mapped.
   * @param price
   *          price of item.
   */
  @Override
  public void findAndMapItemPricedetails(ItemPriceDeatils itemPriceDeatils, Integer price) {
    String soldItemName = itemPriceDeatils.getItemDetails().getItem().getItemName();
    String soldItemConpanyName = itemPriceDeatils.getItemDetails().getItemCompany()
        .getCompanyName();

    // search for item in DB
    Item item = itemRepository.findByItemName(soldItemName);
    if (item == null) {
      log.info("Item not found in DB with name: {}", soldItemName);
      item = new Item();
      item.setItemName(soldItemName);
    }

    // search for item company in DB
    ItemCompany company = itemCompanyRepository.findByCompanyName(soldItemConpanyName);
    if (company == null) {
      log.info("Company not found in DB with name:{}", soldItemConpanyName);
      company = new ItemCompany();
      company.setCompanyName(soldItemConpanyName);
    }

    // create and set values to item details
    ItemDetails itemDetails = new ItemDetails();
    itemDetails.setItem(item);
    itemDetails.setItemCompany(company);

    // set values to item price details
    itemPriceDeatils.setItemDetails(itemDetails);
    itemPriceDeatils.setPrice(price);

  }

  /**
   * Converts and prints given object as JSON string.
   * 
   * @param object
   *          object which is to be converted as JSON.
   */
  @Override
  public void writeObjectAsJson(Object object) {
    try {
      log.info("Converting into JSON, given class: " + object.getClass());
      log.info("JSON string is:" + new ObjectMapper().writeValueAsString(object));
    } catch (JsonProcessingException e) {
      log.error("Unable to conver given object as JSON String, class name: " + object.getClass());
    }

  }

}
