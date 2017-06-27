package com.payment.test.data;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.payment.repositories.BillRepository;
import com.payment.repositories.CustomerRepository;
import com.payment.repositories.ItemCompanyRepository;
import com.payment.repositories.ItemDetailsRepository;
import com.payment.repositories.ItemPriceDetailsRepositoty;
import com.payment.repositories.ItemRepository;
import com.payment.repositories.SystemRepository;
import com.payment.repositories.SystemUsageDetailsRepository;
import com.payment.repositories.UserRepository;
import com.payment.test.dataconfig.DataTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DataTestConfig.class })
public class DataTest {

  private static final Logger log = LoggerFactory.getLogger(DataTest.class);

  @Autowired
  private BillRepository billRepository;

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private ItemCompanyRepository itemCompanyRepository;

  @Autowired
  private ItemDetailsRepository itemDetailsRepository;

  @Autowired
  private ItemPriceDetailsRepositoty itemPriceDetailsRepositoty;

  @Autowired
  private ItemRepository itemRepository;

  @Autowired
  private SystemRepository systemRepository;

  @Autowired
  private SystemUsageDetailsRepository systemUsageDetailsRepository;

  @Autowired
  private UserRepository userRepository;

  @Test
  public void testDataValidation() {
    
    log.info("billRepository in test: {}", billRepository);
    log.info("customerRepository in test: {}", customerRepository);
    log.info("itemCompanyRepository in test: {}", itemCompanyRepository);
    log.info("itemDetailsRepository in test: {}", itemDetailsRepository);
    log.info("itemPriceDetailsRepositoty in test: {}", itemPriceDetailsRepositoty);
    log.info("itemRepository in test: {}", itemRepository);
    log.info("systemRepository in test: {}", systemRepository);
    log.info("systemUsageDetailsRepository in test: {}", systemUsageDetailsRepository);
    log.info("userRepository in test: {}", userRepository);

    assertNotNull("billRepository is null injected", billRepository);
    assertNotNull("customerRepository is null injected", customerRepository);
    assertNotNull("itemCompanyRepository is null injected", itemCompanyRepository);
    assertNotNull("itemDetailsRepository is null injected", itemDetailsRepository);
    assertNotNull("itemPriceDetailsRepositoty is null injected", itemPriceDetailsRepositoty);
    assertNotNull("itemRepository is null injected", itemRepository);
    assertNotNull("systemRepository is null injected", systemRepository);
    assertNotNull("systemUsageDetailsRepository is null injected", systemUsageDetailsRepository);
    assertNotNull("userRepository is null injected", userRepository);

  }

}
