package com.estockmarket.stock.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.estockmarket.stock.dto.DbSyncTopicStocksDto;
import com.estockmarket.stock.entities.Company;
import com.estockmarket.stock.entities.Stocks;
import com.estockmarket.stock.repository.CompanyRepository;

@Service
public class StocksServicesImpl implements StocksServices {

	private static final Logger logger = LoggerFactory.getLogger(StocksServicesImpl.class);

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	private KafkaTemplate<String, Object> template;

	@Autowired
	private Environment env;

	@Override
	public Boolean validateStocksDetails(Stocks stocks, String companycode) {
		Boolean validFlag = Boolean.TRUE;

		// All details fields are be mandatory
		if (stocks == null || stocks.getStockPrice() == null || companycode == null) {
			return Boolean.FALSE;
		}

		// Check if the Company Code exists or not
		Company company = companyRepository.findByCompanyCode(companycode);
		if (company == null) {
			logger.info("The Company with code {} does not exists.", companycode);
			return Boolean.FALSE;
		}

		return validFlag;
	}

	@Override
	public void sendToKafka(Stocks stocks, String cRUD) {
		DbSyncTopicStocksDto mySqlMongoSyncTopic2Dto = new DbSyncTopicStocksDto();
		mySqlMongoSyncTopic2Dto.setStocks(stocks);
		mySqlMongoSyncTopic2Dto.setCrudOps(cRUD);
		template.send(env.getProperty("kafka.topic.name"), mySqlMongoSyncTopic2Dto);
	}

}
