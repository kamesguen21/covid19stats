package com.guen.covid19stats.repository.search;

import com.guen.covid19stats.domain.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Item} entity.
 */
public interface ItemSearchRepository extends ElasticsearchRepository<Item, String> {
}
