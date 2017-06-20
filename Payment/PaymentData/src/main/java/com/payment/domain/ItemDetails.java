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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "ITEM_DETAILS", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "COMPANY_ID", "ITEM_ID" }) })
public class ItemDetails implements Serializable {

  private static final long serialVersionUID = 8803067465655470784L;

  @Id
  @GeneratedValue(generator = "inc")
  @GenericGenerator(strategy = "increment", name = "inc")
  @Column(name = "ID")
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST })
  @JoinColumn(name = "COMPANY_ID")
  private ItemCompany itemCompany;

  @OneToMany(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "ITEM_DETAILS_ID")
  @LazyCollection(LazyCollectionOption.TRUE)
  @JsonManagedReference
  private List<ItemPriceDetails> itemPriceDetails;

  @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
  @JoinColumn(name = "ITEM_ID")
  @JsonBackReference
  private Item item;

  public ItemCompany getItemCompany() {
    return itemCompany;
  }

  public void setItemCompany(ItemCompany itemCompany) {
    this.itemCompany = itemCompany;
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

  public List<ItemPriceDetails> getItemPriceDetails() {
    return itemPriceDetails;
  }

  public void setItemPriceDetails(List<ItemPriceDetails> itemPriceDetails) {
    this.itemPriceDetails = itemPriceDetails;
  }

}
