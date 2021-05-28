package com.ms.currency.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ms.currency.model.CurrencyExchange;
import com.ms.currency.repository.CurrencyExchangeRepository;

@RestController
public class CorrencyController {

	@Autowired
	Environment environment;
	
	@Autowired
	CurrencyExchangeRepository currencyExchageRepo;
	
	@GetMapping("currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange getExchangedValue(@PathVariable String from, @PathVariable String to) {
		CurrencyExchange currencyExchange = currencyExchageRepo.findByFromAndTo(from, to);
		if(currencyExchange == null) {
			throw new RuntimeException("Data not available for " + from + " and " + to);
		}
		
//		CurrencyExchange currencyExchange = new CurrencyExchange(1L, "USD", "INR", BigDecimal.valueOf(65));
		String port = environment.getProperty("local.server.port");
		currencyExchange.setEnvironment(port);
		return currencyExchange;
	}
	
}
