package com.guen.covid19stats.service;

import com.guen.covid19stats.domain.GlobalConfigurations;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link GlobalConfigurations}.
 */
public interface GlobalConfigurationsService {

    /**
     * Save a globalConfigurations.
     *
     * @param globalConfigurations the entity to save.
     * @return the persisted entity.
     */
    GlobalConfigurations save(GlobalConfigurations globalConfigurations);

    /**
     * Get all the globalConfigurations.
     *
     * @return the list of entities.
     */
    List<GlobalConfigurations> findAll();


    /**
     * Get the "id" globalConfigurations.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GlobalConfigurations> findOne(String id);

    /**
     * Delete the "id" globalConfigurations.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    /**
     * Search for the globalConfigurations corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<GlobalConfigurations> search(String query);
}
