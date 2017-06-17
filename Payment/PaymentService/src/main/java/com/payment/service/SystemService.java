package com.payment.service;

import java.util.List;

import com.payment.domain.PaymentSystem;
import com.payment.domain.PaymentSystemUsageDetails;

public interface SystemService {

  /**
   * Gives all systems in DB.
   * 
   * @return {@link Iterable} of {@link PaymentSystem}
   */
  Iterable<PaymentSystem> getAllSystems();

  /**
   * Adds new system into DB.
   * 
   * @param systemName
   *          system name
   * @return Id of new system saved <br>
   *         -1 if, system with given name already in DB.
   */
  Long addNewSystem(String systemName);
  
  /**
   * Gives systems status for curret instance.
   * @return {@link List} of {@link PaymentSystem}
   */
  List<PaymentSystem> getSystemsUsageStatus();
  
  /**
   * Adds new payment system usage details in DB.
   * @param paymentSystemUsageDetails {@link PaymentSystemUsageDetails} object
   * @return Id of object saved.
   */
  Long addSystemUsageDetails(PaymentSystemUsageDetails paymentSystemUsageDetails);

}
