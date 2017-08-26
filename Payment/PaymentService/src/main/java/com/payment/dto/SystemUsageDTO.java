package com.payment.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Using for socket updates.
 *
 */
public class SystemUsageDTO implements Serializable {

  private static final long serialVersionUID = -3647093226266645401L;

  private Long id;

  private SystemDTO paymentSystem;

  private String cutomerName;

  private Date loginTime;

  private Float hours;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public SystemDTO getPaymentSystem() {
    return paymentSystem;
  }

  public void setPaymentSystem(SystemDTO paymentSystem) {
    this.paymentSystem = paymentSystem;
  }

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

}
