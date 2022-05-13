package com.estockmarket.company.restClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.estockmarket.company.config.FeignClientConfiguration;

@FeignClient(value = "stock-api", url = "http://localhost:8989/api/v1.0/market/stock/", configuration = FeignClientConfiguration.class)
public interface StocksApiRestClient {

	@GetMapping("/test")
	String test();

	@DeleteMapping("/delete/{companycode}")
	public void deleteCompanyStocks(@PathVariable String companycode);
}
