package com.guen.covid19stats.service.impl;

import com.guen.covid19stats.service.CdcNewsService;
import com.guen.covid19stats.domain.CdcNews;
import com.guen.covid19stats.repository.CdcNewsRepository;
import com.guen.covid19stats.repository.search.CdcNewsSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link CdcNews}.
 */
@Service
public class CdcNewsServiceImpl implements CdcNewsService {

    private final Logger log = LoggerFactory.getLogger(CdcNewsServiceImpl.class);

    private final CdcNewsRepository cdcNewsRepository;

    private final CdcNewsSearchRepository cdcNewsSearchRepository;

    public CdcNewsServiceImpl(CdcNewsRepository cdcNewsRepository, CdcNewsSearchRepository cdcNewsSearchRepository) {
        this.cdcNewsRepository = cdcNewsRepository;
        this.cdcNewsSearchRepository = cdcNewsSearchRepository;
    }

    @Override
    public CdcNews save(CdcNews cdcNews) {
        log.debug("Request to save CdcNews : {}", cdcNews);
        CdcNews result = cdcNewsRepository.save(cdcNews);
        cdcNewsSearchRepository.save(result);
        return result;
    }

    @Override
    public List<CdcNews> findAll() {
        log.debug("Request to get all CdcNews");
        return cdcNewsRepository.findAll();
    }


    @Override
    public Optional<CdcNews> findOne(String id) {
        log.debug("Request to get CdcNews : {}", id);
        return cdcNewsRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete CdcNews : {}", id);
        cdcNewsRepository.deleteById(id);
        cdcNewsSearchRepository.deleteById(id);
    }

    @Override
    public List<CdcNews> search(String query) {
        log.debug("Request to search CdcNews for query {}", query);
        return StreamSupport
            .stream(cdcNewsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
