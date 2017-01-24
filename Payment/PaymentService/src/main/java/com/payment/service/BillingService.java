package com.payment.service;

import java.util.List;

import com.payment.domain.Bill;

public interface BillingService {
  
  /**
   * saves given bill into database.
   * @param {@link Bill} object to save
   * @return saved bill id.
   */
  public Long saveBill(Bill bill);
  
  public List<Bill> getCustomerBills(Long customerId);

}
