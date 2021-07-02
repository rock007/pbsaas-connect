/**
* sam@here 2021年4月22日
**/
package com.pbsaas.connect.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorResourceFactory;

@Configuration
public class ReactNettyConfiguration {
   
	@Value("${reactor.netty.worker-count}")
    private String workerCount;

    @Bean
    public ReactorResourceFactory reactorClientResourceFactory() {
        System.setProperty("reactor.netty.ioWorkerCount", workerCount);
        
        System.out.println("reactorClientResourceFactory:"+Math.max(Runtime.getRuntime().availableProcessors(), 4)); 
        return new ReactorResourceFactory();
    }
}