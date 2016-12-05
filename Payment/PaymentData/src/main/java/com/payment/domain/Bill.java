package com.payment.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Bill {

  @Id
  @GenericGenerator(name = "inc", strategy = "increment")
  @GeneratedValue(generator = "inc")
  private Long billId;

  @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(name = "bill_items")
  private List<Item> items;

  @ManyToOne(cascade = CascadeType.PERSIST)
  private Customer customer;

  private Long netAmount;

  public Long getBillId() {
    return billId;
  }

  public void setBillId(Long billId) {
    this.billId = billId;
  }

  public List<Item> getItems() {
    return items;
  }

  public void setItems(List<Item> items) {
    this.items = items;
  }

  public Long getNetAmount() {
    return netAmount;
  }

  public void setNetAmount(Long netAmount) {
    this.netAmount = netAmount;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

}
