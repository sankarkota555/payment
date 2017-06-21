package com.payment.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.payment.domain.Customer;

public class BillDTO implements Serializable {

  private static final long serialVersionUID = -4103566902005024218L;

  private Long billId;
  private CustomerDTO customer;
  private List<BillItemDTO> billItems;
  private Date generatedDate;
  private Long totalAmount;

  public Long getBillId() {
    return billId;
  }

  public void setBillId(Long billId) {
    this.billId = billId;
  }

  public CustomerDTO getCustomer() {
    return customer;
  }

  public void setCustomer(CustomerDTO customer) {
    this.customer = customer;
  }

  public Date getGeneratedDate() {
    return generatedDate;
  }

  public void setGeneratedDate(Date generatedDate) {
    this.generatedDate = generatedDate;
  }

  public List<BillItemDTO> getBillItems() {
    return billItems;
  }

  public void setBillItems(List<BillItemDTO> billItems) {
    this.billItems = billItems;
  }

  public Long getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(Long totalAmount) {
    this.totalAmount = totalAmount;
  }

}
