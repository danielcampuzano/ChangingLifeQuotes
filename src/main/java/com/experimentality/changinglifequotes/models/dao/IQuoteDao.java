package com.experimentality.changinglifequotes.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.experimentality.changinglifequotes.models.entity.ChangingLifeQuoteEntity;

public interface IQuoteDao extends CrudRepository<ChangingLifeQuoteEntity, Long> {

}
