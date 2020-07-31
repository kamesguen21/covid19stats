package com.guen.covid19stats.service;

import com.guen.covid19stats.domain.DailyCases;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link DailyCases}.
 */
public interface DailyCasesService {

    /**
     * Save a dailyCases.
     *
     * @param dailyCases the entity to save.
     * @return the persisted entity.
     */
    DailyCases save(DailyCases dailyCases);

    /**
     * Get all the dailyCases.
     *
     * @return the list of entities.
     */
    List<DailyCases> findAll();


    /**
     * Get the "id" dailyCases.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DailyCases> findOne(String id);

    /**
     * Delete the "id" dailyCases.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    /**
     * Search for the dailyCases corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<DailyCases> search(String query);
}
