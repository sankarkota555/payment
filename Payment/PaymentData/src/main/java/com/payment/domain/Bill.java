package com.payment.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "Bill")
public class Bill implements Serializable {

  private static final long serialVersionUID = 3801681122081332394L;

  @Id
  @Column(name = "bill_id")
  @GenericGenerator(name = "inc", strategy = "increment")
  @GeneratedValue(generator = "inc")
  private Long billId;

  @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(name = "bill_items", joinColumns = {
      @JoinColumn(name = "bill_id") }, inverseJoinColumns = { @JoinColumn(name = "item_id") })
  private List<Item> items;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "customer_id")
  private Customer customer;

  @Column(name = "net_amount")
  private Long netAmount;

  @Temporal(TemporalType.TIME)
  @Column(name = "generated_date")
  private Date generatedDate;

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

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public Long getNetAmount() {
    return netAmount;
  }

  public void setNetAmount(Long netAmount) {
    this.netAmount = netAmount;
  }

  public Date getGeneratedDate() {
    return generatedDate;
  }

  public void setGeneratedDate(Date generatedDate) {
    this.generatedDate = generatedDate;
  }

}
