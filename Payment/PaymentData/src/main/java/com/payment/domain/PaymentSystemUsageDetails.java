package com.payment.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "PAYMENT_SYSTEM_USAGE_DETAILS", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "SYSTEM_ID", "LOGIN_TIME" }) })
@DynamicUpdate
public class PaymentSystemUsageDetails implements Serializable {

  private static final long serialVersionUID = -5491185210066217320L;

  @Id
  @Column(name = "ID")
  @GenericGenerator(name = "incrementGenerator", strategy = "increment")
  @GeneratedValue(generator = "incrementGenerator")
  private Long id;

  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "SYSTEM_ID")
  @JsonBackReference
  private PaymentSystem paymentSystem;

  @Column(name = "CUSTOMER_NAME", length = 30)
  private String cutomerName;

  @Column(name = "LOGIN_TIME")
  @Temporal(TemporalType.TIMESTAMP)
  private Date loginTime;

  @Column(name = "HOURS")
  private Float hours;

  public String getCutomerName() {
    return cutomerName;
  }

  public void setCutomerName(String cutomerName) {
    this.cutomerName = cutomerName;
  }

  public Date getLoginTime() {
    return loginTime;
  }

  public void setLoginTime(Date loginTime) {
    this.loginTime = loginTime;
  }

  public Float getHours() {
    return hours;
  }

  public void setHours(Float hours) {
    this.hours = hours;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public PaymentSystem getPaymentSystem() {
    return paymentSystem;
  }

  public void setPaymentSystem(PaymentSystem paymentSystem) {
    this.paymentSystem = paymentSystem;
  }
}
