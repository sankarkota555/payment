package com.payment.dto;

import java.io.Serializable;

public class ItemDTO implements Serializable{

  private static final long serialVersionUID = -719118662936944475L;
  
  private String itemName;
  private Long itemId;
  private Long companyName;
  private String companyId;
  private String capacity;
  private int quantity;
  private int price;


  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public Long getItemId() {
    return itemId;
  }

  public void setItemId(Long itemId) {
    this.itemId = itemId;
  }

  public Long getCompanyName() {
    return companyName;
  }

  public void setCompanyName(Long companyName) {
    this.companyName = companyName;
  }

  public String getCompanyId() {
    return companyId;
  }

  public void setCompanyId(String companyId) {
    this.companyId = companyId;
  }

  public String getCapacity() {
    return capacity;
  }

  public void setCapacity(String capacity) {
    this.capacity = capacity;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

}
