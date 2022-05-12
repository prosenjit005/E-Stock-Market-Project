package com.estockmarket.stock.dto;

import com.estockmarket.stock.entities.Stocks;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DbSyncTopicStocksDto {

	private Stocks stocks;
	private String crudOps;
}
