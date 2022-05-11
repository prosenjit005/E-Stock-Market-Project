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
import com.estockmarket.company.repository.CompanyRepository;
import com.estockmarket.company.service.CompanyServices;

@RestController
@RequestMapping("/company")
public class CompanyApiController {

	private static final Logger logger = LoggerFactory.getLogger(CompanyApiController.class);

	@Autowired
	private CompanyServices companyServices;

	@Autowired
	private CompanyRepository companyRepository;

	@GetMapping("/test")
	public String test() {
		return "CompanyApiController endpoint hit success !!!";
	}

	@PostMapping("/register")
	public String registerCompany(@RequestBody Company company) {

		if (companyServices.validateCompanyDetails(company)) {
			companyRepository.save(company);
			return "Company Data added successfully with ID=" + company.getId();
		} else {
			return "There is an issue.";
		}
	}

	@GetMapping("/info/{companycode}")
	public Company getCompanyInfo(@PathVariable String companycode) {
		return companyServices.getCompanyByCode(companycode);
	}

	@GetMapping("/getall")
	public List<Company> getAllCompanies() {
		return companyRepository.findAll();
	}

	@DeleteMapping("/delete/{companycode}")
	public Company deleteCompany(@PathVariable String companycode) {
		logger.info("deleteCompany with companycode={}", companycode);
		Company company = companyServices.getCompanyByCode(companycode);
		if (null != company) {
			logger.info("Deleting company with id={}", company.getId());
			companyRepository.deleteById(company.getId());
		} else {
			logger.info("Company not found for companycode={}", companycode);
		}
		return company;
	}

}
