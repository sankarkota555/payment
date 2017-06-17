package com.payment.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.payment.domain.PaymentSystem;
import com.payment.domain.PaymentSystemUsageDetails;
import com.payment.repositories.SystemRepository;
import com.payment.repositories.SystemUsageDetailsRepository;
import com.payment.service.SystemService;
import com.payment.utils.DateUtils;
import com.payment.utils.PaymentConstantNames;

@Service
public class SystemServiceImpl implements SystemService {

  private static final Logger log = LoggerFactory.getLogger(SystemServiceImpl.class);

  @Autowired
  private SystemRepository systemRepository;

  @Autowired
  private SystemUsageDetailsRepository systemUsageDetailsRepository;

  @Override
  public Iterable<PaymentSystem> getAllSystems() {
    return systemRepository.findAll();
  }

  @Override
  @Transactional
  public Long addNewSystem(String systemName) {
    if (systemRepository.findBySystemName(systemName) != null) {
      return -1L;
    } else {
      PaymentSystem system = new PaymentSystem();
      system.setSystemName(systemName);
      return systemRepository.save(system).getId();
    }

  }

  @Override
  @Transactional
  public Long addSystemUsageDetails(PaymentSystemUsageDetails paymentSystemUsageDetails) {
    paymentSystemUsageDetails.getPaymentSystem().getId();
    PaymentSystem system = systemRepository
        .findOne(paymentSystemUsageDetails.getPaymentSystem().getId());
    paymentSystemUsageDetails.setPaymentSystem(system);
    return systemUsageDetailsRepository.save(paymentSystemUsageDetails).getId();
  }

  @Override
  public List<PaymentSystem> getSystemsUsageStatus() {
    Iterator<PaymentSystem> allSystemsIterator = systemRepository.findAll().iterator();
    List<PaymentSystem> allSystemsList = new ArrayList<>();
    List<PaymentSystemUsageDetails> usageList;
    PaymentSystem system;
    List<PaymentSystemUsageDetails> usageListFromDb;
    while (allSystemsIterator.hasNext()) {
      system = allSystemsIterator.next();
      usageList = new ArrayList<>();
      usageListFromDb = systemUsageDetailsRepository.getSystemUsageStatus(system,DateUtils.getCurrentdate(),
          new PageRequest(0, 1));
      if (!usageListFromDb.isEmpty()) {
        usageList.add(usageListFromDb.get(0));
      }
      // set usage details
      system.setUsageDetails(usageList);
      // add system to list
      allSystemsList.add(system);
    }
    return allSystemsList;
  }

}
