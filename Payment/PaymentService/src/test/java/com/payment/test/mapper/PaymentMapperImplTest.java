package com.payment.test.mapper;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.aspectj.asm.IRelationshipMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.payment.domain.Bill;
import com.payment.domain.Customer;
import com.payment.domain.Item;
import com.payment.domain.ItemCompany;
import com.payment.domain.ItemDetails;
import com.payment.domain.ItemPriceDetails;
import com.payment.domain.PaymentSystem;
import com.payment.domain.PaymentSystemUsageDetails;
import com.payment.domain.SoldItem;
import com.payment.dto.CustomerDTO;
import com.payment.dto.ItemDTO;
import com.payment.dto.ItemDetailsDTO;
import com.payment.dto.ItemPriceDetailsDTO;
import com.payment.dto.SystemDTO;
import com.payment.dto.SystemUsageDTO;
import com.payment.mapper.PaymentMapperImpl;
import com.payment.repositories.ItemCompanyRepository;
import com.payment.repositories.ItemDetailsRepository;
import com.payment.repositories.ItemRepository;
import com.payment.utils.DateUtils;

public class PaymentMapperImplTest {

  private PaymentMapperImpl paymentMapperImpl;

  @Mock
  private ItemRepository itemRepository;

  @Mock
  private ItemDetailsRepository itemDetailsRepository;

  @Mock
  private ItemCompanyRepository itemCompanyRepository;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    paymentMapperImpl = new PaymentMapperImpl();
    paymentMapperImpl.setItemRepository(itemRepository);
    paymentMapperImpl.setItemDetailsRepository(itemDetailsRepository);
    paymentMapperImpl.setItemCompanyRepository(itemCompanyRepository);
  }

  /**
   * Verify mapping item price details with existing company and existing item for bill.
   */
  @Test
  public void testMappingItemPriceDetailsWithExistingDetailsForBill() {
    Integer soldPrice = 300;
    ItemPriceDetails itemPriceDetails = getItemPriceDetails(null);
    Item item = itemPriceDetails.getItemDetails().getItem();
    ItemCompany itemCompany = itemPriceDetails.getItemDetails().getItemCompany();

    Item dummyItem = getDummyItem();
    ItemCompany dummyCompany = getDummyItemCompanty();

    when(itemRepository.findByItemNameIgnoreCase(item.getItemName())).thenReturn(dummyItem);
    when(itemCompanyRepository.findByCompanyNameIgnoreCase(itemCompany.getCompanyName()))
        .thenReturn(dummyCompany);

    paymentMapperImpl.findAndMapItemPricedetails(itemPriceDetails, soldPrice, true);

    verify(itemRepository).findByItemNameIgnoreCase(item.getItemName());
    verify(itemCompanyRepository).findByCompanyNameIgnoreCase(itemCompany.getCompanyName());
    verify(itemDetailsRepository, never()).findOne(Matchers.anyLong());

    assertEquals("Item is not mapped correctly", dummyItem,
        itemPriceDetails.getItemDetails().getItem());
    assertEquals("Item comapny is not mapped correctly", dummyCompany,
        itemPriceDetails.getItemDetails().getItemCompany());
    assertEquals("Price is mapped incorrect", soldPrice, itemPriceDetails.getPrice());
    assertEquals("Qunatity should not map for bill generation", null,
        itemPriceDetails.getQuantity());

  }

  /**
   * Verify mapping item price details with existing company and new item for bill.
   */
  @Test
  public void testMappingItemPriceDetailsWithExistingCompanyForBill() {
    Integer soldPrice = 300;
    ItemPriceDetails itemPriceDetails = getItemPriceDetails(null);
    Item item = itemPriceDetails.getItemDetails().getItem();
    ItemCompany itemCompany = itemPriceDetails.getItemDetails().getItemCompany();

    ItemCompany dummyCompany = getDummyItemCompanty();

    when(itemRepository.findByItemNameIgnoreCase(item.getItemName())).thenReturn(null);
    when(itemCompanyRepository.findByCompanyNameIgnoreCase(itemCompany.getCompanyName()))
        .thenReturn(dummyCompany);

    paymentMapperImpl.findAndMapItemPricedetails(itemPriceDetails, soldPrice, true);

    verify(itemRepository).findByItemNameIgnoreCase(item.getItemName());
    verify(itemCompanyRepository).findByCompanyNameIgnoreCase(itemCompany.getCompanyName());
    verify(itemDetailsRepository, never()).findOne(Matchers.anyLong());

    assertEquals("New Item name should same", item.getItemName(),
        itemPriceDetails.getItemDetails().getItem().getItemName());
    assertEquals("New Item Id should be null", null,
        itemPriceDetails.getItemDetails().getItem().getItemId());
    assertEquals("Item comapny is not mapped correctly", dummyCompany,
        itemPriceDetails.getItemDetails().getItemCompany());
    assertEquals("Price is mapped incorrect", soldPrice, itemPriceDetails.getPrice());
    assertEquals("Qunatity should not map for bill generation", null,
        itemPriceDetails.getQuantity());

  }

  /**
   * Verify mapping item price details with new company and existing item for bill.
   */
  @Test
  public void testMappingItemPriceDetailsWithExistingItemForBill() {
    Integer soldPrice = 300;
    ItemPriceDetails itemPriceDetails = getItemPriceDetails(null);
    Item item = itemPriceDetails.getItemDetails().getItem();
    ItemCompany itemCompany = itemPriceDetails.getItemDetails().getItemCompany();
    Item dummyItem = getDummyItem();

    when(itemRepository.findByItemNameIgnoreCase(item.getItemName())).thenReturn(dummyItem);
    when(itemCompanyRepository.findByCompanyNameIgnoreCase(itemCompany.getCompanyName()))
        .thenReturn(null);

    paymentMapperImpl.findAndMapItemPricedetails(itemPriceDetails, soldPrice, true);

    verify(itemRepository).findByItemNameIgnoreCase(item.getItemName());
    verify(itemCompanyRepository).findByCompanyNameIgnoreCase(itemCompany.getCompanyName());
    verify(itemDetailsRepository, never()).findOne(Matchers.anyLong());

    assertEquals("New Item company name should same", itemCompany.getCompanyName(),
        itemPriceDetails.getItemDetails().getItemCompany().getCompanyName());
    assertEquals("New Item company Id should be null", null,
        itemPriceDetails.getItemDetails().getItemCompany().getCompanyId());

    assertEquals("Item is not mapped correctly", dummyItem,
        itemPriceDetails.getItemDetails().getItem());
    assertEquals("Price is mapped incorrect", soldPrice, itemPriceDetails.getPrice());
    assertEquals("Qunatity should not map for bill generation", null,
        itemPriceDetails.getQuantity());

  }

  /**
   * Verify mapping with item price details with existing for bill.
   */
  @Test
  public void testMappingItemPriceDetailsWithExistingForBill() {
    Integer soldPrice = 300;
    Long detailsId = 23677327L;
    ItemPriceDetails itemPriceDetails = getItemPriceDetails(detailsId);
    ItemDetails dummyDetails = getDummyItemDetails();
    when(itemDetailsRepository.findOne(itemPriceDetails.getItemDetails().getId()))
        .thenReturn(dummyDetails);

    paymentMapperImpl.findAndMapItemPricedetails(itemPriceDetails, soldPrice, true);

    verify(itemRepository, never()).findByItemNameIgnoreCase(Matchers.anyString());
    verify(itemCompanyRepository, never()).findByCompanyNameIgnoreCase(Matchers.anyString());
    verify(itemDetailsRepository).findOne(detailsId);

    assertEquals("Item details from db is not mapped", dummyDetails,
        itemPriceDetails.getItemDetails());

    assertEquals("Price is mapped incorrect", soldPrice, itemPriceDetails.getPrice());
    assertEquals("Qunatity should not map for bill generation", null,
        itemPriceDetails.getQuantity());

  }

  /**
   * Verify mapping with item price details with new company and new item for bill.
   */
  @Test
  public void testMappingItemPriceDetailsWithNewDetailsForBill() {
    Integer soldPrice = 300;
    ItemPriceDetails itemPriceDetails = getItemPriceDetails(null);
    Item item = itemPriceDetails.getItemDetails().getItem();
    ItemCompany itemCompany = itemPriceDetails.getItemDetails().getItemCompany();

    when(itemRepository.findByItemNameIgnoreCase(item.getItemName())).thenReturn(null);
    when(itemCompanyRepository.findByCompanyNameIgnoreCase(itemCompany.getCompanyName()))
        .thenReturn(null);

    paymentMapperImpl.findAndMapItemPricedetails(itemPriceDetails, soldPrice, true);

    verify(itemRepository).findByItemNameIgnoreCase(item.getItemName());
    verify(itemCompanyRepository).findByCompanyNameIgnoreCase(itemCompany.getCompanyName());
    verify(itemDetailsRepository, never()).findOne(Matchers.anyLong());

    assertEquals("New Item company name should same", itemCompany.getCompanyName(),
        itemPriceDetails.getItemDetails().getItemCompany().getCompanyName());
    assertEquals("New Item company Id should be null", null,
        itemPriceDetails.getItemDetails().getItemCompany().getCompanyId());
    assertEquals("New Item company name should same", itemCompany.getCompanyName(),
        itemPriceDetails.getItemDetails().getItemCompany().getCompanyName());
    assertEquals("New Item company Id should be null", null,
        itemPriceDetails.getItemDetails().getItemCompany().getCompanyId());
    assertEquals("Price is mapped incorrect", soldPrice, itemPriceDetails.getPrice());
    assertEquals("Qunatity should not map for bill generation", null,
        itemPriceDetails.getQuantity());

  }

  /**
   * Verify mapping item price details with existing company and existing item for saving.
   */
  @Test
  public void testMappingItemPriceDetailsWithExistingDetailsForSave() {
    Integer soldPrice = 300;
    ItemPriceDetails itemPriceDetails = getItemPriceDetails(null);
    Integer quantity = itemPriceDetails.getQuantity();
    Item item = itemPriceDetails.getItemDetails().getItem();
    ItemCompany itemCompany = itemPriceDetails.getItemDetails().getItemCompany();

    Item dummyItem = getDummyItem();
    ItemCompany dummyCompany = getDummyItemCompanty();

    when(itemRepository.findByItemNameIgnoreCase(item.getItemName())).thenReturn(dummyItem);
    when(itemCompanyRepository.findByCompanyNameIgnoreCase(itemCompany.getCompanyName()))
        .thenReturn(dummyCompany);

    paymentMapperImpl.findAndMapItemPricedetails(itemPriceDetails, soldPrice, false);

    verify(itemRepository).findByItemNameIgnoreCase(item.getItemName());
    verify(itemCompanyRepository).findByCompanyNameIgnoreCase(itemCompany.getCompanyName());
    verify(itemDetailsRepository, never()).findOne(Matchers.anyLong());

    assertEquals("Item is not mapped correctly", dummyItem,
        itemPriceDetails.getItemDetails().getItem());
    assertEquals("Item comapny is not mapped correctly", dummyCompany,
        itemPriceDetails.getItemDetails().getItemCompany());
    assertEquals("Price is mapped incorrect", soldPrice, itemPriceDetails.getPrice());
    assertEquals("Qunatity should  map for save operation", quantity,
        itemPriceDetails.getQuantity());

  }

  /**
   * Verify mapping with item price details and new company and new item for save operation.
   */
  @Test
  public void testMappingItemPriceDetailsWithNewDetailsForSave() {
    Integer soldPrice = 300;
    ItemPriceDetails itemPriceDetails = getItemPriceDetails(null);
    Integer quantity = itemPriceDetails.getQuantity();
    Item item = itemPriceDetails.getItemDetails().getItem();
    ItemCompany itemCompany = itemPriceDetails.getItemDetails().getItemCompany();

    when(itemRepository.findByItemNameIgnoreCase(item.getItemName())).thenReturn(null);
    when(itemCompanyRepository.findByCompanyNameIgnoreCase(itemCompany.getCompanyName()))
        .thenReturn(null);

    paymentMapperImpl.findAndMapItemPricedetails(itemPriceDetails, soldPrice, false);

    verify(itemRepository).findByItemNameIgnoreCase(item.getItemName());
    verify(itemCompanyRepository).findByCompanyNameIgnoreCase(itemCompany.getCompanyName());
    verify(itemDetailsRepository, never()).findOne(Matchers.anyLong());

    assertEquals("New Item company name should same", itemCompany.getCompanyName(),
        itemPriceDetails.getItemDetails().getItemCompany().getCompanyName());
    assertEquals("New Item company Id should be null", null,
        itemPriceDetails.getItemDetails().getItemCompany().getCompanyId());
    assertEquals("New Item company name should same", itemCompany.getCompanyName(),
        itemPriceDetails.getItemDetails().getItemCompany().getCompanyName());
    assertEquals("New Item company Id should be null", null,
        itemPriceDetails.getItemDetails().getItemCompany().getCompanyId());
    assertEquals("Price is mapped incorrect", soldPrice, itemPriceDetails.getPrice());
    assertEquals("Qunatity should map for save operation", quantity,
        itemPriceDetails.getQuantity());

  }

  @Test
  public void testMapCustomer() {
    Customer customer = new Customer();
    customer.setAddress("Address of cus");
    customer.setEmail("cus email");
    customer.setId(27287L);
    customer.setName("Customer name");
    customer.setPhone("893409234");

    Bill bill = new Bill();
    bill.setBillId(123232L);
    bill.setGeneratedDate(DateUtils.getCurrentTimeStamp());
    bill.setNetAmount(23434L);
    bill.setSoldItems(new ArrayList<SoldItem>());

    List<Bill> bills = new ArrayList<>();
    bills.add(bill);

    customer.setBills(bills);

    CustomerDTO customerDTO = paymentMapperImpl.mapCustomer(customer, false);

    assertEquals("Customer name mapped incorrect", customer.getName(), customerDTO.getName());
    assertEquals("Customer phone mapped incorrect", customer.getPhone(), customerDTO.getPhone());
    assertEquals("Customer ID mapped incorrect", customer.getId(), customerDTO.getId());
    assertEquals("Customer email mapped incorrect", customer.getEmail(), customerDTO.getEmail());
    assertEquals("Customer address mapped incorrect", customer.getAddress(),
        customerDTO.getAddress());
    assertEquals("Customer bills should not map", null, customerDTO.getBills());

    /* Test mapping customer with bills */
    bill.setCustomer(customer);
    customerDTO = paymentMapperImpl.mapCustomer(customer, true);
    assertEquals("Customer bills should map", bill.getBillId(),
        customerDTO.getBills().get(0).getBillId());

  }

  @Test
  public void testConvertSystemUsageToSystem() {
    String cusName = "Customer name";
    String sysName = "Sys name";
    Long sysId = 3223L;
    Float hours = 1.0f;
    Long detailsId = 12L;
    Date loginTime = new Date();

    PaymentSystem paymentSystem = new PaymentSystem();
    paymentSystem.setId(sysId);
    paymentSystem.setSystemName(sysName);

    PaymentSystemUsageDetails usageDetails = new PaymentSystemUsageDetails();
    usageDetails.setCutomerName(cusName);
    usageDetails.setHours(hours);
    usageDetails.setId(detailsId);
    usageDetails.setLoginTime(loginTime);
    usageDetails.setPaymentSystem(paymentSystem);

    SystemDTO systemDTO = paymentMapperImpl.convertSystemUsageToSystem(usageDetails);

    SystemUsageDTO usageDTO = systemDTO.getUsageDetails().get(0);

    assertEquals("System ID mapped incorrect", sysId, systemDTO.getId());
    assertEquals("System name mapped incorrect", sysName, systemDTO.getSystemName());
    assertEquals("SystemUsageDTO list size should be 1", 1, systemDTO.getUsageDetails().size());

    assertEquals("Customer name mapped incorrect", cusName, usageDTO.getCutomerName());
    assertEquals("Hours mapped incorrect", hours, usageDTO.getHours());
    assertEquals("detailsId  detailsId mapped incorrect", detailsId, usageDTO.getId());
    assertEquals("detailsId  detailsId mapped incorrect", loginTime, usageDTO.getLoginTime());
    assertEquals("detailsId  detailsId mapped incorrect", null, usageDTO.getPaymentSystem());

  }

  @Test
  public void testConvertItemPriceDetailsToItem() {
    String capacity = "MM";
    Integer price = 100;
    Long detailsId = 12L;
    Long priceDetailsId = 156L;
    Integer quantity = 10;

    Item item = getDummyItem();
    ItemCompany itemCompany = getDummyItemCompanty();

    ItemDetails itemDetails = new ItemDetails();
    itemDetails.setId(detailsId);
    itemDetails.setItem(item);
    itemDetails.setItemCompany(itemCompany);

    ItemPriceDetails itemPriceDetails = new ItemPriceDetails();
    itemPriceDetails.setCapacity(capacity);
    itemPriceDetails.setId(priceDetailsId);
    itemPriceDetails.setPrice(price);
    itemPriceDetails.setQuantity(quantity);
    itemPriceDetails.setItemDetails(itemDetails);

    ItemDTO itemDTO = paymentMapperImpl.convertItemPriceDetailsToItem(itemPriceDetails);
    ItemDetailsDTO itemDetailsDTO = itemDTO.getItemDetails().get(0);
    ItemPriceDetailsDTO itemPriceDetailsDTO = itemDetailsDTO.getItemPriceDetails().get(0);

    assertEquals("Item ID mapped incorrect", item.getItemId(), itemDTO.getItemId());
    assertEquals("Item name mapped incorrect", item.getItemName(), itemDTO.getItemName());
    assertEquals("Item details list size should be 1", 1, itemDTO.getItemDetails().size());

    assertEquals("Item details Id  mapped incorrect", detailsId, itemDetailsDTO.getId());
    assertEquals("itemCompany mapped incorrect", itemCompany, itemDetailsDTO.getItemCompany());

    assertEquals("Item price  detailsId should be 1", 1,
        itemDetailsDTO.getItemPriceDetails().size());

    assertEquals("capacity mapped incorrect", capacity, itemPriceDetailsDTO.getCapacity());
    assertEquals("priceDetailsId mapped incorrect", priceDetailsId, itemPriceDetailsDTO.getId());
    assertEquals("price mapped incorrect", price, itemPriceDetailsDTO.getPrice());
    assertEquals("quantity mapped incorrect", quantity, itemPriceDetailsDTO.getQuantity());

  }

  private Item getDummyItem() {
    Item item = new Item();
    item.setItemId(458995L);
    item.setItemName("Dummy Item - one");
    return item;
  }

  private ItemCompany getDummyItemCompanty() {
    ItemCompany itemCompany = new ItemCompany();
    itemCompany.setCompanyId(12345L);
    itemCompany.setCompanyName("Dummy Company - one");
    return itemCompany;
  }

  private ItemDetails getDummyItemDetails() {
    ItemDetails itemDetails = new ItemDetails();
    itemDetails.setId(12345L);
    itemDetails.setItem(getDummyItem());
    itemDetails.setItemCompany(getDummyItemCompanty());
    return itemDetails;
  }

  private ItemPriceDetails getItemPriceDetails(Long itemDetailsId) {
    Item item = new Item();
    item.setItemName("Item - one");

    ItemCompany itemCompany = new ItemCompany();
    itemCompany.setCompanyName("Company - one");

    ItemDetails itemDetails = new ItemDetails();
    itemDetails.setItem(item);
    itemDetails.setId(itemDetailsId);
    itemDetails.setItemCompany(itemCompany);
    ItemPriceDetails itemPriceDetails = new ItemPriceDetails();
    itemPriceDetails.setItemDetails(itemDetails);
    itemPriceDetails.setCapacity("Cap");
    itemPriceDetails.setPrice(200);
    itemPriceDetails.setQuantity(2);

    return itemPriceDetails;
  }
}
