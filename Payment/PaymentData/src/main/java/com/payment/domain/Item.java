package com.payment.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "ITEM")
public class Item implements Serializable {

  private static final long serialVersionUID = -212307287210164094L;

  @Id
  @Column(name = "ID")
  @GenericGenerator(name = "incrementGenerator", strategy = "increment")
  @GeneratedValue(generator = "incrementGenerator")
  private Long itemId;

  @Column(name = "ITEM_NAME", length = 100, unique = true)
  private String itemName;

  @OneToMany(cascade= CascadeType.PERSIST)
  @Fetch(FetchMode.JOIN)
  @JoinColumn(name = "ITEM_ID")
  @JsonManagedReference
  private List<ItemDetails> itemDetails;

  public Long getItemId() {
    return itemId;
  }

  public void setItemId(Long itemId) {
    this.itemId = itemId;
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
