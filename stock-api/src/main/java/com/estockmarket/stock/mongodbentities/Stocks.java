package com.estockmarket.stock.mongodbentities;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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

	@Id
	@GeneratedValue
	private Integer id;

	private String companyCode;
	private Double stockPrice;
	private Date stockDateTime;

}
