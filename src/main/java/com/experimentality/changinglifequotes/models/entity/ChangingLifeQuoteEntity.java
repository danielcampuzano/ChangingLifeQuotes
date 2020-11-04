package com.experimentality.changinglifequotes.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CLQ_CHANGING_LIFE_QUOTE")
public class ChangingLifeQuoteEntity implements Serializable {

	private static final long serialVersionUID = 6075316203091762851L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CLQ_SEQ_QUOTE")
	private Long id;
	private String quote;
	private String imageUrl;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getQuote() {
		return quote;
	}
	public void setQuote(String quote) {
		this.quote = quote;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
		
}
