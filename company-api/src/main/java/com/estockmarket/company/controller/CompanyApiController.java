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
import com.estockmarket.company.restClient.StocksApiRestClient;
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

	private String CRUD_C = "C";
	private String CRUD_U = "U";
	private String CRUD_D = "D";

	@GetMapping("/test")
	public String test() {
		return "CompanyApiController endpoint hit success !!!";
	}
	
	@GetMapping("/testStocksApi")
	public String testCompanyApi() {
		return stocksApiRestClient.test() + " from CompanyApiController !!";
	}

	@PostMapping("/register")
	public Company registerCompany(@RequestBody Company company) {
		Company companyReturn = null;

		// creating the company in the MySQL DB
		if (companyServices.validateCompanyDetails(company)) {
			companyReturn = companyRepository.save(company);
			logger.info("Company Data added successfully with ID=" + company.getId());
		} else {
			logger.info("There is an issue while saving the Company.");
		}

		// publishing in the topic so that MongoDB can also be in sync
		if (null != companyReturn) {
			companyServices.sendToKafka(companyReturn, CRUD_C);
		}

		return companyReturn;
	}

	@GetMapping("/info/{companycode}")
	public com.estockmarket.company.mongoDbEntities.Company getCompanyInfo(@PathVariable String companycode) {
		// for this Read operation we will use
		// MongoDB instead of MySQL
		com.estockmarket.company.mongoDbEntities.Company company = companyMongoDbRepository
				.findByCompanyCode(companycode);
		return company;
	}

	@GetMapping("/getall")
	public List<com.estockmarket.company.mongoDbEntities.Company> getAllCompanies() {
		// for this Read operation we will use
		// MongoDB instead of MySQL
		List<com.estockmarket.company.mongoDbEntities.Company> companyList = companyMongoDbRepository.findAll();
		return companyList;
	}

	@DeleteMapping("/delete/{companycode}")
	public Company deleteCompany(@PathVariable String companycode) {
		logger.info("deleteCompany with companycode={}", companycode);
		Company company = companyServices.getCompanyByCode(companycode);
		if (null != company) {
			logger.info("Deleting company stocks with id={}", company.getId());
			stocksApiRestClient.deleteCompanyStocks(companycode);
			
			logger.info("Deleting company with id={}", company.getId());
			companyRepository.deleteById(company.getId());
		} else {
			logger.info("Company not found for companycode={}", companycode);
		}

		// publishing in the topic so that MongoDB can also be in sync
		if (null != company) {
			companyServices.sendToKafka(company, CRUD_D);
		}
		return company;
	}

}
