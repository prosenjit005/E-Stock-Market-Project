package com.estockmarket.company.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.estockmarket.company.dto.MySqlMongoSyncTopic1Dto;
import com.estockmarket.company.entities.Company;
import com.estockmarket.company.repository.CompanyRepository;

@Service
public class CompanyServicesImpl implements CompanyServices {

	private static final Logger logger = LoggerFactory.getLogger(CompanyServicesImpl.class);

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private KafkaTemplate<String, Object> template;

	@Autowired
	private Environment env;

	@Override
	public Company getCompanyByCode(String companyCode) {
		Company company = null;
		logger.info("getCompanyByCode: companyCode={}", companyCode);
		company = companyRepository.findByCompanyCode(companyCode);
		logger.info("getCompanyByCode: company={}", null != company ? company.toString() : null);
		return company;
	}

	@Override
	public Boolean validateCompanyDetails(Company company) {
		logger.info("validateCompanyDetails: input company={}", company);
		Boolean validFlag = Boolean.TRUE;

		// All details fields are be mandatory
		if (company == null || company.getCompanyCode() == null || company.getCompanyName() == null
				|| company.getCompanyCEO() == null || company.getCompanyTurnover() == null
				|| company.getCompanyWebsite() == null || company.getStockExchange() == null) {
			logger.info("validateCompanyDetails: mandatory details are missing");
			validFlag = Boolean.FALSE;
			return validFlag;
		}

		// Company Code must be unique
		if (getCompanyByCode(company.getCompanyCode()) != null) {
			logger.info("The company already exists for the code={}", company.getCompanyCode());
			validFlag = Boolean.FALSE;
			return validFlag;
		}

		// Company Turnover must be greater than 10Cr
		if (Boolean.TRUE.equals(validFlag) && (company.getCompanyTurnover() < 10)) {
			logger.info("validateCompanyDetails: Company Turnover must be greater than 10Cr.");
			validFlag = Boolean.FALSE;
			return validFlag;
		}

		return validFlag;
	}

	@Override
	public void sendToKafka(Company company, String cRUD) {
		logger.info("sendToKafka: input company={}, CRUD={}", company, cRUD);
		MySqlMongoSyncTopic1Dto mySqlMongoSyncTopic1Dto = new MySqlMongoSyncTopic1Dto();
		mySqlMongoSyncTopic1Dto.setCompany(company);
		mySqlMongoSyncTopic1Dto.setCrudOps(cRUD);
		logger.info("sendToKafka: sending to Kafka mySqlMongoSyncTopic1Dto={}", mySqlMongoSyncTopic1Dto);
		if (null != env && null != env.getProperty("kafka.topic.name")) {
			template.send(env.getProperty("kafka.topic.name"), mySqlMongoSyncTopic1Dto);
			logger.info("sendToKafka: success!");
		}
	}

}
