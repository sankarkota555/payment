package com.payment.config;

import org.apache.log4j.Logger;

//@Configuration
//@EnableJpaRepositories(basePackages="com.payment.repositories") // package name where is you spring data repositories written
public class HibernateConfig {
  
  private Logger logger=Logger.getLogger(HibernateConfig.class);

/*  @Bean
  public DataSource getDataSource() {
    BasicDataSource ds = new BasicDataSource();
    ds.setUsername("payment");
    ds.setPassword("payment");
    ds.setUrl("jdbc:oracle:thin:@localhost:1521:XE");
    ds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
    logger.info("Data source object created "+ ds);
    return ds;
  }*/
  
/*  @Bean
  public JndiObjectFactoryBean getJndiObject(){
    JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
    jndiObjectFactoryBean.setJndiName("jdbc/dataSource");
    return jndiObjectFactoryBean;
  }
*/
/*  @Bean
  public HibernateJpaVendorAdapter getHibernateJpaAdapter() {
    HibernateJpaVendorAdapter adpter=new HibernateJpaVendorAdapter();
    adpter.setShowSql(true);
    adpter.setGenerateDdl(true);
    adpter.setDatabasePlatform("org.hibernate.dialect.OracleDialect");
    
    return adpter;
  }*/
  
/*  @Bean(name="entityManagerFactory") // same name "entityManagerFactory"  required if u change name then @EnableJpaRepositories(entityManagerFactoryRef="name" ,basePackages="com.data.repositories")
  public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() throws IOException{
    LocalContainerEntityManagerFactoryBean factory=new LocalContainerEntityManagerFactoryBean();
    factory.setDataSource(getDataSource());
    factory.setJpaVendorAdapter(getHibernateJpaAdapter());
    factory.setPackagesToScan("com.payment.domain"); // packages where you wrote @Entity classes. (Domain classes)
    
    Properties hibernateProperties=new Properties();
    hibernateProperties.load(new  ClassPathResource("hibernate.properties").getInputStream()); // load hibernate.properties file
    factory.setJpaProperties(hibernateProperties);
    
    return factory;
  }*/
  

}
