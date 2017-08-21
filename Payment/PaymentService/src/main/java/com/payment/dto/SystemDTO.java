package com.payment.dto;

import java.io.Serializable;
import java.util.List;

public class SystemDTO implements Serializable {

  private static final long serialVersionUID = 5296209836930054197L;

  private Long id;

  private String systemName;

  private List<SystemUsageDTO> usageDetails;

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

  public List<SystemUsageDTO> getUsageDetails() {
    return usageDetails;
  }

  public void setUsageDetails(List<SystemUsageDTO> usageDetails) {
    this.usageDetails = usageDetails;
  }

}
