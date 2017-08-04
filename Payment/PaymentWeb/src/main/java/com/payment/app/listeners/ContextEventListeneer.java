package com.payment.app.listeners;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.payment.domain.PaymentUser;
import com.payment.repositories.UserRepository;

@Component
public class ContextEventListeneer {

  private static final Logger log = LoggerFactory.getLogger(ContextEventListeneer.class);

  @Autowired
  private UserRepository userRepository;

  @Value("${user.home}")
  private String userHomePath;

  @Value("#{servletContext.contextPath}")
  private String servletContextPath;

  @Value("${localhost.port}")
  private String serverPortNumber;

  @Value("${users.directory.path}")
  private String userdDir;
  
  @Value("${username}")
  private String loggedUsername;
  
  private static final String FILE_NAME = "paymentLogin.html";

  private static final String ADMIN_TEXT = "admin";

  private static final String HTML_TEXT = "<html><head><script>window.location.replace('http://%s')</script></head></html>";

  @EventListener({ ContextRefreshedEvent.class })
  @Async
  void contextRefreshedEventFired() {
    log.debug("Context path of web application: {}", servletContextPath);
    log.info("====== Context event listener fired ======");
    // validate default user
    validateDefaultUser();
    // create shortcut file
    createShortcutFile();
  }

  @Transactional
  private void validateDefaultUser() {
    try {
      PaymentUser adminUser = userRepository.findByUserName(ADMIN_TEXT);
      if (adminUser == null) {
        log.info("Admin user not found in DB, inserting default admin user to DB");
        adminUser = new PaymentUser();
        adminUser.setId(123L);
        adminUser.setUserName(ADMIN_TEXT);
        adminUser.setPassword(ADMIN_TEXT);
        adminUser.setRole(ADMIN_TEXT);
        userRepository.save(adminUser);
        log.info("Successfully inserted Admin user to DB with default values");
      }
    } catch (Exception exception) {
      log.error("Exception while validating default admin user", exception);
    }
  }

  private void createShortcutFile() {

    String fileLocation = null;

    /*
     * if (userHomePath.contains(loggedUsername)) { log.info("user home path contains user name");
     * fileLocation = userHomePath; }else {
     */

    log.debug("creating user home path maually");
    File usersDirectory = new File(userdDir);
    File[] filesInUser = usersDirectory.listFiles();
    for (File subFile : filesInUser) {
      if (subFile.getName().toLowerCase()
          .contains(loggedUsername.toLowerCase().substring(0, loggedUsername.length() - 2))
          && subFile.isDirectory()) {
        log.debug("user names matched - logged user:{}, found user:{}", loggedUsername,
            subFile.getName());
        log.debug("file absolute path: {}", subFile.getAbsolutePath());
        fileLocation = subFile.getAbsolutePath();
        break;
      }
    }
    // }
    /* Verify shortcut file */
    fileLocation += File.separator + "Desktop" + File.separator + FILE_NAME;
    log.info("File write location: {}", fileLocation);
    File shortcutFile = new File(fileLocation);

    // The below try block is java "try-with-resources" from JDK 7 onwards
    try (FileOutputStream fileOutputStream = new FileOutputStream(shortcutFile)) {
      byte[] bytes = String.format(HTML_TEXT, InetAddress.getLoopbackAddress().getHostAddress()
          + ":" + serverPortNumber + servletContextPath).getBytes();
      log.debug("HTML string bytes length:{} ", bytes.length);
      fileOutputStream.write(bytes);
      log.debug("Writing to shortcut file completed...");
    } catch (IOException ioException) {
      log.error("Exception while validating default shorcut file:", ioException);
    }
  }

}
