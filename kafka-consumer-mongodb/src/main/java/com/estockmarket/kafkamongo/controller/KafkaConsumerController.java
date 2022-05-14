package com.estockmarket.kafkamongo.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RestController;

import com.estockmarket.kafkamongo.entities.Company;
import com.estockmarket.kafkamongo.entities.Stocks;
import com.estockmarket.kafkamongo.services.SpringbootMongoDbService;

@RestController
public class KafkaConsumerController {

	private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerController.class);

	List<String> messages = new ArrayList<>();

	@Autowired
	SpringbootMongoDbService springbootMongoDbService;

	@KafkaListener(groupId = "#{'${kafka.topic.name}'}"
			+ "-1", topics = "#{'${kafka.topic.name}'}", containerFactory = "consumerKafkaListenerContainerFactory")
	public Company getJsonMsgFromTopic(com.estockmarket.company.dto.MySqlMongoSyncTopic1Dto mySqlMongoSyncTopic1Dto) {
		logger.info("Kafka Topic=dbSynTopicCompany received data: MySqlMongoSyncTopic1Dto={}",
				mySqlMongoSyncTopic1Dto.toString());
		Company company = springbootMongoDbService.mongoDbCrudOps(mySqlMongoSyncTopic1Dto);
		logger.info("Kafka Topic=dbSynTopicCompany Return data: company={}", company);
		return company;
	}

	@KafkaListener(groupId = "#{'${kafka.topic.name}'}"
			+ "-1", topics = "#{'${kafka.topic.name.stocks}'}", containerFactory = "consumerKafkaListenerContainerFactory")
	public Stocks getJsonMsgFromTopicStocks(com.estockmarket.stock.dto.DbSyncTopicStocksDto dbSyncTopicStocksDto) {
		logger.info("Kafka Topic=dbSyncTopicStocks received data: DbSyncTopicStocksDto={}",
				dbSyncTopicStocksDto.toString());
		Stocks stocks = springbootMongoDbService.mongoDbCrudOpsStocks(dbSyncTopicStocksDto);
		logger.info("Kafka Topic=dbSyncTopicStocks Return data: stocks={}", stocks);
		return stocks;
	}

}
