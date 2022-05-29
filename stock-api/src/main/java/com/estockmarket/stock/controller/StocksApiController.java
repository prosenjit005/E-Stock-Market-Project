package com.estockmarket.stock.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estockmarket.stock.entities.Stocks;
import com.estockmarket.stock.repository.StocksRepository;
import com.estockmarket.stock.restclient.CompanyApiRestClient;
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

	private String crudC = "C";
	private String crudD = "D";

	@GetMapping("/test")
	public String test() {
		logger.info("StocksApiController endpoint hit success !!!");
		return "StocksApiController endpoint hit success !!!";
	}

	@GetMapping("/testCompanyApi")
	public String testCompanyApi() {
		logger.info("companyApiRestClient.test() from StocksApiController endpoint hit success !!!");
		return companyApiRestClient.test() + " from StocksApiController !!";
	}

	@PostMapping("/add/{companycode}")
	public Stocks addNewStock(@RequestBody Stocks stocks, @PathVariable String companycode) {
		logger.info("addNewStock: stocks={} \n companycode={}", stocks, companycode);
		if (Boolean.TRUE.equals(stocksServices.validateStocksDetails(stocks, companycode))) {
			logger.info("addNewStock: stocks validation is successful");
			stocks.setCompanyCode(companycode);
			stocks.setStockDateTime(new Date());
			stocksRepository.save(stocks);

			// publishing in the topic so that MongoDB can also be in sync
			if (null != stocks) {
				logger.info("addNewStock: sending to kafka: CRUD_C: stocks={}", stocks);
				stocksServices.sendToKafka(stocks, crudC);
			}

			return stocks;
		} else {
			logger.info("addNewStock: stocks validation failed");
			return null;
		}

	}

	@DeleteMapping("/delete/{companycode}")
	public void deleteCompanyStocks(@PathVariable String companycode) {
		logger.info("deleteCompanyStocks: input companycode={}", companycode);
		List<Stocks> stocksList = stocksRepository.findByCompanyCode(companycode);

		// this will delete all stocks with the given company code. (from MySQL DB)
		stocksRepository.deleteAllByCompanyCode(companycode);

		// publishing in the topic so that MongoDB can also be in sync
		if (null != stocksList && !stocksList.isEmpty()) {
			logger.info("deleteCompanyStocks: sending to kafka: CRUD_D: stocksList.get(0)={}", stocksList.get(0));
			stocksServices.sendToKafka(stocksList.get(0), crudD);
		}
	}

	@GetMapping("/get/{companycode}/{startdate}/{enddate}")
	public List<com.estockmarket.stock.mongodbentities.Stocks> getStocks(@PathVariable String companycode,
			@PathVariable("startdate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date startdate,
			@PathVariable("enddate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date enddate) {
		logger.info("getStocks: input companycode={}, startdate={}, enddate={}", companycode, startdate, enddate);
		List<com.estockmarket.stock.mongodbentities.Stocks> stocksList = stocksServices.getStocksList(companycode,
				startdate, enddate);
		logger.info("getStocks: stocksList={}", stocksList);
		return stocksList;
	}

}
