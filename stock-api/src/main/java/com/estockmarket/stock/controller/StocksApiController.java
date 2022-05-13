package com.estockmarket.stock.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estockmarket.stock.entities.Stocks;
import com.estockmarket.stock.repository.StocksRepository;
import com.estockmarket.stock.restClient.CompanyApiRestClient;
import com.estockmarket.stock.service.StocksServices;

@RestController
@RequestMapping("/stock")
public class StocksApiController {

	private static final Logger logger = LoggerFactory.getLogger(StocksApiController.class);

	@Autowired
	StocksServices stocksServices;

	@Autowired
	StocksRepository stocksRepository;

	@Autowired
	CompanyApiRestClient companyApiRestClient;

	private String CRUD_C = "C";
	private String CRUD_U = "U";
	private String CRUD_D = "D";

	@GetMapping("/test")
	public String test() {
		return "StocksApiController endpoint hit success !!!";
	}

	@GetMapping("/testCompanyApi")
	public String testCompanyApi() {
		return companyApiRestClient.test() + " from StocksApiController !!";
	}

	@PostMapping("/add/{companycode}")
	public String addNewStock(@RequestBody Stocks stocks, @PathVariable String companycode) {
		logger.info("stocks={} \n companycode={}", stocks, companycode);
		if (stocksServices.validateStocksDetails(stocks, companycode)) {
			stocks.setCompanyCode(companycode);
			stocks.setStockDateTime(new Date());
			stocksRepository.save(stocks);

			// publishing in the topic so that MongoDB can also be in sync
			if (null != stocks) {
				stocksServices.sendToKafka(stocks, CRUD_C);
			}

			return "Stocks Data added successfully with ID=" + stocks.getId();
		} else {
			return "There is an issue.";
		}

	}

	@DeleteMapping("/delete/{companycode}")
	public void deleteCompanyStocks(@PathVariable String companycode) {
		List<Stocks> stocksList = stocksRepository.findByCompanyCode(companycode);
		
		// this will delete all stocks with the given company code. (from MySQL DB)
		stocksRepository.deleteAllByCompanyCode(companycode);

		// publishing in the topic so that MongoDB can also be in sync
		if (null != stocksList && stocksList.size() > 0) {
			stocksServices.sendToKafka(stocksList.get(0), CRUD_D);
		}
	}

}
