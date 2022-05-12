package com.estockmarket.kafkamongo.services;

import com.estockmarket.kafkamongo.entities.Company;
import com.estockmarket.kafkamongo.entities.Stocks;
import com.estockmarket.stock.dto.DbSyncTopicStocksDto;

public interface SpringbootMongoDbService {

	Company mongoDbCrudOps(com.estockmarket.company.dto.MySqlMongoSyncTopic1Dto mySqlMongoSyncTopic1Dto);

	Stocks mongoDbCrudOpsStocks(DbSyncTopicStocksDto dbSyncTopicStocksDto);

}
