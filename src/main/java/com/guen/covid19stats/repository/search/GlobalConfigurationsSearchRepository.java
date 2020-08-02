package com.guen.covid19stats.repository.search;

import com.guen.covid19stats.domain.GlobalConfigurations;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link GlobalConfigurations} entity.
 */
public interface GlobalConfigurationsSearchRepository extends ElasticsearchRepository<GlobalConfigurations, String> {
}
