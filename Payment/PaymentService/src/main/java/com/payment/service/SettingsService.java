package com.payment.service;

public interface SettingsService {

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
  int chagePassword(String currentPassword, String newPassword, String userName);

}
