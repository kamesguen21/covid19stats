package com.guen.covid19stats.repository.search;

import com.guen.covid19stats.domain.CdcNews;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link CdcNews} entity.
 */
public interface CdcNewsSearchRepository extends ElasticsearchRepository<CdcNews, String> {
}
