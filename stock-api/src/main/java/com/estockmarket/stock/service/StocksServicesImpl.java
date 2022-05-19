package com.estockmarket.stock.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.estockmarket.stock.dto.Company;
import com.estockmarket.stock.dto.DbSyncTopicStocksDto;
import com.estockmarket.stock.entities.Stocks;
import com.estockmarket.stock.repository.StocksMongoDbRepository;
import com.estockmarket.stock.restclient.CompanyApiRestClient;

@Service
public class StocksServicesImpl implements StocksServices {

	private static final Logger logger = LoggerFactory.getLogger(StocksServicesImpl.class);

	@Autowired
	CompanyApiRestClient companyApiRestClient;

	@Autowired
	private KafkaTemplate<String, Object> template;

	@Autowired
	private Environment env;

	@Autowired
	StocksMongoDbRepository stocksMongoDbRepository;

	@Override
	public Boolean validateStocksDetails(Stocks stocks, String companycode) {
		logger.info("validateStocksDetails: stocks={}, companycode={}", stocks, companycode);
		Boolean validFlag = Boolean.TRUE;

		// All details fields are be mandatory
		if (stocks == null || stocks.getStockPrice() == null || companycode == null) {
			logger.info("validateStocksDetails: mandatory details are missing.");
			return Boolean.FALSE;
		}

		// Check if the Company Code exists or not
		Company company = companyApiRestClient.getCompanyInfo(companycode);
		if (company == null) {
			logger.info("The Company with code {} does not exists.", companycode);
			return Boolean.FALSE;
		}

		return validFlag;
	}

	@Override
	public void sendToKafka(Stocks stocks, String cRUD) {
		logger.info("sendToKafka: stocks={}, cRUD={}", stocks, cRUD);
		DbSyncTopicStocksDto mySqlMongoSyncTopic2Dto = new DbSyncTopicStocksDto();
		mySqlMongoSyncTopic2Dto.setStocks(stocks);
		mySqlMongoSyncTopic2Dto.setCrudOps(cRUD);
		logger.info("sendToKafka: DbSyncTopicStocksDto={}", mySqlMongoSyncTopic2Dto);
		template.send(env.getProperty("kafka.topic.name"), mySqlMongoSyncTopic2Dto);
	}

	@Override
	public List<com.estockmarket.stock.mongodbentities.Stocks> getStocksList(String companycode, Date startdate,
			Date enddate) {
		logger.info("getStocksList: companycode={}, startdate={}, enddate={}", companycode, startdate, enddate);
		List<com.estockmarket.stock.mongodbentities.Stocks> stocksList = stocksMongoDbRepository
				.getAllStocks(companycode, startdate, enddate);
		logger.info("getStocksList: stocksList={}", stocksList);
		return stocksList;
	}

}
