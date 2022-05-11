package com.estockmarket.kafkamongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.estockmarket.kafkamongo.entities.Company;

public interface CompanyRepository extends MongoRepository<Company, Integer> {

	@Query("{companyCode :?0}")
	Company findByCompanyCode(String companyCode);

}
