package com.estockmarket.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estockmarket.company.entities.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

	Company findByCompanyCode(String companyCode);
	
}
