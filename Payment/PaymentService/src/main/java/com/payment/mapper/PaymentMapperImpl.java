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
import com.payment.domain.Customer;
import com.payment.domain.Item;
import com.payment.domain.ItemCompany;
import com.payment.domain.ItemDetails;
import com.payment.domain.ItemPriceDetails;
import com.payment.domain.PaymentSystem;
import com.payment.domain.PaymentSystemUsageDetails;
import com.payment.domain.SoldItem;
import com.payment.dto.BillDTO;
import com.payment.dto.BillItemDTO;
import com.payment.dto.CustomerDTO;
import com.payment.dto.ItemDTO;
import com.payment.dto.ItemDetailsDTO;
import com.payment.dto.ItemPriceDetailsDTO;
import com.payment.dto.SystemDTO;
import com.payment.dto.SystemUsageDTO;
import com.payment.repositories.ItemCompanyRepository;
import com.payment.repositories.ItemDetailsRepository;
import com.payment.repositories.ItemRepository;

@Component
public class PaymentMapperImpl implements PaymentMapper {

  @Autowired
  private ItemRepository itemRepository;

  @Autowired
  private ItemCompanyRepository itemCompanyRepository;

  @Autowired
  private ItemDetailsRepository itemDetailsRepository;

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
    billDto.setCustomer(mapCustomer(bill.getCustomer(), false));
    billDto.setTotalAmount(bill.getNetAmount());
    BillItemDTO billItemDto;
    List<BillItemDTO> billItemsList = new ArrayList<>();
    String capacity = null;
    for (SoldItem soldItem : bill.getSoldItems()) {
      billItemDto = new BillItemDTO();

      billItemDto.setCapacity(soldItem.getItemPriceDetails().getCapacity());
      billItemDto.setCompanyId(
          soldItem.getItemPriceDetails().getItemDetails().getItemCompany().getCompanyId());
      billItemDto.setCompanyName(
          soldItem.getItemPriceDetails().getItemDetails().getItemCompany().getCompanyName());
      billItemDto.setItemId(soldItem.getItemPriceDetails().getItemDetails().getItem().getItemId());
      billItemDto
          .setItemName(soldItem.getItemPriceDetails().getItemDetails().getItem().getItemName());
      billItemDto.setPrice(soldItem.getSoldPrice());
      billItemDto.setQuantity(soldItem.getQuantity());
      capacity = (soldItem.getItemPriceDetails().getCapacity() != null)
          ? soldItem.getItemPriceDetails().getCapacity() : "-";
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
   * @param isForBill
   *          true, if you are mapping to save bill. <br>
   *          false, if you are mapping for update/adding new item.
   */
  @Override
  public void findAndMapItemPricedetails(ItemPriceDetails itemPriceDetails, Integer price,
      boolean isForBill) {
    writeObjectAsJson(itemPriceDetails);
    ItemDetails itemDetails = null;
    if (itemPriceDetails.getItemDetails().getId() == null) {
      String soldItemName = itemPriceDetails.getItemDetails().getItem().getItemName();
      String soldItemConpanyName = itemPriceDetails.getItemDetails().getItemCompany()
          .getCompanyName();

      // search for item in DB
      Item item = itemRepository.findByItemNameIgnoreCase(soldItemName);
      if (item == null) {
        log.info("Item not found in DB with name: {}", soldItemName);
        item = new Item();
        item.setItemName(soldItemName);
      }

      // search for item company in DB
      ItemCompany company = itemCompanyRepository.findByCompanyNameIgnoreCase(soldItemConpanyName);
      if (company == null) {
        log.info("Company not found in DB with name:{}", soldItemConpanyName);
        company = new ItemCompany();
        company.setCompanyName(soldItemConpanyName);
      }

      // create and set values to item details
      itemDetails = new ItemDetails();
      itemDetails.setItem(item);
      itemDetails.setItemCompany(company);
    } else {
      log.info("Searching for item details in DB with id:{}",
          itemPriceDetails.getItemDetails().getId());
      itemDetails = itemDetailsRepository.findOne(itemPriceDetails.getItemDetails().getId());
    }

    // set values to item price details
    itemPriceDetails.setItemDetails(itemDetails);
    itemPriceDetails.setPrice(price);
    // if mapping is for bill generation do not set quantity.
    if (isForBill) {
      itemPriceDetails.setQuantity(null);
    }
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

  @Override
  public CustomerDTO mapCustomer(Customer customer, boolean includeBills) {
    CustomerDTO customerDTO = new CustomerDTO();
    customerDTO.setAddress(customer.getAddress());
    customerDTO.setEmail(customer.getEmail());
    customerDTO.setId(customer.getId());
    customerDTO.setName(customer.getName());
    customerDTO.setPhone(customer.getPhone());
    if (includeBills) {
      customerDTO.setBills(mapBillToDto(customer.getBills()));
    }
    return customerDTO;
  }

  @Override
  public SystemUsageDTO mapPaymentSystemUsageDetails(PaymentSystemUsageDetails systemUsageDetails,
      boolean includeSystem) {

    SystemUsageDTO systemUsageDTO = new SystemUsageDTO();
    systemUsageDTO.setCutomerName(systemUsageDetails.getCutomerName());
    systemUsageDTO.setHours(systemUsageDetails.getHours());
    systemUsageDTO.setId(systemUsageDetails.getId());
    systemUsageDTO.setLoginTime(systemUsageDetails.getLoginTime());
    if (includeSystem) {
      // Don't include system
      systemUsageDTO
          .setPaymentSystem(mapPaymentSystem(systemUsageDetails.getPaymentSystem(), false));
    }

    return systemUsageDTO;
  }

  @Override
  public SystemDTO mapPaymentSystem(PaymentSystem system, boolean includeUsageDetails) {
    SystemDTO systemDTO = new SystemDTO();
    systemDTO.setId(system.getId());
    systemDTO.setSystemName(system.getSystemName());
    if (includeUsageDetails) {
      // Don't include usage details
      systemDTO.setUsageDetails(mapPaymentSystemUsageDetails(system.getUsageDetails(), false));
    }

    return systemDTO;
  }

  @Override
  public List<SystemUsageDTO> mapPaymentSystemUsageDetails(
      List<PaymentSystemUsageDetails> systemUsageDetails, boolean includeSystem) {

    List<SystemUsageDTO> list = new ArrayList<>();
    for (PaymentSystemUsageDetails details : systemUsageDetails) {
      list.add(mapPaymentSystemUsageDetails(details, includeSystem));
    }
    return list;
  }

  @Override
  public SystemDTO convertSystemUsageToSystem(PaymentSystemUsageDetails details) {
    List<SystemUsageDTO> usageList = new ArrayList<>();
    usageList.add(mapPaymentSystemUsageDetails(details, false));
    SystemDTO systemDTO = mapPaymentSystem(details.getPaymentSystem(), false);
    systemDTO.setUsageDetails(usageList);
    return systemDTO;
  }

  @Override
  public ItemDTO convertItemPriceDetailsToItem(ItemPriceDetails itemPriceDetails) {

    List<ItemPriceDetailsDTO> itemPriceDetailsDTOs = new ArrayList<>();
    itemPriceDetailsDTOs.add(mapItemPriceDetailsToDTO(itemPriceDetails));

    ItemDetailsDTO itemDetailsDTO = mapItemDetailsToDTO(itemPriceDetails.getItemDetails(), false);
    itemDetailsDTO.setItemPriceDetails(itemPriceDetailsDTOs);

    List<ItemDetailsDTO> itemDetailsDTOs = new ArrayList<>();
    itemDetailsDTOs.add(itemDetailsDTO);

    ItemDTO itemDTO = new ItemDTO();
    itemDTO.setItemDetails(itemDetailsDTOs);
    itemDTO.setItemId(itemPriceDetails.getItemDetails().getItem().getItemId());
    itemDTO.setItemName(itemPriceDetails.getItemDetails().getItem().getItemName());
    return itemDTO;
  }

  private ItemPriceDetailsDTO mapItemPriceDetailsToDTO(ItemPriceDetails itemPriceDetails) {
    ItemPriceDetailsDTO dto = new ItemPriceDetailsDTO();
    dto.setCapacity(itemPriceDetails.getCapacity());
    dto.setId(itemPriceDetails.getId());
    dto.setPrice(itemPriceDetails.getPrice());
    dto.setQuantity(itemPriceDetails.getQuantity());
    return dto;
  }

  private ItemDetailsDTO mapItemDetailsToDTO(ItemDetails itemDetails,
      boolean includeItemPriceDetails) {
    ItemDetailsDTO dto = new ItemDetailsDTO();
    dto.setId(itemDetails.getId());
    dto.setItemCompany(itemDetails.getItemCompany());
    if (includeItemPriceDetails)
      dto.setItemPriceDetails(mapItemPriceDetailsToDTO(itemDetails.getItemPriceDetails()));
    return dto;
  }

  private List<ItemPriceDetailsDTO> mapItemPriceDetailsToDTO(
      List<ItemPriceDetails> itemPriceDetailsList) {
    List<ItemPriceDetailsDTO> list = new ArrayList<>();
    for (ItemPriceDetails details : itemPriceDetailsList) {
      list.add(mapItemPriceDetailsToDTO(details));
    }
    return list;
  }

  /* setters for unit testing */
  public void setItemRepository(ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }

  public void setItemCompanyRepository(ItemCompanyRepository itemCompanyRepository) {
    this.itemCompanyRepository = itemCompanyRepository;
  }

  public void setItemDetailsRepository(ItemDetailsRepository itemDetailsRepository) {
    this.itemDetailsRepository = itemDetailsRepository;
  }

}
