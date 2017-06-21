package com.payment.dto;

import java.io.Serializable;
import java.util.List;

public class CustomerDTO implements Serializable {

  private static final long serialVersionUID = -6471133908807265278L;
  private Long id;

  private String name;

  private String address;

  private String email;

  private String phone;

  private List<BillDTO> bills;

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

  public List<BillDTO> getBills() {
    return bills;
  }

  public void setBills(List<BillDTO> bills) {
    this.bills = bills;
  }

}
