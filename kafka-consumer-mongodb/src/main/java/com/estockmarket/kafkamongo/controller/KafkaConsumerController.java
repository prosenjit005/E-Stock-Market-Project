package com.estockmarket.kafkamongo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RestController;

import com.estockmarket.kafkamongo.entities.Company;
import com.estockmarket.kafkamongo.services.SpringbootMongoDbService;

@RestController
public class KafkaConsumerController {

	List<String> messages = new ArrayList<>();

	@Autowired
	SpringbootMongoDbService springbootMongoDbService;

	@KafkaListener(groupId = "#{'${kafka.topic.name}'}"
			+ "-1", topics = "#{'${kafka.topic.name}'}", containerFactory = "consumerKafkaListenerContainerFactory")
	public Company getJsonMsgFromTopic(com.estockmarket.company.dto.MySqlMongoSyncTopic1Dto mySqlMongoSyncTopic1Dto) {
		Company company = springbootMongoDbService.mongoDbCrudOps(mySqlMongoSyncTopic1Dto);
		return company;
	}

}
