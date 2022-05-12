package com.estockmarket.stock.entities;

import java.util.Date;

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
@Table(name = "stocks")
public class Stocks {

	@Id
	@GeneratedValue
	private Integer id;

	private String companyCode;
	private Double stockPrice;
	private Date stockDateTime;

}
