package com.experimentality.changinglifequotes.models.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.experimentality.changinglifequotes.models.dao.IQuoteDao;
import com.experimentality.changinglifequotes.models.entity.ChangingLifeQuoteEntity;

@Service
public class QuoteServiceImpl implements IQuoteService {
	
	@Autowired
	private IQuoteDao quoteDao;
	
	private final Logger log = LoggerFactory.getLogger(QuoteServiceImpl.class);

	@Override
	@Transactional(readOnly = true)
	public ChangingLifeQuoteEntity findQuoteById(Long id) {
		ChangingLifeQuoteEntity quote = new ChangingLifeQuoteEntity();
		try {
			quote = quoteDao.findById(id).orElse(null);
		} catch (Exception e) {
			log.error("There has been an error retrieving the result", e);
		}
		
		return quote;
	}

	@Override
	@Transactional
	public void saveQuote(ChangingLifeQuoteEntity quote) {
		try {
			quoteDao.save(quote);
		} catch (Exception e) {
			log.error("There has been an error saving the quote", e);
		}
		
	}

	@Override
	public void deleteQuote(Long id) {
		try {
			quoteDao.deleteById(id);
		} catch (Exception e) {
			log.error("There has been an error deleting the quote", e);
		}
		
	}

	@Override
	public ChangingLifeQuoteEntity getRandomQuote() {
		ChangingLifeQuoteEntity quote = new ChangingLifeQuoteEntity();
		
		try {
			
			String quoteMsg = getExternalQuoute();
			String imgUrl = getImageUrlFromQuote(quoteMsg);
			
			quote.setImageUrl(imgUrl);
			quote.setQuote(quoteMsg);
			
			saveQuote(quote);
			
		} catch (Exception e) {
			log.error("There has been an error deleting the quote", e);
		}
		
		return quote;
	}
	
	private String getExternalQuoute() {
		return "Quote";
	}
	
	private String getImageUrlFromQuote(String quoteMsg) {
		return "Image";
	}

}
