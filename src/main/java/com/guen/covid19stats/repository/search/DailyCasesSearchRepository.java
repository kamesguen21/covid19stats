package com.guen.covid19stats.repository.search;

import com.guen.covid19stats.domain.DailyCases;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link DailyCases} entity.
 */
public interface DailyCasesSearchRepository extends ElasticsearchRepository<DailyCases, String> {
}
