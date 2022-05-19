package com.estockmarket.stockapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@EnableEurekaClient
@OpenAPIDefinition(info = @Info(title = "Stock API", version = "1.0", description = "stock api"))
public class StockmarketGatewayApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockmarketGatewayApiApplication.class, args);
	}

}
