package com.payment.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.payment.domain.Bill;
import com.payment.domain.Customer;
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
  public Long saveBill(final Bill bill) {
    log.info("bill details");
    Customer customer = bill.getCustomer();
    log.info("cutomer details: {}", customer);
    log.info("bill generated date: " + bill.getGeneratedDate());
    log.info("bill getNetAmount: " + bill.getNetAmount());

    if (bill.getGeneratedDate() == null) {
      bill.setGeneratedDate(DateUtils.getCurrentTimeStamp());
    }
    if (customer.getPhone() == null || customer.getPhone().isEmpty()) {
      customer = customerRepository.findByNameIgnoreCase(customer.getName());
    } else {
      customer = customerRepository.findByPhone(customer.getPhone());
    }
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
        // mapping is for bill generation, so send true.
        boolean isForBill = true;
        paymentMapper.findAndMapItemPricedetails(itemPriceDetails, soldItem.getSoldPrice(),
            isForBill);
        // set item price details sold item
        soldItem.setItemPriceDetails(itemPriceDetails);
      }
    }
    if (customer != null) {
      log.info("Setting customer from DB to bill: {}", customer);
      bill.setCustomer(customer);
    }
    Bill savedBill = billrepository.save(bill);
    log.info("bill saved success fully with id:{} ", savedBill.getBillId());
    return savedBill.getBillId();

  }

  public List<Bill> getCustomerBills(Long customerId) {
    Customer customer = customerRepository.findOne(customerId);
    return customer.getBills();
  }

  public Bill getBillById(long billId) {
    return billrepository.findOne(billId);
  }

  public List<BillDTO> getBillsBetweebDates(final Date frmDate,final Date toDate) {
    Date fromDateValue = DateUtils.removeTime(frmDate);
    Date toDateValue = toDate;
    if (toDateValue == null) {
      toDateValue = DateUtils.getCurrentTimeStamp();
    }
    log.info("Getting bills between dates, from date:{}, to date: {}", fromDateValue, toDateValue);
    List<Bill> bills = billrepository.findByGeneratedDateBetween(fromDateValue, toDateValue);
    log.info("Number of bills found between {} and {} is :{}", fromDateValue, toDateValue, bills.size());

    return paymentMapper.mapBillToDto(bills);
  }

  /* Setters to set Mock objects */

  public void setBillrepository(BillRepository billrepository) {
    this.billrepository = billrepository;
  }

  public void setCustomerRepository(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public void setItemDetailsRepository(ItemDetailsRepository itemDetailsRepository) {
    this.itemDetailsRepository = itemDetailsRepository;
  }

  public void setItemPriceDetailsRepositoty(ItemPriceDetailsRepositoty itemPriceDetailsRepositoty) {
    this.itemPriceDetailsRepositoty = itemPriceDetailsRepositoty;
  }

  public void setItemRepository(ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }

  public void setItemCompanyRepository(ItemCompanyRepository itemCompanyRepository) {
    this.itemCompanyRepository = itemCompanyRepository;
  }

  public void setPaymentMapper(PaymentMapper paymentMapper) {
    this.paymentMapper = paymentMapper;
  }

}
