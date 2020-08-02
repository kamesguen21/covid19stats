package com.guen.covid19stats.service;

import com.guen.covid19stats.domain.Country;
import com.guen.covid19stats.domain.DailyCases;
import com.guen.covid19stats.service.dto.DailyCasesResponseDTO;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link DailyCases}.
 */
public interface DailyCasesService {

    static DailyCases findLast(List<DailyCases> DailyCases) {
        DailyCases lastDailyCase = null;
        for (DailyCases dailyCase : DailyCases) {
            if (lastDailyCase == null || dailyCase.getDate().compareTo(lastDailyCase.getDate()) > 0) {
                lastDailyCase = dailyCase;
            }
        }
        return lastDailyCase;
    }

    static List<DailyCases> mapDtosToEntities(List<DailyCasesResponseDTO> DailyCases, Country country) {
        List<DailyCases> dailyCasesList = new ArrayList<>();
        for (DailyCasesResponseDTO DailyCase : DailyCases) {
            DailyCases cases = new DailyCases();
            cases.setCountry(country);
            cases.setActive(DailyCase.getActive());
            cases.setConfirmed(DailyCase.getConfirmed());
            cases.setDeaths(DailyCase.getDeaths());
            cases.setLat(Double.parseDouble(DailyCase.getLat()));
            cases.setLon(Double.parseDouble(DailyCase.getLon()));
            cases.setRecovered(DailyCase.getRecovered());
            cases.setDate(Instant.parse(DailyCase.getDate()));
            dailyCasesList.add(cases);
        }
        return dailyCasesList;
    }

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
     * Get all the dailyCases by country.
     *
     * @return the list of entities by country.
     */
    List<DailyCases> findAllByCountry(Country country);

    /**
     * Get last dailyCase of country.
     *
     * @return the last entity by country.
     */
    DailyCases findLastByCountry(Country country);

    /**
     * Get last dailyCase of country.
     *
     * @return the last entity by country.
     */
    List<DailyCases> saveAll(List<DailyCases> dailyCases);

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
     * @return the list of entities.
     */
    List<DailyCases> search(String query);

}
