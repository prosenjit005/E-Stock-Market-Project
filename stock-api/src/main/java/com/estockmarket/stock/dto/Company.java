package com.estockmarket.stock.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Company {

	private Integer id;
	private String companyCode;
	private String companyName;

	private String companyCEO;
	private Double companyTurnover;
	private String companyWebsite;
	private String stockExchange;

}
