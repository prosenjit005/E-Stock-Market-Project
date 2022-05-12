package com.estockmarket.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estockmarket.stock.entities.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

	Company findByCompanyCode(String companyCode);

}
