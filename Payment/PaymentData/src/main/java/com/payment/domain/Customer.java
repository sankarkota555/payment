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

@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "customer")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Customer implements Serializable{

  private static final long serialVersionUID = -2224463901198787144L;

  @Id
  @Column(name = "id")
  @GenericGenerator(name = "inc", strategy = "increment")
  @GeneratedValue(generator = "inc")
  private Long id;

  @Column(name = "name", length = 50)
  private String name;

  @Column(name = "address")
  private String address;

  @Column(name = "email", length = 50, unique = true)
  private String email;

  @Column(name = "phone", length = 15, unique = true)
  private String phone;

  @OneToMany(cascade=CascadeType.PERSIST)
  @LazyCollection(value = LazyCollectionOption.TRUE)
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
