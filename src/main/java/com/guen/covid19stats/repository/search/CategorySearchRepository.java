package com.guen.covid19stats.repository.search;

import com.guen.covid19stats.domain.Category;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Category} entity.
 */
public interface CategorySearchRepository extends ElasticsearchRepository<Category, String> {
}
