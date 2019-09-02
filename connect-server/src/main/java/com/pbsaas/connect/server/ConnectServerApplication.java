package com.pbsaas.connect.server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients({"com.pbsaas.connect.service"})
public class ConnectServerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ConnectServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
