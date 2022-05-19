package com.estockmarket.company.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.estockmarket.company.mongodbentities.Company;

public interface CompanyMongoDbRepository extends MongoRepository<Company, Integer> {

	@Query("{companyCode :?0}")
	Company findByCompanyCode(String companyCode);

}
