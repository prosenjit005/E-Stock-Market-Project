package com.estockmarket.kafkamongo.services;

import com.estockmarket.kafkamongo.entities.Company;

public interface SpringbootMongoDbService {

	Company mongoDbCrudOps(com.estockmarket.company.dto.MySqlMongoSyncTopic1Dto mySqlMongoSyncTopic1Dto);

}
