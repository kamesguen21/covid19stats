package com.guen.covid19stats.repository;

import com.guen.covid19stats.domain.DailyCases;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the DailyCases entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DailyCasesRepository extends MongoRepository<DailyCases, String> {
}
