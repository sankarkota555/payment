package com.payment.test.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.payment.domain.PaymentUser;
import com.payment.repositories.UserRepository;
import com.payment.service.impl.SettingsServiceImpl;

public class SettingsServiceImplTest {

  private SettingsServiceImpl settingsServiceImpl;

  @Mock
  private UserRepository userRepository;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    settingsServiceImpl = new SettingsServiceImpl();
    settingsServiceImpl.setUserRepository(userRepository);
  }

  @Test
  public void testChagePasswordSuccess() {
    String curPass = "pass";
    String newPass = "newPass";
    String userName = "usrName";

    PaymentUser dummyUser = getDummyUser();
    when(userRepository.findByUserName(userName)).thenReturn(dummyUser);

    int result = settingsServiceImpl.chagePassword(curPass, newPass, userName);

    assertEquals("Should return 1 ", 1, result);
    assertEquals("Password should update", newPass, dummyUser.getPassword());
    verify(userRepository).save(dummyUser);
  }

  /**
   * Test change password with wrong current password.
   */
  @Test
  public void testChagePasswordFail() {
    String curPass = "pass123";
    String newPass = "newPass";
    String userName = "usrName";

    PaymentUser dummyUser = getDummyUser();
    String passowrd = dummyUser.getPassword();
    when(userRepository.findByUserName(userName)).thenReturn(dummyUser);

    int result = settingsServiceImpl.chagePassword(curPass, newPass, userName);

    assertEquals("Should return -1 ", -1, result);
    assertEquals("Password should not update", passowrd, dummyUser.getPassword());
    verify(userRepository, never()).save(dummyUser);
  }

  @Test
  public void testChagePasswordInvaliduser() {
    String curPass = "pass";
    String newPass = "newPass";
    String userName = "usrName";

    when(userRepository.findByUserName(userName)).thenReturn(null);

    int result = settingsServiceImpl.chagePassword(curPass, newPass, userName);

    assertEquals("Should return 0 ", 0, result);
    verify(userRepository, never()).save(Matchers.any(PaymentUser.class));
  }

  private PaymentUser getDummyUser() {
    PaymentUser user = new PaymentUser();
    user.setId(35632652L);
    user.setPassword("pass");
    user.setUserName("usrName");

    return user;
  }

}
