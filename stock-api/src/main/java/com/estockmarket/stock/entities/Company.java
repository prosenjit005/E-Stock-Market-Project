package com.estockmarket.stock.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "company")
public class Company {

	@Id
	@GeneratedValue
	private Integer id;
	private String companyCode;
	private String companyName;

	private String companyCEO;
	private Double companyTurnover;
	private String companyWebsite;
	private String stockExchange;

}
