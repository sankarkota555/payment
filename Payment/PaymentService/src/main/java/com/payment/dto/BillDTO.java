package com.payment.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.payment.domain.Customer;
import com.payment.domain.SoldItem;

public class BillDTO implements Serializable {

  private static final long serialVersionUID = -4103566902005024218L;

  private Long billId;
  private Customer customer;
  private List<SoldItem> soldItems;
  private Date generatedDate;
  private Integer totalAmount;

  public Long getBillId() {
    return billId;
  }

  public void setBillId(Long billId) {
    this.billId = billId;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public Date getGeneratedDate() {
    return generatedDate;
  }

  public List<SoldItem> getSoldItems() {
    return soldItems;
  }

  public void setSoldItems(List<SoldItem> soldItems) {
    this.soldItems = soldItems;
  }

  public void setGeneratedDate(Date generatedDate) {
    this.generatedDate = generatedDate;
  }

  public Integer getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(Integer totalAmount) {
    this.totalAmount = totalAmount;
  }

}
