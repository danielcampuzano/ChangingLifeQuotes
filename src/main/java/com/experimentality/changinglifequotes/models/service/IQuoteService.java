package com.experimentality.changinglifequotes.models.service;

import com.experimentality.changinglifequotes.models.entity.ChangingLifeQuoteEntity;

/**
 * Interface with the methods to access the quotes in db
 * @author Daniel Campuzano
 *
 */
public interface IQuoteService {

	ChangingLifeQuoteEntity findQuoteById(Long id);
	
	void saveQuote(ChangingLifeQuoteEntity quote);
	
	void deleteQuote(Long id);
	
	ChangingLifeQuoteEntity getRandomQuote();
}
