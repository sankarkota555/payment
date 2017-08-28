package com.payment.dto;

import java.io.Serializable;

/**
 * Using for socket updates.
 *
 */
public class ItemPriceDetailsDTO implements Serializable {

  private static final long serialVersionUID = 5285585375818722169L;

  private Long id;

  private String capacity;

  private Integer price;

  private Integer quantity;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
