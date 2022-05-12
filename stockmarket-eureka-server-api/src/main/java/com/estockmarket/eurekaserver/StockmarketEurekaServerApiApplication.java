package com.estockmarket.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class StockmarketEurekaServerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockmarketEurekaServerApiApplication.class, args);
	}

}
