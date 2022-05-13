package com.estockmarket.stock.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.estockmarket.stock.entities.Stocks;

@Transactional
public interface StocksRepository extends JpaRepository<Stocks, Integer> {

	@Modifying
	@Query(value = "DELETE FROM Stocks s WHERE s.companyCode = :companyCode")
	int deleteAllByCompanyCode(@Param("companyCode") String companyCode);

	@Query(value = "SELECT s FROM Stocks s WHERE s.companyCode = :companyCode")
	List<Stocks> findByCompanyCode(@Param("companyCode") String companyCode);

}
