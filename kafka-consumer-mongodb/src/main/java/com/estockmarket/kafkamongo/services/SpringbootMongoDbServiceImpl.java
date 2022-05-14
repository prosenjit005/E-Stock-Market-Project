package com.estockmarket.kafkamongo.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.estockmarket.company.dto.MySqlMongoSyncTopic1Dto;
import com.estockmarket.kafkamongo.entities.Company;
import com.estockmarket.kafkamongo.entities.Stocks;
import com.estockmarket.kafkamongo.repository.CompanyRepository;
import com.estockmarket.kafkamongo.repository.StocksRepository;
import com.estockmarket.stock.dto.DbSyncTopicStocksDto;

@Service
public class SpringbootMongoDbServiceImpl implements SpringbootMongoDbService {

	private static final Logger logger = LoggerFactory.getLogger(SpringbootMongoDbServiceImpl.class);

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private StocksRepository stocksRepository;

	@Autowired
	private MongoOperations mongoOperations;

	@Override
	public Company mongoDbCrudOps(MySqlMongoSyncTopic1Dto mySqlMongoSyncTopic1Dto) {
		logger.info("mongoDbCrudOps input data: MySqlMongoSyncTopic1Dto={}", mySqlMongoSyncTopic1Dto);
		Company company = mySqlMongoSyncTopic1Dto.getCompany();
		String crudOps = mySqlMongoSyncTopic1Dto.getCrudOps();

		if (null != company) {
			if (crudOps.equalsIgnoreCase("C")) {
				company = mongoCreateOps(company);
			} else if (crudOps.equalsIgnoreCase("U")) {
				company = mongoUpdateOps(company);
			} else if (crudOps.equalsIgnoreCase("D")) {
				company = mongoDeleteOps(company);
			}
		}

		return company;
	}

	public Company mongoCreateOps(Company company) {
		logger.info("mongoCreateOps: Company={}", company);
		companyRepository.save(company);
		return company;
	}

	public Company mongoUpdateOps(Company company) {
		logger.info("mongoUpdateOps: Company={}", company);
		companyRepository.save(company);
		return company;
	}

	public Company mongoDeleteOps(Company company) {
		logger.info("mongoDeleteOps: Company={}", company);
		companyRepository.deleteById(company.getId());
		return company;
	}

	@Override
	public Stocks mongoDbCrudOpsStocks(DbSyncTopicStocksDto dbSyncTopicStocksDto) {
		logger.info("mongoDbCrudOpsStocks input data: DbSyncTopicStocksDto={}", dbSyncTopicStocksDto);
		Stocks stocks = dbSyncTopicStocksDto.getStocks();
		String crudOps = dbSyncTopicStocksDto.getCrudOps();

		if (null != stocks) {
			if (crudOps.equalsIgnoreCase("C")) {
				stocks = mongoCreateOps(stocks);
			} else if (crudOps.equalsIgnoreCase("U")) {
				stocks = mongoUpdateOps(stocks);
			} else if (crudOps.equalsIgnoreCase("D")) {
				stocks = mongoDeleteAllOps(stocks);
			}
		}

		return stocks;
	}

	public Stocks mongoCreateOps(Stocks stocks) {
		logger.info("mongoCreateOps: Stocks={}", stocks);
		stocksRepository.save(stocks);
		return stocks;
	}

	public Stocks mongoUpdateOps(Stocks stocks) {
		logger.info("mongoUpdateOps: Stocks={}", stocks);
		stocksRepository.save(stocks);
		return stocks;
	}

	public Stocks mongoDeleteAllOps(Stocks stocks) {
		logger.info("mongoDeleteAllOps: Stocks={}", stocks);
		Query query = new Query();
		query.addCriteria(Criteria.where("companyCode").gte(stocks.getCompanyCode()));
		mongoOperations.findAllAndRemove(query, Stocks.class);
		return stocks;
	}
}
