package com.estockmarket.stock.service;

import java.util.Date;
import java.util.List;

import com.estockmarket.stock.entities.Stocks;

public interface StocksServices {

	Boolean validateStocksDetails(Stocks stocks, String companycode);

	void sendToKafka(Stocks stocks, String cRUD);

	List<com.estockmarket.stock.mongodbentities.Stocks> getStocksList(String companycode, Date startdate, Date enddate);

}
