package com.guen.covid19stats.repository;

import com.guen.covid19stats.domain.Country;
import com.guen.covid19stats.domain.DailyCases;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data MongoDB repository for the DailyCases entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DailyCasesRepository extends MongoRepository<DailyCases, String> {
    List<DailyCases> findAllByCountry(Country country);
}
