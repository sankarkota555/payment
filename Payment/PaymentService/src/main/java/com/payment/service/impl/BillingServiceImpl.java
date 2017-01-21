package com.payment.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payment.domain.Bill;
import com.payment.domain.Customer;
import com.payment.domain.SoldItem;
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
    log.info("sold items details: ");
    List<SoldItem> items = bill.getSoldItems();
    for (SoldItem item : items) {
      log.info("item name: "+ item.getItemDetails().getItem().getItemName());
      log.info("sold quantity: " + item.getQuantity() + "  sold price: " + item.getSoldPrice());

      log.info("  getCapacity: " + item.getItemDetails().getCapacity() + "  company name: "
          + item.getItemDetails().getItemCompany().getCompanyName());

    }
    bill = billrepository.save(bill);
    log.info("bill saved success fully");

  }

}
