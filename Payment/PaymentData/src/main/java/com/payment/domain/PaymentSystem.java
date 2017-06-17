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

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "PAYMENT_SYSTEM")
public class PaymentSystem implements Serializable {

  private static final long serialVersionUID = 8688129275700983799L;

  @Id
  @Column(name = "ID")
  @GenericGenerator(name = "incrementGenerator", strategy = "increment")
  @GeneratedValue(generator = "incrementGenerator")
  private Long id;

  @Column(name = "system_name", length = 30, unique = true)
  private String systemName;

  @OneToMany(mappedBy = "paymentSystem", cascade = CascadeType.PERSIST)
  @LazyCollection(LazyCollectionOption.TRUE)
  private List<PaymentSystemUsageDetails> usageDetails;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getSystemName() {
    return systemName;
  }

  public void setSystemName(String systemName) {
    this.systemName = systemName;
  }

  public List<PaymentSystemUsageDetails> getUsageDetails() {
    return usageDetails;
  }

  public void setUsageDetails(List<PaymentSystemUsageDetails> usageDetails) {
    this.usageDetails = usageDetails;
  }

}
