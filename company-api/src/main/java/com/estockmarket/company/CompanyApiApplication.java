package com.estockmarket.company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
//@EnableSwagger2
@EnableEurekaClient
public class CompanyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompanyApiApplication.class, args);
	}

//	@Bean
//	public Docket productApi() {
//		return new Docket(DocumentationType.SWAGGER_2).select().paths(PathSelectors.any())
//				.apis(RequestHandlerSelectors.basePackage("com.estockmarket.company.controller"))
//				.apis(RequestHandlerSelectors.basePackage("org.springframework.boot.actuate")).build()
//				.apiInfo(apiInfo());
//	}
//
//	private ApiInfo apiInfo() {
//		return new ApiInfoBuilder().title("Building RESTful APIs with Swagger2 in Spring Boot").description("test")
//				.termsOfServiceUrl("https://www.estckmarketdemo.com/").version("1.0").build();
//	}

}
