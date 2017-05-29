package com.payment.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.payment.domain.Bill;
import com.payment.domain.Customer;
import com.payment.domain.Item;
import com.payment.domain.ItemCompany;
import com.payment.domain.ItemDetails;
import com.payment.domain.ItemPriceDetails;
import com.payment.domain.SoldItem;
import com.payment.dto.BillDTO;
import com.payment.mapper.PaymentMapper;
import com.payment.repositories.BillRepository;
import com.payment.repositories.CustomerRepository;
import com.payment.repositories.ItemCompanyRepository;
import com.payment.repositories.ItemDetailsRepository;
import com.payment.repositories.ItemPriceDetailsRepositoty;
import com.payment.repositories.ItemRepository;
import com.payment.service.BillingService;
import com.payment.utils.DateUtils;

@Service
public class BillingServiceImpl implements BillingService {

  private static final Logger log = LoggerFactory.getLogger(BillingServiceImpl.class);

  @Autowired
  private BillRepository billrepository;
  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private ItemDetailsRepository itemDetailsRepository;
  @Autowired
  private ItemPriceDetailsRepositoty itemPriceDetailsRepositoty;
  @Autowired
  private ItemRepository itemRepository;
  @Autowired
  private ItemCompanyRepository itemCompanyRepository;
  @Autowired
  private PaymentMapper paymentMapper;

  /**
   * saves given bill into database.
   * 
   * @param bill
   *          {@link Bill} object to save
   * @return saved bill id.
   */
  @Transactional
  public Long saveBill(Bill bill) {
    log.info("bill details");
    Customer customer = bill.getCustomer();
    log.info("cutomer details: ");
    log.info("name: " + customer.getName() + "  email:" + customer.getEmail() + " Address: "
        + customer.getAddress() + " phone:" + customer.getPhone());
    log.info("bill generated date: " + bill.getGeneratedDate());
    log.info("bill getNetAmount: " + bill.getNetAmount());

    if (bill.getGeneratedDate() == null) {
      bill.setGeneratedDate(Calendar.getInstance().getTime());
    }
    customer = customerRepository.findByPhone(bill.getCustomer().getPhone());
    ItemPriceDetails itemPriceDetails = null;
    log.info("sold items details: ");
    for (SoldItem soldItem : bill.getSoldItems()) {
      // check item is existing in DB
      if (soldItem.getItemPriceDetails().getId() != null) {
        itemPriceDetails = itemPriceDetailsRepositoty
            .findOne(soldItem.getItemPriceDetails().getId());
        // update quantity
        if (itemPriceDetails.getQuantity() != null) {
          itemPriceDetails.setQuantity(itemPriceDetails.getQuantity() - soldItem.getQuantity());
        }
        log.info("Item price details exists in DB with id: " + itemPriceDetails.getId());
        soldItem.setItemPriceDetails(itemPriceDetails);
      }
      // Item or company is new, then add them to pricedetails
      else {
        log.info("Item details not found in DB - creating new item details");
        itemPriceDetails = soldItem.getItemPriceDetails();
        // Map Item price details.
        paymentMapper.findAndMapItemPricedetails(itemPriceDetails, soldItem.getSoldPrice());
        // set item price details sold item
        soldItem.setItemPriceDetails(itemPriceDetails);
      }
    }
    if (customer != null) {
      bill.setCustomer(customer);
    }
    log.info("NOT saving bill, uncomment to save");
    bill = billrepository.save(bill);
    log.info("bill saved success fully with id:{} ", bill.getBillId());
    return bill.getBillId();

  }

  @Transactional
  public List<Bill> getCustomerBills(Long customerId) {
    Customer customer = customerRepository.findOne(customerId);
    return customer.getBills();
  }

  @Transactional
  public Bill getBillById(long billId) {
    return billrepository.findOne(billId);
  }

  @Transactional(readOnly = true)
  public List<BillDTO> getBillsBetweebDates(Date frmDate, Date toDate) {
    frmDate = DateUtils.removeTime(frmDate);
    if (toDate == null) {
      toDate = DateUtils.getCurrentdate();
    }
    log.info("get bills between dates, from date:{}, to date: {}", frmDate, toDate);
    List<Bill> bills = billrepository.getBillsBetweenDates(frmDate, toDate);
    log.info("Number if bills: " + bills.size());

    return paymentMapper.mapBillToDto(bills);
  }

}
