package com.payment.test.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.payment.domain.Bill;
import com.payment.domain.Customer;
import com.payment.domain.ItemPriceDetails;
import com.payment.domain.SoldItem;
import com.payment.mapper.PaymentMapper;
import com.payment.repositories.BillRepository;
import com.payment.repositories.CustomerRepository;
import com.payment.repositories.ItemPriceDetailsRepositoty;
import com.payment.service.impl.BillingServiceImpl;
import com.payment.utils.DateUtils;

@RunWith(PowerMockRunner.class)
public class BillingServiceImplTest {

  private BillingServiceImpl billingServiceImpl;

  @Mock
  private BillRepository billrepository;

  @Mock
  private CustomerRepository customerRepository;

  @Mock
  private ItemPriceDetailsRepositoty itemPriceDetailsRepositoty;

  @Mock
  private PaymentMapper paymentMapper;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    billingServiceImpl = new BillingServiceImpl();
    billingServiceImpl.setCustomerRepository(customerRepository);
    billingServiceImpl.setBillrepository(billrepository);
    billingServiceImpl.setItemPriceDetailsRepositoty(itemPriceDetailsRepositoty);
    billingServiceImpl.setPaymentMapper(paymentMapper);
  }

  /**
   * Test save bill with existing Item and existing customer with phone.
   */
  @Test
  public void testSaveBillWithExistingItemAndExistingCustomerByPhone() {
    int quantityBeforeSave = 0;
    Bill bill = getPreparedBill();
    Customer dummyCustomer = bill.getCustomer();
    Date billDate = bill.getGeneratedDate();

    /* ------- Test with Existing customer by phone ----------- */
    quantityBeforeSave = bill.getSoldItems().get(0).getItemPriceDetails().getQuantity();
    billingServiceImpl.saveBill(bill);

    assertEquals("Existing customer from DB with Phone is not set to bill", dummyCustomer,
        bill.getCustomer());
    assertEquals("Item quantity not reducing", new Integer(quantityBeforeSave - 1),
        bill.getSoldItems().get(0).getItemPriceDetails().getQuantity());
    assertEquals("Bill generated date is incorrect", billDate, bill.getGeneratedDate());

    verify(customerRepository).findByPhone(dummyCustomer.getPhone());
    verify(customerRepository, never()).findByNameIgnoreCase(Matchers.anyString());

  }

  /**
   * Test save bill with existing Item and new customer with phone.
   */
  @Test
  public void testSaveBillWithExistingItemAndNewCustomerByPhone() {

    int quantityBeforeSave = 0;
    Bill bill = getPreparedBill();
    Date billDate = bill.getGeneratedDate();

    /* ------- Test with new customer by phone ----------- */
    Customer newCustomer = new Customer();
    newCustomer.setPhone("46387344638764");
    newCustomer.setName("New customer");
    bill.setCustomer(newCustomer);
    when(customerRepository.findByPhone(newCustomer.getPhone())).thenReturn(null);

    quantityBeforeSave = bill.getSoldItems().get(0).getItemPriceDetails().getQuantity();
    billingServiceImpl.saveBill(bill);

    assertEquals("New customer with Phone is not set to bill", newCustomer, bill.getCustomer());
    assertEquals("Item quantity not reducing", new Integer(quantityBeforeSave - 1),
        bill.getSoldItems().get(0).getItemPriceDetails().getQuantity());
    assertEquals("Bill generated date is incorrect", billDate, bill.getGeneratedDate());

    verify(customerRepository).findByPhone(newCustomer.getPhone());
    verify(customerRepository, never()).findByNameIgnoreCase(Matchers.anyString());
  }

  /**
   * Test save bill with existing Item and existing customer with name.
   */
  @Test
  public void testSaveBillWithExistingItemAndExistingCustomerByname() {

    int quantityBeforeSave = 0;
    Bill bill = getPreparedBill();
    Customer dummyCustomer = bill.getCustomer();
    Date billDate = bill.getGeneratedDate();

    /* ------- Test with Existing customer by Name ----------- */

    // remove phone number form customer.
    dummyCustomer.setPhone("");

    when(customerRepository.findByNameIgnoreCase(dummyCustomer.getName()))
        .thenReturn(dummyCustomer);

    quantityBeforeSave = bill.getSoldItems().get(0).getItemPriceDetails().getQuantity();
    billingServiceImpl.saveBill(bill);

    assertEquals("Existing customer from DB with name is not set to bill", dummyCustomer,
        bill.getCustomer());
    assertEquals("Item quantity not reducing", new Integer(quantityBeforeSave - 1),
        bill.getSoldItems().get(0).getItemPriceDetails().getQuantity());
    assertEquals("Bill generated date is incorrect", billDate, bill.getGeneratedDate());

    verify(customerRepository).findByNameIgnoreCase(dummyCustomer.getName());
    verify(customerRepository, never()).findByPhone(Matchers.anyString());
  }

  /**
   * Test save bill with existing Item and new customer with name.
   */
  @Test
  public void testSaveBillWithExistingItemAndNewCustomerByname() {

    int quantityBeforeSave = 0;
    Bill bill = getPreparedBill();
    Date billDate = bill.getGeneratedDate();

    /* ------- Test with new customer by Name ----------- */
    Customer newCustomer = new Customer();
    newCustomer.setPhone("");
    newCustomer.setName("New customer");

    // Set new customer to bill
    bill.setCustomer(newCustomer);
    when(customerRepository.findByNameIgnoreCase(newCustomer.getName())).thenReturn(null);
    quantityBeforeSave = bill.getSoldItems().get(0).getItemPriceDetails().getQuantity();
    billingServiceImpl.saveBill(bill);

    assertEquals("New customer with name is not set to bill", newCustomer, bill.getCustomer());
    assertEquals("Item quantity not reducing", new Integer(quantityBeforeSave - 1),
        bill.getSoldItems().get(0).getItemPriceDetails().getQuantity());
    assertEquals("Bill generated date is incorrect", billDate, bill.getGeneratedDate());

    verify(customerRepository).findByNameIgnoreCase(newCustomer.getName());
    verify(customerRepository, never()).findByPhone(Matchers.anyString());
  }

  @Test
  public void testSaveBillWithNewItems() {
    Bill bill = new Bill();
    Customer dummyCustomer = getDummyCustomer();
    boolean isForBill = true;

    List<SoldItem> soldItems = new ArrayList<SoldItem>();
    // Create New items
    SoldItem soldItem1 = new SoldItem();
    soldItem1.setQuantity(1);
    soldItem1.setSoldPrice(120);
    ItemPriceDetails itemPriceDetails1 = new ItemPriceDetails();
    itemPriceDetails1.setQuantity(12);
    soldItem1.setItemPriceDetails(itemPriceDetails1);

    SoldItem soldItem2 = new SoldItem();
    soldItem2.setQuantity(12);
    soldItem2.setSoldPrice(230);
    ItemPriceDetails itemPriceDetails2 = new ItemPriceDetails();
    itemPriceDetails2.setQuantity(15);
    soldItem2.setItemPriceDetails(itemPriceDetails2);

    soldItems.add(soldItem1);
    soldItems.add(soldItem2);

    bill.setSoldItems(soldItems);
    bill.setCustomer(dummyCustomer);

    when(billrepository.save(bill)).thenReturn(bill);

    billingServiceImpl.saveBill(bill);
    
    verify(paymentMapper).findAndMapItemPricedetails(itemPriceDetails1, soldItem1.getSoldPrice(),
        isForBill);
    verify(paymentMapper).findAndMapItemPricedetails(itemPriceDetails2, soldItem2.getSoldPrice(),
        isForBill);

  }

  @Test
  @PrepareForTest({DateUtils.class })
  public void testSaveBillWithDefaultBillDate() {
    Bill bill = new Bill();
    Customer customer = new Customer();
    customer.setName("cus name");
    customer.setAddress("Address of cus");

    bill.setCustomer(customer);
    bill.setSoldItems(new ArrayList<>());
    bill.setGeneratedDate(null);

    Date billGeneratedDate = DateUtils.getCurrentTimeStamp();

    // Mock static method of DateUtils
    PowerMockito.mockStatic(DateUtils.class);
    when(DateUtils.getCurrentTimeStamp()).thenReturn(billGeneratedDate);

    when(customerRepository.findByNameIgnoreCase(customer.getName()))
        .thenReturn(getDummyCustomer());
    when(billrepository.save(bill)).thenReturn(bill);

    billingServiceImpl.saveBill(bill);
    
    // Verify bill date is set to current time.
    assertEquals("Default bill generation date is incorrect", billGeneratedDate,
        bill.getGeneratedDate());

  }

  /**
   * Verify get bill between dates.
   */
  @Test
  public void testGetBillsBetweebDates() {
    Date fromDate = DateUtils.getCurrentTimeStamp();
    Date toDate = DateUtils.getCurrentTimeStamp();
    billingServiceImpl.getBillsBetweebDates(fromDate, toDate);
    verify(billrepository).findByGeneratedDateBetween(DateUtils.removeTime(fromDate), toDate);
  }

  /**
   * Verify get bill with todate as null value.
   */
  @Test
  @PrepareForTest({ DateUtils.class })
  public void testGetBillsBetweebDatesWithToDateNull() {
    Date fromDate = DateUtils.getCurrentTimeStamp();

    Date toDate = DateUtils.getCurrentTimeStamp();
    // Mock static method of DateUtils
    PowerMockito.mockStatic(DateUtils.class);
    when(DateUtils.getCurrentTimeStamp()).thenReturn(toDate);

    billingServiceImpl.getBillsBetweebDates(fromDate, null);
    verify(billrepository).findByGeneratedDateBetween(DateUtils.removeTime(fromDate), toDate);

  }

  @Test
  public void testGetBillById() {
    long billId = 1l;
    billingServiceImpl.getBillById(billId);
    verify(billrepository).findOne(billId);
  }

  @Test
  public void testGetCustomerBills() {
    long customerId = 1l;
    Customer customer = mock(Customer.class);
    List<Bill> bills = new ArrayList<Bill>();
    when(customerRepository.findOne(customerId)).thenReturn(customer);
    when(customer.getBills()).thenReturn(bills);
    List<Bill> foundBills = billingServiceImpl.getCustomerBills(customerId);

    verify(customerRepository).findOne(customerId);
    verify(customer).getBills();

    assertEquals("Customer bills returned incorrect", bills, foundBills);
  }

  private Customer getDummyCustomer() {
    Customer customer = new Customer();
    customer.setAddress("Dummy address");
    customer.setEmail("dummy.email@mails.com");
    customer.setId(23423423L);
    customer.setName("Dummy name");
    customer.setPhone("3485694350");
    return customer;
  }

  private Bill getPreparedBill() {
    Long itemPriceDetailsId = 123L;

    Bill bill = new Bill();
    Customer dummyCustomer = getDummyCustomer();

    List<SoldItem> soldItems = new ArrayList<SoldItem>();
    // Create existing item
    SoldItem soldItem = new SoldItem();
    ItemPriceDetails itemPriceDetails = new ItemPriceDetails();
    itemPriceDetails.setId(itemPriceDetailsId);
    itemPriceDetails.setQuantity(20);

    soldItem.setItemPriceDetails(itemPriceDetails);
    soldItem.setQuantity(1);
    soldItem.setSoldPrice(120);

    // Add existing item
    soldItems.add(soldItem);

    bill.setCustomer(dummyCustomer);
    bill.setSoldItems(soldItems);
    bill.setGeneratedDate(DateUtils.getCurrentTimeStamp());

    when(customerRepository.findByPhone(bill.getCustomer().getPhone())).thenReturn(dummyCustomer);
    when(itemPriceDetailsRepositoty
        .findOne(bill.getSoldItems().get(0).getItemPriceDetails().getId()))
            .thenReturn(bill.getSoldItems().get(0).getItemPriceDetails());
    when(billrepository.save(bill)).thenReturn(bill);

    return bill;
  }

}
