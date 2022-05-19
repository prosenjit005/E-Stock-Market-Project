package com.estockmarket.stock.restclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.estockmarket.stock.config.FeignClientConfiguration;
import com.estockmarket.stock.dto.Company;

@FeignClient(value = "company-api", url = "http://localhost:8989/api/v1.0/market/company/", configuration = FeignClientConfiguration.class)
public interface CompanyApiRestClient {

	@GetMapping("/test")
	String test();

	@GetMapping("/info/{companycode}")
	public Company getCompanyInfo(@PathVariable String companycode);
}
