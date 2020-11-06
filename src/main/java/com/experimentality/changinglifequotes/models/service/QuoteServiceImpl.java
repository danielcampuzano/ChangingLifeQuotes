package com.experimentality.changinglifequotes.models.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.experimentality.changinglifequotes.models.dao.IQuoteDao;
import com.experimentality.changinglifequotes.models.entity.ChangingLifeQuoteEntity;

@Service
public class QuoteServiceImpl implements IQuoteService {
	
	@Autowired
	private IQuoteDao quoteDao;
	
	@Autowired
	private RestTemplate clientRest;
	
	@Value("${apikey.textToImage}")
	private String apiKeyTextToImg;
	
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
			quote.setImageUrl("");
			quote.setQuote(e.getMessage());
			log.error("There has been an error deleting the quote", e);
		}
		
		return quote;
	}
	
	private String getExternalQuoute() throws Exception {

		final String uri = "http://quotes.rest/qod.json?category=inspire";

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<String> entity = new HttpEntity<String>(headers);

		ResponseEntity<String> response = clientRest.exchange(uri, HttpMethod.GET, entity, String.class);
				
		JSONArray quotesArray  = new JSONObject(response.getBody()).getJSONObject("contents").getJSONArray("quotes");
		JSONObject quote = quotesArray.getJSONObject(0);
		String quoteMsg = quote.getString("quote");
		
		
		return quoteMsg;
	}
	
	private String getImageUrlFromQuote(String quoteMsg) throws Exception {

		final String uri = "https://api.deepai.org/api/text2img";

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set("api-key", apiKeyTextToImg);
		
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		Map<String, String> params = new HashMap<String, String>();
		params.put("text", quoteMsg);

		ResponseEntity<String> response = clientRest.exchange(uri, HttpMethod.GET, entity, String.class, params);
		
		JSONObject jsonObj = new JSONObject(response.getBody());
		String imgUrl = jsonObj.getString("output_url");
		
		return imgUrl;
		
	}

}
