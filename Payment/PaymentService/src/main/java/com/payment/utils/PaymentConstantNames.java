package com.payment.utils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:shopDetails.properties")
public class PaymentConstantNames {

  @Resource
  private Environment environment;

  private String shopName;

  private String shopAddress;

  private String mobileNumber;

  private String note;

  private static final Logger log = LoggerFactory.getLogger(PaymentConstantNames.class);

  public String getShopName() {
    return shopName;
  }

  public String getShopAddress() {
    return shopAddress;
  }

  public String getMobileNumber() {
    return mobileNumber;
  }

  public String getNote() {
    return note;
  }

  @PostConstruct
  private void setProperties() {
    shopName = environment.getProperty("shop.name");
    shopAddress = environment.getProperty("shop.address");
    mobileNumber = environment.getProperty("shop.mobileNumber");
    note = environment.getProperty("bill.notes");
    log.info(
        "Details loaded and set to variables as follows:\nshopName:{}\nshopAddress:{}\nmobileNumber:{}\nnote:{}",
        shopName, shopAddress, mobileNumber, note);
  }

}
