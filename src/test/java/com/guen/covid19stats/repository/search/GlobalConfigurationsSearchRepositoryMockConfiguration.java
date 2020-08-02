package com.guen.covid19stats.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link GlobalConfigurationsSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class GlobalConfigurationsSearchRepositoryMockConfiguration {

    @MockBean
    private GlobalConfigurationsSearchRepository mockGlobalConfigurationsSearchRepository;

}
