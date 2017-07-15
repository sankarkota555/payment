package com.payment.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "CUSTOMER")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Customer implements Serializable {

  private static final long serialVersionUID = -2224463901198787144L;

  @Id
  @Column(name = "ID")
  @GenericGenerator(name = "inc", strategy = "increment")
  @GeneratedValue(generator = "inc")
  private Long id;

  @Column(name = "NAME", unique = true, length = 50)
  private String name;

  @Column(name = "ADDRESS")
  private String address;

  @Column(name = "EMAIL", length = 50, unique = true)
  private String email;

  @Column(name = "PHONE", length = 15, unique = true)
  private String phone;

  @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "customer")
  @LazyCollection(value = LazyCollectionOption.TRUE)
  @JsonManagedReference
  private List<Bill> bills;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public List<Bill> getBills() {
    return bills;
  }

  public void setBills(List<Bill> bills) {
    this.bills = bills;
  }

  @Override
  public String toString() {
    return "Customer [id=" + id + ", name=" + name + ", phone=" + phone + "]";
  }

}
