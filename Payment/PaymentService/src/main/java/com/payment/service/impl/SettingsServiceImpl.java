package com.payment.service.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payment.domain.PaymentUser;
import com.payment.repositories.UserRepository;
import com.payment.service.SettingsService;

/*
 * Settings service implementation
 */
@Service
public class SettingsServiceImpl implements SettingsService {

  @Autowired
  private UserRepository userRepository;

  private static final Logger log = LoggerFactory.getLogger(SettingsServiceImpl.class);

  /**
   * Changes password of given user
   * 
   * @param newPassword
   *          new password
   * @param userName
   *          user name
   * @param currentPassword
   *          current Password
   * @return 1, if success<br>
   *         -1, if current password not matched.<br>
   *         0, if failed.
   */
  @Transactional
  @Override
  public int chagePassword(String currentPassword, String newPassword, String userName) {
    PaymentUser user = userRepository.findByUserName(userName);
    if (user != null) {
      if (user.getPassword().equals(currentPassword)) {
        user.setPassword(newPassword);
        userRepository.save(user);
        log.info("Changed password of user: {}", userName);
        return 1;
      } else {
        log.info("current password and not matched.");
        return -1;
      }
    }
    return 0;
  }

}
