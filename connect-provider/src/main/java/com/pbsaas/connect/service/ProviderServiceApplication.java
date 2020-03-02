/**
 * sam@here 2019/8/31
 **/
package com.pbsaas.connect.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringCloudApplication
@EnableEurekaClient
@ComponentScan(basePackages ={ "com.pbsaas.connect.service","com.pbsaas.connect.db.service"})
@EnableJpaRepositories(basePackages ={ "com.pbsaas.connect.db.repository"})
@EntityScan(basePackages ={ "com.pbsaas.connect.db.entity"})
public class ProviderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderServiceApplication.class, args);
    }
}
