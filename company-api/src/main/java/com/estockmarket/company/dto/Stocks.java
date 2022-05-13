package com.estockmarket.company.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Stocks {

	private Integer id;

	private String companyCode;
	private Double stockPrice;
	private Date stockDateTime;

}
