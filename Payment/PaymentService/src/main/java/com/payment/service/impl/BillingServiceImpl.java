package com.payment.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payment.domain.Bill;
import com.payment.domain.Customer;
import com.payment.domain.Item;
import com.payment.domain.ItemDetails;
import com.payment.repositories.BillRepository;
import com.payment.service.BillingService;

@Service
public class BillingServiceImpl implements BillingService {

  private static Logger log = Logger.getLogger(BillingServiceImpl.class);

  @Autowired
  private BillRepository billrepository;

  @Override
  @Transactional
  public void saveBill(Bill bill) {
    log.info("bill details");
    Customer customer = bill.getCustomer();
    log.info("cutomer details: ");
    log.info("name: " + customer.getName() + "  email:" + customer.getEmail() + " Address: "
        + customer.getAddress() + " phone:" + customer.getPhone());

    log.info("bill generated date: " + bill.getGeneratedDate());
    log.info("bill getNetAmount: " + bill.getNetAmount());
    List<Item> items = bill.getItems();
    for (Item item : items) {
      log.info("Item details: ");
      log.info("getItemId: " + item.getItemId() + "  getItemName" + item.getItemName());
      List<ItemDetails> details = item.getItemDetails();
      for (ItemDetails itemDetails : details) {
        log.info(
            "  getCapacity: " + itemDetails.getCapacity() + " getPrice:" + itemDetails.getPrice()
                + "  company name: " + itemDetails.getItemCompany().getCompanyName());
      }
    }
    bill = billrepository.save(bill);
    log.info("bill saved success fully");

  }

}
