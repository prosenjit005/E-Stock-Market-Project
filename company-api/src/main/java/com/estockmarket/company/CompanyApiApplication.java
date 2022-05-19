package com.estockmarket.company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
//@EnableSwagger2
@EnableEurekaClient
@EnableFeignClients
public class CompanyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompanyApiApplication.class, args);
	}

}
