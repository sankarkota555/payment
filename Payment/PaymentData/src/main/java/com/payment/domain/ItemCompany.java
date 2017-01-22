package com.payment.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@DynamicUpdate
@Table(name = "ItemCompany")
public class ItemCompany implements Serializable{

  private static final long serialVersionUID = 5744165048369913046L;

  @Id
  @GenericGenerator(name = "incrementGenerator", strategy = "increment")
  @GeneratedValue(generator = "incrementGenerator")
  private Long companyId;

  @Column(name="companyName", length = 50, unique = true)
  private String companyName;

/*  @ManyToMany(mappedBy = "itemCompanies")
  private List<Item> items;*/
  
  @OneToMany(mappedBy="itemCompany")
  private List<ItemDetails> itemDetails;

  public Long getCompanyId() {
    return companyId;
  }

  public void setCompanyId(Long companyId) {
    this.companyId = companyId;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

/*  public List<Item> getItems() {
    return items;
  }

  public void setItems(List<Item> items) {
    this.items = items;
  }*/

}
