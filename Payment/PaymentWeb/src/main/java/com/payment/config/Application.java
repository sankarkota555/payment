package com.payment.config;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
public class Application extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
      return application.sources(Application.class);
  }
  
  public static void main(String args[]) {
    SpringApplication.run(Application.class, args);
  }
  
  @Bean
  public String applicationVersion() {
    return this.getClass().getPackage().getImplementationVersion();
  }
}