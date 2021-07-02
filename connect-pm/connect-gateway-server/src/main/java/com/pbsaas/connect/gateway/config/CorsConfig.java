package com.pbsaas.connect.gateway.config;

import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.reactive.server.ReactiveWebServerFactory;
import org.springframework.boot.web.server.WebServer;

//import org.drugman.gateway.config.security.CorsResponseHeaderFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.DefaultCorsProcessor;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.pattern.PathPatternParser;

@Configuration
public class CorsConfig {

	@Autowired
	HttpHandler httpHandler;

	WebServer http;
	
	@Value("${http.port}") 
	private int port;
	
	@Bean
	public CorsWebFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
		source.registerCorsConfiguration("/**", buildCorsConfiguration());
		
		CorsWebFilter corsWebFilter = new CorsWebFilter(source, new DefaultCorsProcessor() {
			@Override
			protected boolean handleInternal(ServerWebExchange exchange, CorsConfiguration config, 
				boolean preFlightRequest) 
			{
				/**
				ServerHttpResponse response = exchange.getResponse();
				
				HttpHeaders  header= response.getHeaders();
				
				header.setAccessControlAllowCredentials(true);
				
				header.set("Access-Control-Allow-Origin", "*");
				header.set("Access-Control-Allow-Credentials", "true");
				header.set("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
				header.set("Access-Control-Max-Age", "3600");
				header.set("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization,x-requested-with");
		    	***/
				// 预留扩展点
				 if (exchange.getRequest().getMethod() == HttpMethod.OPTIONS) {
					return super.handleInternal(exchange, config, preFlightRequest);
				 }
				
				 return true;
			}
		});
		
		return corsWebFilter;
	}

	private CorsConfiguration buildCorsConfiguration() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedOrigin("*");
		
		corsConfiguration.addAllowedMethod("*");
	
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addExposedHeader("Content-Range");
		
		corsConfiguration.setMaxAge(7200L);
		corsConfiguration.setAllowCredentials(true);
		return corsConfiguration;
	}
	
	@PostConstruct
	public void start() {
	    ReactiveWebServerFactory factory = new NettyReactiveWebServerFactory(port);
	    this.http = factory.getWebServer(this.httpHandler);
	    this.http.start();
	}

	@PreDestroy
	public void stop() {
	    this.http.stop();
	}
}
