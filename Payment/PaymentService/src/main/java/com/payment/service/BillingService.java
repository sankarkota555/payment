package com.payment.service;

import java.util.Date;
import java.util.List;

import com.payment.domain.Bill;
import com.payment.dto.BillDTO;

public interface BillingService {
  
  /**
   * saves given bill into database.
   * @param {@link Bill} object to save
   * @return saved bill id.
   */
  public Long saveBill(final Bill bill);
  
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
   * Gives bills between given dates
   * @param frmDate from date
   * @param todate to date
   * @return {@link List} of {@link BillDTO}
   */
  public List<BillDTO> getBillsBetweebDates(Date frmDate, Date todate);


}
