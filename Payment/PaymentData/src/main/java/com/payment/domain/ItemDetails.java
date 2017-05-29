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
@Table(name = "ItemDetails", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "company_id", "item_id" }) })
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

  @OneToMany(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "itemdetails_id")
  @LazyCollection(LazyCollectionOption.TRUE)
  @JsonManagedReference
  private List<ItemPriceDetails> itemPriceDetails;

  @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
  @JoinColumn(name = "item_id")
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
