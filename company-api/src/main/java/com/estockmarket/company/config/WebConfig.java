package com.estockmarket.company.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/stockapi/**").allowedOrigins("http://stockapi")
				.allowedMethods("GET", "POST", "DELETE")
				.allowedHeaders("header1", "header2")
				.exposedHeaders("header1", "header2")
				.allowCredentials(false)
				.maxAge(3600);
	}

}
