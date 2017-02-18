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

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@DynamicUpdate
@Table(name = "ItemDetails", uniqueConstraints = { @UniqueConstraint( columnNames = { "company_id", "item_id" } ) } )
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class ItemDetails implements Serializable {

  private static final long serialVersionUID = 8803067465655470784L;

  @Id
  @GeneratedValue(generator = "inc")
  @GenericGenerator(strategy = "increment", name = "inc")
  @Column(name = "id")
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST })
  @JoinColumn(name = "company_id")
  private ItemCompany itemCompany;

  @Column(name = "capacity", length = 20)
  private String capacity;

  @Column(name = "price")
  private Integer price;

  @Column(name = "quantity")
  private Integer quantity;

  @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Item getItem() {
    return item;
  }

  public void setItem(Item item) {
    this.item = item;
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

}
