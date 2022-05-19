package com.estockmarket.company.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estockmarket.company.entities.Company;
import com.estockmarket.company.repository.CompanyMongoDbRepository;
import com.estockmarket.company.repository.CompanyRepository;
import com.estockmarket.company.restclient.StocksApiRestClient;
import com.estockmarket.company.service.CompanyServices;

@RestController
@RequestMapping("/company")
public class CompanyApiController {

	private static final Logger logger = LoggerFactory.getLogger(CompanyApiController.class);

	@Autowired
	private CompanyServices companyServices;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private CompanyMongoDbRepository companyMongoDbRepository;

	@Autowired
	private StocksApiRestClient stocksApiRestClient;

	private String crudC = "C";
	private String crudD = "D";

	@GetMapping("/test")
	public String test() {
		logger.info("CompanyApiController endpoint hit success !!!");
		return "CompanyApiController endpoint hit success !!!";
	}

	@GetMapping("/testStocksApi")
	public String testCompanyApi() {
		logger.info("stocksApiRestClient.test() from CompanyApiController endpoint hit success !!!");
		return stocksApiRestClient.test() + " from CompanyApiController !!";
	}

	@PostMapping("/register")
	public Company registerCompany(@RequestBody Company company) {
		logger.info("registerCompany endpoint hit with input:company={}", company);
		Company companyReturn = null;

		// creating the company in the MySQL DB
		if (Boolean.TRUE.equals(companyServices.validateCompanyDetails(company))) {
			logger.info("registerCompany endpoint: company validation is a success!");
			companyReturn = companyRepository.save(company);
			logger.info("Company Data added successfully with ID={}", company.getId());
		} else {
			logger.info("There is an issue while saving the Company.");
		}

		// publishing in the topic so that MongoDB can also be in sync
		if (null != companyReturn) {
			logger.info("registerCompany endpoint: sending the company data to kafka: CRUD_C!");
			companyServices.sendToKafka(companyReturn, crudC);
		}

		return companyReturn;
	}

	@GetMapping("/info/{companycode}")
	public com.estockmarket.company.mongodbentities.Company getCompanyInfo(@PathVariable String companycode) {
		logger.info("getCompanyInfo endpoint hit with input:companycode={}", companycode);
		// for this Read operation we will use
		// MongoDB instead of MySQL
		com.estockmarket.company.mongodbentities.Company company = companyMongoDbRepository
				.findByCompanyCode(companycode);
		logger.info("getCompanyInfo Return company={}", company);
		return company;
	}

	@GetMapping("/getall")
	public List<com.estockmarket.company.mongodbentities.Company> getAllCompanies() {
		logger.info("getAllCompanies endpoint hit !");
		// for this Read operation we will use
		// MongoDB instead of MySQL
		List<com.estockmarket.company.mongodbentities.Company> companyList = companyMongoDbRepository.findAll();
		logger.info("getAllCompanies Return companyList={}", companyList);
		return companyList;
	}

	@DeleteMapping("/delete/{companycode}")
	public Company deleteCompany(@PathVariable String companycode) {
		logger.info("deleteCompany with companycode={}", companycode);
		Company company = companyServices.getCompanyByCode(companycode);
		if (null != company) {
			logger.info("deleteCompany: Deleting company stocks with id={}", company.getId());
			stocksApiRestClient.deleteCompanyStocks(companycode);

			logger.info("deleteCompany: Deleting company with id={}", company.getId());
			companyRepository.deleteById(company.getId());
		} else {
			logger.info("Company not found for companycode={}", companycode);
		}

		// publishing in the topic so that MongoDB can also be in sync
		if (null != company) {
			logger.info("deleteCompany endpoint: sending the company data to kafka: CRUD_D!");
			companyServices.sendToKafka(company, crudD);
		}
		return company;
	}

}
