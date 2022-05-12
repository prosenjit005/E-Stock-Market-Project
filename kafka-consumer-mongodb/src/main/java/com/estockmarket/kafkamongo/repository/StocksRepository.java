package com.estockmarket.kafkamongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.estockmarket.kafkamongo.entities.Stocks;

public interface StocksRepository extends MongoRepository<Stocks, Integer> {

}
