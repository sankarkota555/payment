package com.payment.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@DynamicUpdate
@Table(name = "ItemDetails")
public class ItemDetails {

  @Id
  @GeneratedValue(generator = "foreigngen")
  @GenericGenerator(strategy = "foreign", name = "foreigngen", parameters = @Parameter(name = "property", value = "itemCompany"))
  @Column(name = "company_id")
  private Long id;

  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
  @PrimaryKeyJoinColumn
  private ItemCompany itemCompany;

  @Column(name = "capacity", length = 20)
  private String capacity;

  @Column(name = "price")
  private int price;

  @ManyToOne(cascade= CascadeType.ALL)
  @Fetch(FetchMode.JOIN)
  @JoinColumn(name = "item_id")
  private Item item;

  public ItemCompany getItemCompany() {
    return itemCompany;
  }

  public void setItemCompany(ItemCompany itemCompany) {
    this.itemCompany = itemCompany;
  }

  public String getCapacity() {
    return capacity;
  }

  public void setCapacity(String capacity) {
    this.capacity = capacity;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Item getItem() {
    return item;
  }

  public void setItem(Item item) {
    this.item = item;
  }

}