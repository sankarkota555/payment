package com.payment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.faces.config.AbstractFacesFlowConfiguration;
import org.springframework.faces.webflow.FlowFacesContextLifecycleListener;
import org.springframework.faces.webflow.JsfViewFactoryCreator;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.ViewFactoryCreator;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.executor.FlowExecutor;

/**
 * 
 * Spring web-flow configuration.
 *
 */
@Configuration
public class WebFlowConfig extends AbstractFacesFlowConfiguration {

	@Bean
	public ViewFactoryCreator viewFactoryCreator() {
	  return new JsfViewFactoryCreator();
	}

	@Bean
	public FlowBuilderServices flowBuilderServices() {
		return getFlowBuilderServicesBuilder().setDevelopmentMode(true).build();

	}

	@Bean
	public FlowDefinitionRegistry flowRegistry() {
		return getFlowDefinitionRegistryBuilder(flowBuilderServices()).setBasePath("/WEB-INF/flows")
				.addFlowLocationPattern("/**/*-flow.xml").build();
	}

	@Bean
	public FlowExecutor flowExecutor() {
		return getFlowExecutorBuilder(flowRegistry()).addFlowExecutionListener(new FlowFacesContextLifecycleListener())
				.build();
	}

}
