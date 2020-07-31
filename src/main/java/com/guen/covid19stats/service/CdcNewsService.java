package com.guen.covid19stats.service;

import com.guen.covid19stats.domain.CdcNews;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CdcNews}.
 */
public interface CdcNewsService {

    /**
     * Save a cdcNews.
     *
     * @param cdcNews the entity to save.
     * @return the persisted entity.
     */
    CdcNews save(CdcNews cdcNews);

    /**
     * Get all the cdcNews.
     *
     * @return the list of entities.
     */
    List<CdcNews> findAll();


    /**
     * Get the "id" cdcNews.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CdcNews> findOne(String id);

    /**
     * Delete the "id" cdcNews.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    /**
     * Search for the cdcNews corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<CdcNews> search(String query);
}
