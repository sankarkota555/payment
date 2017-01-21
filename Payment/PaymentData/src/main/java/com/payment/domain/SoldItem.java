package com.payment.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;

@Entity
@DynamicInsert
@Table(name = "solditem")
public class SoldItem implements Serializable{

  private static final long serialVersionUID = 1942326327198211908L;

  @Id
  @Column(name = "solditem_id")
  @GenericGenerator(name = "inc", strategy = "increment")
  @GeneratedValue(generator = "inc")
  private Long soldItemId;

  @OneToOne(fetch = FetchType.EAGER,cascade=CascadeType.PERSIST)
  @JoinColumn(name = "itemdetails_id")
  private ItemDetails itemDetails;

  @Column(name = "quantity")
  private Integer quantity;

  @Column(name = "soldprice")
  private Integer soldPrice;

  public Long getSoldItemId() {
    return soldItemId;
  }

  public void setSoldItemId(Long soldItemId) {
    this.soldItemId = soldItemId;
  }

  public ItemDetails getItemDetails() {
    return itemDetails;
  }

  public void setItemDetails(ItemDetails itemDetails) {
    this.itemDetails = itemDetails;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Integer getSoldPrice() {
    return soldPrice;
  }

  public void setSoldPrice(Integer soldPrice) {
    this.soldPrice = soldPrice;
  }

}
