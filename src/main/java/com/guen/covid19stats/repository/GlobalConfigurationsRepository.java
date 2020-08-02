package com.guen.covid19stats.repository;

import com.guen.covid19stats.domain.GlobalConfigurations;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the GlobalConfigurations entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GlobalConfigurationsRepository extends MongoRepository<GlobalConfigurations, String> {
}
