package com.payment.service;

import java.util.Date;
import java.util.List;

import com.payment.domain.Bill;

public interface BillingService {
  
  /**
   * saves given bill into database.
   * @param {@link Bill} object to save
   * @return saved bill id.
   */
  public Long saveBill(Bill bill);
  
  /**
   * Gives customer bills as list
   * @param customerId customer ID
   * @return customer bills
   */
  public List<Bill> getCustomerBills(Long customerId);
  
  /**
   * Gives bill based on ID
   * @param billId bill ID
   * @return {@link Bill} object
   */
  public Bill getBillById(long billId);
  
  /**
   * Gives bills based on date
   * @param billDate date for get bills
   * @return {@link List} of {@link Bill}
   */
  public List<Bill> getBillsBasedOnDate(Date billDate);


}
