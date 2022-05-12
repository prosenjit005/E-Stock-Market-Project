package com.estockmarket.kafkamongo.entities;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "stocks")
public class Stocks {

	private Integer id;

	private String companyCode;
	private Double stockPrice;
	private Date stockDateTime;

}
