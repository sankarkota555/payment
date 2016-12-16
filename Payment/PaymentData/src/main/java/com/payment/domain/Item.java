package com.payment.domain;

import java.io.Serializable;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "item")
public class Item implements Serializable {

  private static final long serialVersionUID = -212307287210164094L;

  @Id
  @Column(name = "item_id")
  @GenericGenerator(name = "incrementGenerator", strategy = "increment")
  @GeneratedValue(generator = "incrementGenerator")
  private Long itemId;

  @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  @JoinTable(name = "item_company", joinColumns = {
      @JoinColumn(name = "items_itemId") }, inverseJoinColumns = {
          @JoinColumn(name = "itemCompany_companyId") })
  private List<ItemCompany> itemCompanies;

  @ManyToMany(mappedBy = "items", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
  private List<Bill> bills;

  @Column(name = "item_Name", length = 100)
  private String itemName;

  @OneToMany(mappedBy = "item")
  private List<ItemDetails> itemDetails;

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

  public List<Bill> getBills() {
    return bills;
  }

  public void setBills(List<Bill> bills) {
    this.bills = bills;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public List<ItemDetails> getItemDetails() {
    return itemDetails;
  }

  public void setItemDetails(List<ItemDetails> itemDetails) {
    this.itemDetails = itemDetails;
  }

}
