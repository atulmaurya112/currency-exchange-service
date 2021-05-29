package com.ms.currency.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class CircuitBreakerController {

	Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);
	
	@GetMapping("retry-test")
	@Retry(name="custom-one", fallbackMethod="testFallback")
	public String retryTest(@RequestParam String value) {
		logger.info("retry-test api called..!");
		ResponseEntity<String> response = new RestTemplate().getForEntity("http://localhost:8080/test", String.class);
		return response.getBody();
	}
	
	@GetMapping("circuit-breaker")
	@CircuitBreaker(name="custom-two", fallbackMethod="testFallback")
	public String circuitBreaker(@RequestParam String value) {
		logger.info("custom-two api called..!");
		ResponseEntity<String> response = new RestTemplate().getForEntity("http://localhost:8080/test", String.class);
		return response.getBody();
	}
	
	@GetMapping("rate-limiter")
	@RateLimiter(name="custom-three", fallbackMethod="testFallback")
	public String rateLimiter(@RequestParam String value) {
		logger.info("rate-limiter api called..!");
		ResponseEntity<String> response = new RestTemplate().getForEntity("http://localhost:8080/test", String.class);
		return response.getBody();
	}
	
	@GetMapping("bulk-head")
	@Bulkhead(name="custom-four", fallbackMethod="testFallback")
	public String bulkHead(@RequestParam String value) {
		logger.info("bulk-head api called..!");
		ResponseEntity<String> response = new RestTemplate().getForEntity("http://localhost:8080/test", String.class);
		return response.getBody();
	}
	
	public String testFallback(Exception e) {
		return "This is fallback response..!";
	}
	
}
