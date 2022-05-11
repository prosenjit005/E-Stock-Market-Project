package com.estockmarket.company.service;

import com.estockmarket.company.entities.Company;

public interface CompanyServices {

	Company getCompanyByCode(String companyCode);

	Boolean validateCompanyDetails(Company company);

}
