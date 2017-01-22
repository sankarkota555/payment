package com.payment.service;

import java.util.List;

import com.payment.domain.Bill;

public interface BillingService {
  
  public void saveBill(Bill bill);
  
  public List<Bill> getCustomerBills(Long customerId);

}
