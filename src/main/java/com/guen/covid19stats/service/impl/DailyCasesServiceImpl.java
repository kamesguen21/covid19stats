package com.guen.covid19stats.service.impl;

import com.guen.covid19stats.domain.Country;
import com.guen.covid19stats.domain.DailyCases;
import com.guen.covid19stats.repository.DailyCasesRepository;
import com.guen.covid19stats.repository.search.DailyCasesSearchRepository;
import com.guen.covid19stats.service.DailyCasesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing {@link DailyCases}.
 */
@Service
public class DailyCasesServiceImpl implements DailyCasesService {

    private final Logger log = LoggerFactory.getLogger(DailyCasesServiceImpl.class);

    private final DailyCasesRepository dailyCasesRepository;

    private final DailyCasesSearchRepository dailyCasesSearchRepository;

    public DailyCasesServiceImpl(DailyCasesRepository dailyCasesRepository, DailyCasesSearchRepository dailyCasesSearchRepository) {
        this.dailyCasesRepository = dailyCasesRepository;
        this.dailyCasesSearchRepository = dailyCasesSearchRepository;
    }

    @Override
    public DailyCases save(DailyCases dailyCases) {
        log.debug("Request to save DailyCases : {}", dailyCases);
        DailyCases result = dailyCasesRepository.save(dailyCases);
        dailyCasesSearchRepository.save(result);
        return result;
    }

    @Override
    public List<DailyCases> findAll() {
        log.debug("Request to get all DailyCases");
        return dailyCasesRepository.findAll();
    }

    @Override
    public List<DailyCases> findAllByCountry(Country country) {
        return dailyCasesRepository.findAllByCountry(country);
    }

    @Override
    public DailyCases findLastByCountry(Country country) {
        return DailyCasesService.findLast(dailyCasesRepository.findAllByCountry(country));
    }

    @Override
    public List<DailyCases> saveAll(List<DailyCases> dailyCases) {
        return dailyCasesRepository.saveAll(dailyCases);
    }


    @Override
    public Optional<DailyCases> findOne(String id) {
        log.debug("Request to get DailyCases : {}", id);
        return dailyCasesRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete DailyCases : {}", id);
        dailyCasesRepository.deleteById(id);
        dailyCasesSearchRepository.deleteById(id);
    }

    @Override
    public List<DailyCases> search(String query) {
        log.debug("Request to search DailyCases for query {}", query);
        return StreamSupport
            .stream(dailyCasesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
