package com.guen.covid19stats.repository;

import com.guen.covid19stats.domain.CdcNews;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the CdcNews entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CdcNewsRepository extends MongoRepository<CdcNews, String> {
}
