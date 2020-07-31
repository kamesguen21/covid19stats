package com.guen.covid19stats.web.rest;

import com.guen.covid19stats.Covid19StatsApp;
import com.guen.covid19stats.domain.DailyCases;
import com.guen.covid19stats.repository.DailyCasesRepository;
import com.guen.covid19stats.repository.search.DailyCasesSearchRepository;
import com.guen.covid19stats.service.DailyCasesService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.guen.covid19stats.domain.enumeration.Status;
/**
 * Integration tests for the {@link DailyCasesResource} REST controller.
 */
@SpringBootTest(classes = Covid19StatsApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class DailyCasesResourceIT {

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PROVINCE = "AAAAAAAAAA";
    private static final String UPDATED_PROVINCE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_CITY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CITY_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_LAT = 1L;
    private static final Long UPDATED_LAT = 2L;

    private static final Long DEFAULT_LON = 1L;
    private static final Long UPDATED_LON = 2L;

    private static final Integer DEFAULT_CASES = 1;
    private static final Integer UPDATED_CASES = 2;

    private static final Status DEFAULT_STATUS = Status.CONFIRMED;
    private static final Status UPDATED_STATUS = Status.RECOVERED;

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private DailyCasesRepository dailyCasesRepository;

    @Autowired
    private DailyCasesService dailyCasesService;

    /**
     * This repository is mocked in the com.guen.covid19stats.repository.search test package.
     *
     * @see com.guen.covid19stats.repository.search.DailyCasesSearchRepositoryMockConfiguration
     */
    @Autowired
    private DailyCasesSearchRepository mockDailyCasesSearchRepository;

    @Autowired
    private MockMvc restDailyCasesMockMvc;

    private DailyCases dailyCases;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DailyCases createEntity() {
        DailyCases dailyCases = new DailyCases()
            .country(DEFAULT_COUNTRY)
            .countryCode(DEFAULT_COUNTRY_CODE)
            .province(DEFAULT_PROVINCE)
            .city(DEFAULT_CITY)
            .cityCode(DEFAULT_CITY_CODE)
            .lat(DEFAULT_LAT)
            .lon(DEFAULT_LON)
            .cases(DEFAULT_CASES)
            .status(DEFAULT_STATUS)
            .date(DEFAULT_DATE);
        return dailyCases;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DailyCases createUpdatedEntity() {
        DailyCases dailyCases = new DailyCases()
            .country(UPDATED_COUNTRY)
            .countryCode(UPDATED_COUNTRY_CODE)
            .province(UPDATED_PROVINCE)
            .city(UPDATED_CITY)
            .cityCode(UPDATED_CITY_CODE)
            .lat(UPDATED_LAT)
            .lon(UPDATED_LON)
            .cases(UPDATED_CASES)
            .status(UPDATED_STATUS)
            .date(UPDATED_DATE);
        return dailyCases;
    }

    @BeforeEach
    public void initTest() {
        dailyCasesRepository.deleteAll();
        dailyCases = createEntity();
    }

    @Test
    public void createDailyCases() throws Exception {
        int databaseSizeBeforeCreate = dailyCasesRepository.findAll().size();
        // Create the DailyCases
        restDailyCasesMockMvc.perform(post("/api/daily-cases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dailyCases)))
            .andExpect(status().isCreated());

        // Validate the DailyCases in the database
        List<DailyCases> dailyCasesList = dailyCasesRepository.findAll();
        assertThat(dailyCasesList).hasSize(databaseSizeBeforeCreate + 1);
        DailyCases testDailyCases = dailyCasesList.get(dailyCasesList.size() - 1);
        assertThat(testDailyCases.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testDailyCases.getCountryCode()).isEqualTo(DEFAULT_COUNTRY_CODE);
        assertThat(testDailyCases.getProvince()).isEqualTo(DEFAULT_PROVINCE);
        assertThat(testDailyCases.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testDailyCases.getCityCode()).isEqualTo(DEFAULT_CITY_CODE);
        assertThat(testDailyCases.getLat()).isEqualTo(DEFAULT_LAT);
        assertThat(testDailyCases.getLon()).isEqualTo(DEFAULT_LON);
        assertThat(testDailyCases.getCases()).isEqualTo(DEFAULT_CASES);
        assertThat(testDailyCases.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDailyCases.getDate()).isEqualTo(DEFAULT_DATE);

        // Validate the DailyCases in Elasticsearch
        verify(mockDailyCasesSearchRepository, times(1)).save(testDailyCases);
    }

    @Test
    public void createDailyCasesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dailyCasesRepository.findAll().size();

        // Create the DailyCases with an existing ID
        dailyCases.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restDailyCasesMockMvc.perform(post("/api/daily-cases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dailyCases)))
            .andExpect(status().isBadRequest());

        // Validate the DailyCases in the database
        List<DailyCases> dailyCasesList = dailyCasesRepository.findAll();
        assertThat(dailyCasesList).hasSize(databaseSizeBeforeCreate);

        // Validate the DailyCases in Elasticsearch
        verify(mockDailyCasesSearchRepository, times(0)).save(dailyCases);
    }


    @Test
    public void getAllDailyCases() throws Exception {
        // Initialize the database
        dailyCasesRepository.save(dailyCases);

        // Get all the dailyCasesList
        restDailyCasesMockMvc.perform(get("/api/daily-cases?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dailyCases.getId())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE)))
            .andExpect(jsonPath("$.[*].province").value(hasItem(DEFAULT_PROVINCE)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].cityCode").value(hasItem(DEFAULT_CITY_CODE)))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.intValue())))
            .andExpect(jsonPath("$.[*].lon").value(hasItem(DEFAULT_LON.intValue())))
            .andExpect(jsonPath("$.[*].cases").value(hasItem(DEFAULT_CASES)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @Test
    public void getDailyCases() throws Exception {
        // Initialize the database
        dailyCasesRepository.save(dailyCases);

        // Get the dailyCases
        restDailyCasesMockMvc.perform(get("/api/daily-cases/{id}", dailyCases.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dailyCases.getId()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.countryCode").value(DEFAULT_COUNTRY_CODE))
            .andExpect(jsonPath("$.province").value(DEFAULT_PROVINCE))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.cityCode").value(DEFAULT_CITY_CODE))
            .andExpect(jsonPath("$.lat").value(DEFAULT_LAT.intValue()))
            .andExpect(jsonPath("$.lon").value(DEFAULT_LON.intValue()))
            .andExpect(jsonPath("$.cases").value(DEFAULT_CASES))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }
    @Test
    public void getNonExistingDailyCases() throws Exception {
        // Get the dailyCases
        restDailyCasesMockMvc.perform(get("/api/daily-cases/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateDailyCases() throws Exception {
        // Initialize the database
        dailyCasesService.save(dailyCases);

        int databaseSizeBeforeUpdate = dailyCasesRepository.findAll().size();

        // Update the dailyCases
        DailyCases updatedDailyCases = dailyCasesRepository.findById(dailyCases.getId()).get();
        updatedDailyCases
            .country(UPDATED_COUNTRY)
            .countryCode(UPDATED_COUNTRY_CODE)
            .province(UPDATED_PROVINCE)
            .city(UPDATED_CITY)
            .cityCode(UPDATED_CITY_CODE)
            .lat(UPDATED_LAT)
            .lon(UPDATED_LON)
            .cases(UPDATED_CASES)
            .status(UPDATED_STATUS)
            .date(UPDATED_DATE);

        restDailyCasesMockMvc.perform(put("/api/daily-cases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDailyCases)))
            .andExpect(status().isOk());

        // Validate the DailyCases in the database
        List<DailyCases> dailyCasesList = dailyCasesRepository.findAll();
        assertThat(dailyCasesList).hasSize(databaseSizeBeforeUpdate);
        DailyCases testDailyCases = dailyCasesList.get(dailyCasesList.size() - 1);
        assertThat(testDailyCases.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testDailyCases.getCountryCode()).isEqualTo(UPDATED_COUNTRY_CODE);
        assertThat(testDailyCases.getProvince()).isEqualTo(UPDATED_PROVINCE);
        assertThat(testDailyCases.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testDailyCases.getCityCode()).isEqualTo(UPDATED_CITY_CODE);
        assertThat(testDailyCases.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testDailyCases.getLon()).isEqualTo(UPDATED_LON);
        assertThat(testDailyCases.getCases()).isEqualTo(UPDATED_CASES);
        assertThat(testDailyCases.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDailyCases.getDate()).isEqualTo(UPDATED_DATE);

        // Validate the DailyCases in Elasticsearch
        verify(mockDailyCasesSearchRepository, times(2)).save(testDailyCases);
    }

    @Test
    public void updateNonExistingDailyCases() throws Exception {
        int databaseSizeBeforeUpdate = dailyCasesRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDailyCasesMockMvc.perform(put("/api/daily-cases")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dailyCases)))
            .andExpect(status().isBadRequest());

        // Validate the DailyCases in the database
        List<DailyCases> dailyCasesList = dailyCasesRepository.findAll();
        assertThat(dailyCasesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DailyCases in Elasticsearch
        verify(mockDailyCasesSearchRepository, times(0)).save(dailyCases);
    }

    @Test
    public void deleteDailyCases() throws Exception {
        // Initialize the database
        dailyCasesService.save(dailyCases);

        int databaseSizeBeforeDelete = dailyCasesRepository.findAll().size();

        // Delete the dailyCases
        restDailyCasesMockMvc.perform(delete("/api/daily-cases/{id}", dailyCases.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DailyCases> dailyCasesList = dailyCasesRepository.findAll();
        assertThat(dailyCasesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DailyCases in Elasticsearch
        verify(mockDailyCasesSearchRepository, times(1)).deleteById(dailyCases.getId());
    }

    @Test
    public void searchDailyCases() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        dailyCasesService.save(dailyCases);
        when(mockDailyCasesSearchRepository.search(queryStringQuery("id:" + dailyCases.getId())))
            .thenReturn(Collections.singletonList(dailyCases));

        // Search the dailyCases
        restDailyCasesMockMvc.perform(get("/api/_search/daily-cases?query=id:" + dailyCases.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dailyCases.getId())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].countryCode").value(hasItem(DEFAULT_COUNTRY_CODE)))
            .andExpect(jsonPath("$.[*].province").value(hasItem(DEFAULT_PROVINCE)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].cityCode").value(hasItem(DEFAULT_CITY_CODE)))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.intValue())))
            .andExpect(jsonPath("$.[*].lon").value(hasItem(DEFAULT_LON.intValue())))
            .andExpect(jsonPath("$.[*].cases").value(hasItem(DEFAULT_CASES)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
}
