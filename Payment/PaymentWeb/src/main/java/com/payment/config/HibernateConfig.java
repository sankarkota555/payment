package com.payment.config;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;


@Configuration
@EnableJpaRepositories(basePackages = "com.payment.repositories") // package name where is you
                                                                  // spring data repositories
                                                                  // written
@PropertySource("classpath:dataSource.properties")
public class HibernateConfig {

  private static final Logger log = LoggerFactory.getLogger(HibernateConfig.class);

  @Autowired
  private DataSource dataSource;
  
  @Resource
  private Environment environment;

  @Bean
  public DataSource getDataSource() {
    log.info("environ ment: "+ environment);
    BasicDataSource ds = new BasicDataSource();
    ds.setUsername(environment.getProperty("dataSourceUserName"));
    ds.setPassword(environment.getProperty("dataSourcePassword"));
    ds.setUrl(environment.getProperty("dataSourceUrl"));
    ds.setDriverClassName(environment.getProperty("dataSourceDriverClass"));
    log.info("Data source object created " + ds);
    return ds;
  }

  /*
   * @Bean public JndiObjectFactoryBean getJndiObject(){ JndiObjectFactoryBean jndiObjectFactoryBean
   * = new JndiObjectFactoryBean(); jndiObjectFactoryBean.setJndiName("jdbc/dataSource"); return
   * jndiObjectFactoryBean; }
   */
  @Bean
  public HibernateJpaVendorAdapter getHibernateJpaAdapter() {
    HibernateJpaVendorAdapter adpter = new HibernateJpaVendorAdapter();
    adpter.setShowSql(true);
    adpter.setGenerateDdl(true);
    adpter.setDatabasePlatform("org.hibernate.dialect.OracleDialect");

    return adpter;
  }

  @Bean(name = "entityManagerFactory") // same name "entityManagerFactory" required if u change name
                                       // then @EnableJpaRepositories(entityManagerFactoryRef="name"
                                       // ,basePackages="com.data.repositories")
  public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() throws IOException {
    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
    factory.setDataSource(getDataSource());
    factory.setJpaVendorAdapter(getHibernateJpaAdapter());
    factory.setPackagesToScan("com.payment.domain"); // packages where you wrote @Entity classes.
                                                     // (Domain classes)
    Properties hibernateProperties = new Properties();
    hibernateProperties.load(new ClassPathResource("hibernate.properties").getInputStream()); // load
                                                                                              // hibernate.properties
                                                                                              // file
    factory.setJpaProperties(hibernateProperties);
    return factory;
  }

  /**
   * close database connection on server stopping time.
   */
  @PreDestroy
  public void preDestroy() {
    log.info("In side pre destroy of hibernate config");
    Connection connection = null;
    try {
      connection = dataSource.getConnection();
      if (connection != null) {
        log.info("Closing database connection");
        connection.close();
        log.info("Success fully closed database connection !!!");
      }
    } catch (SQLException e) {
      log.error("Error while getting database connection");
      e.printStackTrace();
    }
  }

}
