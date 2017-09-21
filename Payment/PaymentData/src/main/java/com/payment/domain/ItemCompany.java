package com.payment.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "ITEM_COMPANY")
public class ItemCompany implements Serializable {

  private static final long serialVersionUID = 5744165048369913046L;

  @Id
  @Column(name = "ID")
  @GenericGenerator(name = "incrementGenerator", strategy = "increment")
  @GeneratedValue(generator = "incrementGenerator")
  private Long companyId;

  @Column(name = "COMPANY_NAME", length = 50, unique = true)
  private String companyName;

  @OneToMany(mappedBy = "itemCompany")
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

  @Override
  public int hashCode() {
    final int PRIME = 31;
    int result = 1;
    result = PRIME * result + ((companyId == null) ? 0 : companyId.hashCode());
    result = PRIME * result + ((companyName == null) ? 0 : companyName.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ItemCompany other = (ItemCompany) obj;
    if (companyId == null) {
      if (other.companyId != null)
        return false;
    } else if (!companyId.equals(other.companyId))
      return false;
    if (companyName == null) {
      if (other.companyName != null)
        return false;
    } else if (!companyName.equals(other.companyName))
      return false;
    return true;
  }

}
