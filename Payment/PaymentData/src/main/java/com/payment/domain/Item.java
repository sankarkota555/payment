package com.payment.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@DynamicUpdate
public class Item {

  @Id
  @GenericGenerator(name = "incrementGenerator", strategy = "increment")
  @GeneratedValue(generator = "incrementGenerator")
  private Long itemId;

  @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
  @JoinTable(name = "item_company", joinColumns = {
      @JoinColumn(name = "items_itemId") }, inverseJoinColumns = {
          @JoinColumn(name = "itemCompany_companyId") })
  @JsonBackReference
  private List<ItemCompany> itemCompanies;

  @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(name="item_bill")
  private List<Bill> bills;

  @Column(length = 100)
  private String itemName;

  @Column
  private int price;

  public Long getItemId() {
    return itemId;
  }

  public void setItemId(Long itemId) {
    this.itemId = itemId;
  }

  public List<ItemCompany> getItemCompanies() {
    return itemCompanies;
  }

  public void setItemCompanies(List<ItemCompany> itemCompanies) {
    this.itemCompanies = itemCompanies;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public List<Bill> getBills() {
    return bills;
  }

  public void setBills(List<Bill> bills) {
    this.bills = bills;
  }

}
