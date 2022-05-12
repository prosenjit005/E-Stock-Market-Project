package com.estockmarket.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estockmarket.stock.entities.Stocks;

public interface StocksRepository extends JpaRepository<Stocks, Integer> {

}
