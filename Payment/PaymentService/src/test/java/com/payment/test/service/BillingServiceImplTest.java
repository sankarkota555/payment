package com.payment.test.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.payment.domain.Bill;
import com.payment.domain.Customer;
import com.payment.domain.ItemDetails;
import com.payment.domain.ItemPriceDetails;
import com.payment.domain.SoldItem;
import com.payment.mapper.PaymentMapper;
import com.payment.repositories.BillRepository;
import com.payment.repositories.CustomerRepository;
import com.payment.repositories.ItemPriceDetailsRepositoty;
import com.payment.service.impl.BillingServiceImpl;

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

  @Test
  public void testSaveBillWithExistingItem() {
    Long itemPriceDetailsId = 123L;
    Integer itemQuantity = 20;
    Integer soldQuantity = 1;
    Integer remainingQuantity = itemQuantity - soldQuantity;

    Bill bill = new Bill();
    Customer dummyCustomer = getBummyCustomer();

    List<SoldItem> soldItems = new ArrayList<SoldItem>();
    // Create existing item
    SoldItem soldItem = new SoldItem();
    ItemPriceDetails itemPriceDetails = new ItemPriceDetails();
    itemPriceDetails.setId(itemPriceDetailsId);
    itemPriceDetails.setQuantity(itemQuantity);
    soldItem.setItemPriceDetails(itemPriceDetails);
    soldItem.setQuantity(soldQuantity);
    soldItem.setSoldPrice(120);

    // Add existing item
    soldItems.add(soldItem);
    
    bill.setCustomer(dummyCustomer);
    bill.setSoldItems(soldItems);

    // Mock method return values
    /*when(bill.getCustomer()).thenReturn(dummyCustomer);
    when(bill.getSoldItems()).thenReturn(soldItems);*/
    
   /* when(soldItem.getSoldPrice()).thenReturn(220);
    when(soldItem.getQuantity()).thenReturn(1);
    when(soldItem.getItemPriceDetails()).thenReturn(itemPriceDetails);
    
    when(itemPriceDetails.getId()).thenReturn(itemPriceDetailsId);
    when(itemPriceDetails.getQuantity()).thenReturn(20);*/
    
    when(customerRepository.findByPhone("3485694350")).thenReturn(dummyCustomer);
    when(itemPriceDetailsRepositoty.findOne(itemPriceDetailsId)).thenReturn(itemPriceDetails);
    when(billrepository.save(bill)).thenReturn(bill);

    billingServiceImpl.saveBill(bill);
    
    assertEquals("Existing customer from DB not set to bill", dummyCustomer, bill.getCustomer());
    assertEquals("Item quantity not reducing", remainingQuantity,
        bill.getSoldItems().get(0).getItemPriceDetails().getQuantity());
    
    /* Test with new customer */
    
    Customer newCustomer  = new Customer();
    newCustomer.setName("New customer");
    bill.setCustomer(newCustomer);
    when(customerRepository.findByPhone("3485694350")).thenReturn(null);
    billingServiceImpl.saveBill(bill);
    assertEquals("New customer is replaced with customer from DB", newCustomer, bill.getCustomer());
    
    /* Test with new customer */

  }
  
  @Test
  public void testSaveBillWithNewItems(){
    Bill bill = new Bill();
    Customer dummyCustomer = getBummyCustomer();
    boolean isForBill = true;
    
    List<SoldItem> soldItems = new ArrayList<SoldItem>();
    // Create New items
    SoldItem soldItem1 = new SoldItem();
    ItemPriceDetails itemPriceDetails1 = new ItemPriceDetails();
    itemPriceDetails1.setQuantity(12);
    
    soldItem1.setItemPriceDetails(itemPriceDetails1);
    soldItem1.setQuantity(1);
    soldItem1.setSoldPrice(120);
    
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
    
    //Mockito.doNothing().when(paymentMapper).findAndMapItemPricedetails(itemPriceDetails1, soldItem1.getSoldPrice(), isForBill);
    when(billrepository.save(bill)).thenReturn(bill);
    
    billingServiceImpl.saveBill(bill);
    
    verify(paymentMapper).findAndMapItemPricedetails(itemPriceDetails1, 120, isForBill);
    verify(paymentMapper).findAndMapItemPricedetails(itemPriceDetails2, 230, isForBill);
    
    /*assertEquals("Item details from mapper not set to bill", soldItem1.getSoldPrice(), bill.getSoldItems().get(0).getItemPriceDetails().getPrice());
    assertEquals("Item details from mapper not set to bill", soldItem2.getSoldPrice(), bill.getSoldItems().get(1).getItemPriceDetails().getPrice());*/
    
  }

  private Customer getBummyCustomer() {
    Customer customer = new Customer();
    customer.setAddress("Dummy address");
    customer.setEmail("dummy.email@mails.com");
    customer.setId(23423423L);
    customer.setName("Dummy name");
    customer.setPhone("3485694350");
    return customer;
  }

  private List<SoldItem> getSoldItems(int numOfItems) {
    List<SoldItem> soldItems = new ArrayList<SoldItem>();
    SoldItem soldItem;
    ItemPriceDetails itemPriceDetails;
    for (int index = 1; index <= numOfItems; index++) {
      soldItem = new SoldItem();
      itemPriceDetails = new ItemPriceDetails();
      itemPriceDetails.setId((long) (123 + index));
      soldItem.setItemPriceDetails(itemPriceDetails);
      soldItems.add(soldItem);
    }
    return soldItems;
  }

}
