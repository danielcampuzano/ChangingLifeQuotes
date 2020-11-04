package com.experimentality.changinglifequotes.models.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.experimentality.changinglifequotes.models.entity.ChangingLifeQuoteEntity;
import com.experimentality.changinglifequotes.models.service.IQuoteService;

@RestController
@RequestMapping("/api/v1")
public class QuoteRestController {

	@Autowired
	private IQuoteService quoteService;
	
	@GetMapping("/generate-changing-life-quote")
	public ChangingLifeQuoteEntity getRandomQuote() {
		return quoteService.getRandomQuote();
	}
	
}
