package com.estockmarket.kafkamongo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estockmarket.company.dto.MySqlMongoSyncTopic1Dto;
import com.estockmarket.kafkamongo.entities.Company;
import com.estockmarket.kafkamongo.repository.CompanyRepository;

@Service
public class SpringbootMongoDbServiceImpl implements SpringbootMongoDbService {

	@Autowired
	private CompanyRepository companyRepository;

	@Override
	public Company mongoDbCrudOps(MySqlMongoSyncTopic1Dto mySqlMongoSyncTopic1Dto) {
		Company company = mySqlMongoSyncTopic1Dto.getCompany();
		String crudOps = mySqlMongoSyncTopic1Dto.getCrudOps();

		if (null != company) {
			if (crudOps.equalsIgnoreCase("C")) {
				company = mongoCreateOps(company);
			} else if (crudOps.equalsIgnoreCase("U")) {
				company = mongoUpdateOps(company);
			} else if (crudOps.equalsIgnoreCase("D")) {
				company = mongoDeleteOps(company);
			}
		}

		return company;
	}

	public Company mongoCreateOps(Company company) {
		companyRepository.save(company);
		return company;
	}

	public Company mongoUpdateOps(Company company) {
		companyRepository.save(company);
		return company;
	}

	public Company mongoDeleteOps(Company company) {
		companyRepository.deleteById(company.getId());
		return company;
	}

}
