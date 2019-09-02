package com.pbsaas.web;

import com.pbsaas.web.app.config.JerseyConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.glassfish.jersey.servlet.ServletContainer;  
import org.glassfish.jersey.servlet.ServletProperties;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages ={ "com.pbsaas.connect.service","com.pbsaas.connect.db.service"})
@EnableJpaRepositories(basePackages ={ "com.pbsaas.connect.db.repository"})
@EntityScan(basePackages ={ "com.pbsaas.connect.db.entity"})
public class WebApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}
	
	@Bean
    ProtobufHttpMessageConverter protobufHttpMessageConverter() {
        return new ProtobufHttpMessageConverter();
    }
	
	@Bean
	public ServletRegistrationBean jersetServlet() {
		ServletRegistrationBean registration = new ServletRegistrationBean(new ServletContainer(), "/mars/*");
		// our rest resources will be available in the path /jersey/*
		registration.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, JerseyConfig.class.getName());
		return registration;
	}
	

}
