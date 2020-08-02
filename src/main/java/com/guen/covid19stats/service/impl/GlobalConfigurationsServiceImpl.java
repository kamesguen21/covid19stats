package com.guen.covid19stats.service.impl;

import com.guen.covid19stats.service.GlobalConfigurationsService;
import com.guen.covid19stats.domain.GlobalConfigurations;
import com.guen.covid19stats.repository.GlobalConfigurationsRepository;
import com.guen.covid19stats.repository.search.GlobalConfigurationsSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link GlobalConfigurations}.
 */
@Service
public class GlobalConfigurationsServiceImpl implements GlobalConfigurationsService {

    private final Logger log = LoggerFactory.getLogger(GlobalConfigurationsServiceImpl.class);

    private final GlobalConfigurationsRepository globalConfigurationsRepository;

    private final GlobalConfigurationsSearchRepository globalConfigurationsSearchRepository;

    public GlobalConfigurationsServiceImpl(GlobalConfigurationsRepository globalConfigurationsRepository, GlobalConfigurationsSearchRepository globalConfigurationsSearchRepository) {
        this.globalConfigurationsRepository = globalConfigurationsRepository;
        this.globalConfigurationsSearchRepository = globalConfigurationsSearchRepository;
    }

    @Override
    public GlobalConfigurations save(GlobalConfigurations globalConfigurations) {
        log.debug("Request to save GlobalConfigurations : {}", globalConfigurations);
        GlobalConfigurations result = globalConfigurationsRepository.save(globalConfigurations);
        globalConfigurationsSearchRepository.save(result);
        return result;
    }

    @Override
    public List<GlobalConfigurations> findAll() {
        log.debug("Request to get all GlobalConfigurations");
        return globalConfigurationsRepository.findAll();
    }


    @Override
    public Optional<GlobalConfigurations> findOne(String id) {
        log.debug("Request to get GlobalConfigurations : {}", id);
        return globalConfigurationsRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete GlobalConfigurations : {}", id);
        globalConfigurationsRepository.deleteById(id);
        globalConfigurationsSearchRepository.deleteById(id);
    }

    @Override
    public List<GlobalConfigurations> search(String query) {
        log.debug("Request to search GlobalConfigurations for query {}", query);
        return StreamSupport
            .stream(globalConfigurationsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
