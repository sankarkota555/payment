package com.payment.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.payment.domain.PaymentSystem;
import com.payment.domain.PaymentSystemUsageDetails;
import com.payment.service.SystemService;

@RestController
public class SystemController {

  @Autowired
  private SystemService systemService;

  private static final Logger log = LoggerFactory.getLogger(SystemController.class);

  @RequestMapping(value = "/getSystems", method = RequestMethod.POST)
  public Iterable<PaymentSystem> getAllSystems() {
    return systemService.getAllSystems();
  }

  @RequestMapping(value = "/addNewSystem", method = RequestMethod.POST)
  public Long addNewSystem(String systemName) {
    return systemService.addNewSystem(systemName);
  }

  @RequestMapping(value = "/getSystemsStatus", method = RequestMethod.POST)
  public Iterable<PaymentSystem> getSystemsStatus() {
    return systemService.getSystemsUsageStatus();
  }

  @RequestMapping(value = "/addSystemUsageDetails", method = RequestMethod.POST)
  public Long addSystemUsageDetails(
      @RequestBody PaymentSystemUsageDetails paymentSystemUsageDetails) {
    return systemService.addSystemUsageDetails(paymentSystemUsageDetails);
  }

  @RequestMapping(value = "/updateSystemUsageDetails", method = RequestMethod.POST)
  public Long updateSystemUsageDetails(
      @RequestBody PaymentSystemUsageDetails paymentSystemUsageDetails) {
    return systemService.updateSystemUsageDetails(paymentSystemUsageDetails);
  }
}
