package com.ms.currency.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.currency.model.CurrencyExchange;

public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange, Long>{

	CurrencyExchange findByFromAndTo(String from, String to);
	
}
