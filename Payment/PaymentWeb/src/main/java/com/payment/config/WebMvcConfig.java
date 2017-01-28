package com.payment.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.faces.webflow.JsfFlowHandlerAdapter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ResourceBundleViewResolver;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.mvc.servlet.FlowHandlerMapping;

@Configuration
@ComponentScan(basePackages="com.payment")
public class WebMvcConfig extends WebMvcConfigurerAdapter{

  @Autowired
  private FlowExecutor flowExecutor;

  @Autowired
  private FlowDefinitionRegistry flowRegistry;

  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }

  /**
   * Bean for spring webflow configuration
   * 
   * @return {@link JsfFlowHandlerAdapter}
   */
  @Bean
  public JsfFlowHandlerAdapter getJsfFlowHandlerAdapter() {
    JsfFlowHandlerAdapter handlerAdapter = new JsfFlowHandlerAdapter();
    handlerAdapter.setFlowExecutor(flowExecutor);
    return handlerAdapter;
  }

  /**
   * Bean for spring webflow configuration
   * 
   * @return {@link FlowHandlerMapping}
   */
  @Bean
  public FlowHandlerMapping flowHandlerMapping() {
    FlowHandlerMapping handlerMapping = new FlowHandlerMapping();
    handlerMapping.setOrder(-1);
    handlerMapping.setFlowRegistry(flowRegistry);
    return handlerMapping;
  }
  
  @Bean
  public ViewResolver getResourceBundleView(){
    ResourceBundleViewResolver resourceBundleViewResolver = new ResourceBundleViewResolver();
    resourceBundleViewResolver.setBasename("views");
    resourceBundleViewResolver.setOrder(2);    
    return resourceBundleViewResolver;
  }

}