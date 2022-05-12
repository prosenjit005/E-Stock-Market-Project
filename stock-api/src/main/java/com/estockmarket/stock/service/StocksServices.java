package com.estockmarket.stock.service;

import com.estockmarket.stock.entities.Stocks;

public interface StocksServices {

	Boolean validateStocksDetails(Stocks stocks, String companycode);

	void sendToKafka(Stocks stocks, String cRUD);

}
