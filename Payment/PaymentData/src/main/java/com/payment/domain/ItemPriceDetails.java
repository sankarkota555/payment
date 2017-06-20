package com.payment.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "ITEM_PRICE_DETAILS", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "CAPACITY", "ITEM_DETAILS_ID" }) })
@DynamicUpdate
@DynamicInsert
public class ItemPriceDetails implements Serializable{

  private static final long serialVersionUID = -3876472800137573252L;

  @Id
  @Column(name = "ID")
  @GenericGenerator(name = "incrementGenerator", strategy = "increment")
  @GeneratedValue(generator = "incrementGenerator")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, cascade= CascadeType.PERSIST)
  @JoinColumn(name="ITEM_DETAILS_ID")
  @JsonBackReference
  private ItemDetails itemDetails;

  @Column(name = "CAPACITY", length = 20)
  private String capacity;

  @Column(name = "PRICE")
  private Integer price;
  
  @Column(name = "QUANTITY")
  private Integer quantity;
  
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ItemDetails getItemDetails() {
    return itemDetails;
  }

  public void setItemDetails(ItemDetails itemDetails) {
    this.itemDetails = itemDetails;
  }

  public String getCapacity() {
    return capacity;
  }

  public void setCapacity(String capacity) {
    this.capacity = capacity;
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  
}
