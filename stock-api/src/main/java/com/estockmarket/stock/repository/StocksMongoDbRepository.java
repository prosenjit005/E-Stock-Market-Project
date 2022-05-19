package com.estockmarket.stock.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.estockmarket.stock.mongodbentities.Stocks;

public interface StocksMongoDbRepository extends MongoRepository<Stocks, Integer> {

	@Query("{ companyCode: ?0, 'stockDateTime' : { $gt: ?1, $lt: ?2 } }")
	List<Stocks> getAllStocks(String companyCode, Date startdate, Date enddate);

}
