package com.guen.covid19stats.web.rest;

import com.guen.covid19stats.Covid19StatsApp;
import com.guen.covid19stats.domain.GlobalConfigurations;
import com.guen.covid19stats.repository.GlobalConfigurationsRepository;
import com.guen.covid19stats.repository.search.GlobalConfigurationsSearchRepository;
import com.guen.covid19stats.service.GlobalConfigurationsService;

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

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GlobalConfigurationsResource} REST controller.
 */
@SpringBootTest(classes = Covid19StatsApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class GlobalConfigurationsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DISCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DISCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_HOST = "AAAAAAAAAA";
    private static final String UPDATED_HOST = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private GlobalConfigurationsRepository globalConfigurationsRepository;

    @Autowired
    private GlobalConfigurationsService globalConfigurationsService;

    /**
     * This repository is mocked in the com.guen.covid19stats.repository.search test package.
     *
     * @see com.guen.covid19stats.repository.search.GlobalConfigurationsSearchRepositoryMockConfiguration
     */
    @Autowired
    private GlobalConfigurationsSearchRepository mockGlobalConfigurationsSearchRepository;

    @Autowired
    private MockMvc restGlobalConfigurationsMockMvc;

    private GlobalConfigurations globalConfigurations;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GlobalConfigurations createEntity() {
        GlobalConfigurations globalConfigurations = new GlobalConfigurations()
            .name(DEFAULT_NAME)
            .discription(DEFAULT_DISCRIPTION)
            .host(DEFAULT_HOST)
            .code(DEFAULT_CODE);
        return globalConfigurations;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GlobalConfigurations createUpdatedEntity() {
        GlobalConfigurations globalConfigurations = new GlobalConfigurations()
            .name(UPDATED_NAME)
            .discription(UPDATED_DISCRIPTION)
            .host(UPDATED_HOST)
            .code(UPDATED_CODE);
        return globalConfigurations;
    }

    @BeforeEach
    public void initTest() {
        globalConfigurationsRepository.deleteAll();
        globalConfigurations = createEntity();
    }

    @Test
    public void createGlobalConfigurations() throws Exception {
        int databaseSizeBeforeCreate = globalConfigurationsRepository.findAll().size();
        // Create the GlobalConfigurations
        restGlobalConfigurationsMockMvc.perform(post("/api/global-configurations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(globalConfigurations)))
            .andExpect(status().isCreated());

        // Validate the GlobalConfigurations in the database
        List<GlobalConfigurations> globalConfigurationsList = globalConfigurationsRepository.findAll();
        assertThat(globalConfigurationsList).hasSize(databaseSizeBeforeCreate + 1);
        GlobalConfigurations testGlobalConfigurations = globalConfigurationsList.get(globalConfigurationsList.size() - 1);
        assertThat(testGlobalConfigurations.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGlobalConfigurations.getDiscription()).isEqualTo(DEFAULT_DISCRIPTION);
        assertThat(testGlobalConfigurations.getHost()).isEqualTo(DEFAULT_HOST);
        assertThat(testGlobalConfigurations.getCode()).isEqualTo(DEFAULT_CODE);

        // Validate the GlobalConfigurations in Elasticsearch
        verify(mockGlobalConfigurationsSearchRepository, times(1)).save(testGlobalConfigurations);
    }

    @Test
    public void createGlobalConfigurationsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = globalConfigurationsRepository.findAll().size();

        // Create the GlobalConfigurations with an existing ID
        globalConfigurations.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restGlobalConfigurationsMockMvc.perform(post("/api/global-configurations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(globalConfigurations)))
            .andExpect(status().isBadRequest());

        // Validate the GlobalConfigurations in the database
        List<GlobalConfigurations> globalConfigurationsList = globalConfigurationsRepository.findAll();
        assertThat(globalConfigurationsList).hasSize(databaseSizeBeforeCreate);

        // Validate the GlobalConfigurations in Elasticsearch
        verify(mockGlobalConfigurationsSearchRepository, times(0)).save(globalConfigurations);
    }


    @Test
    public void getAllGlobalConfigurations() throws Exception {
        // Initialize the database
        globalConfigurationsRepository.save(globalConfigurations);

        // Get all the globalConfigurationsList
        restGlobalConfigurationsMockMvc.perform(get("/api/global-configurations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(globalConfigurations.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].discription").value(hasItem(DEFAULT_DISCRIPTION)))
            .andExpect(jsonPath("$.[*].host").value(hasItem(DEFAULT_HOST)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }
    
    @Test
    public void getGlobalConfigurations() throws Exception {
        // Initialize the database
        globalConfigurationsRepository.save(globalConfigurations);

        // Get the globalConfigurations
        restGlobalConfigurationsMockMvc.perform(get("/api/global-configurations/{id}", globalConfigurations.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(globalConfigurations.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.discription").value(DEFAULT_DISCRIPTION))
            .andExpect(jsonPath("$.host").value(DEFAULT_HOST))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }
    @Test
    public void getNonExistingGlobalConfigurations() throws Exception {
        // Get the globalConfigurations
        restGlobalConfigurationsMockMvc.perform(get("/api/global-configurations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateGlobalConfigurations() throws Exception {
        // Initialize the database
        globalConfigurationsService.save(globalConfigurations);

        int databaseSizeBeforeUpdate = globalConfigurationsRepository.findAll().size();

        // Update the globalConfigurations
        GlobalConfigurations updatedGlobalConfigurations = globalConfigurationsRepository.findById(globalConfigurations.getId()).get();
        updatedGlobalConfigurations
            .name(UPDATED_NAME)
            .discription(UPDATED_DISCRIPTION)
            .host(UPDATED_HOST)
            .code(UPDATED_CODE);

        restGlobalConfigurationsMockMvc.perform(put("/api/global-configurations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGlobalConfigurations)))
            .andExpect(status().isOk());

        // Validate the GlobalConfigurations in the database
        List<GlobalConfigurations> globalConfigurationsList = globalConfigurationsRepository.findAll();
        assertThat(globalConfigurationsList).hasSize(databaseSizeBeforeUpdate);
        GlobalConfigurations testGlobalConfigurations = globalConfigurationsList.get(globalConfigurationsList.size() - 1);
        assertThat(testGlobalConfigurations.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGlobalConfigurations.getDiscription()).isEqualTo(UPDATED_DISCRIPTION);
        assertThat(testGlobalConfigurations.getHost()).isEqualTo(UPDATED_HOST);
        assertThat(testGlobalConfigurations.getCode()).isEqualTo(UPDATED_CODE);

        // Validate the GlobalConfigurations in Elasticsearch
        verify(mockGlobalConfigurationsSearchRepository, times(2)).save(testGlobalConfigurations);
    }

    @Test
    public void updateNonExistingGlobalConfigurations() throws Exception {
        int databaseSizeBeforeUpdate = globalConfigurationsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGlobalConfigurationsMockMvc.perform(put("/api/global-configurations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(globalConfigurations)))
            .andExpect(status().isBadRequest());

        // Validate the GlobalConfigurations in the database
        List<GlobalConfigurations> globalConfigurationsList = globalConfigurationsRepository.findAll();
        assertThat(globalConfigurationsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the GlobalConfigurations in Elasticsearch
        verify(mockGlobalConfigurationsSearchRepository, times(0)).save(globalConfigurations);
    }

    @Test
    public void deleteGlobalConfigurations() throws Exception {
        // Initialize the database
        globalConfigurationsService.save(globalConfigurations);

        int databaseSizeBeforeDelete = globalConfigurationsRepository.findAll().size();

        // Delete the globalConfigurations
        restGlobalConfigurationsMockMvc.perform(delete("/api/global-configurations/{id}", globalConfigurations.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GlobalConfigurations> globalConfigurationsList = globalConfigurationsRepository.findAll();
        assertThat(globalConfigurationsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the GlobalConfigurations in Elasticsearch
        verify(mockGlobalConfigurationsSearchRepository, times(1)).deleteById(globalConfigurations.getId());
    }

    @Test
    public void searchGlobalConfigurations() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        globalConfigurationsService.save(globalConfigurations);
        when(mockGlobalConfigurationsSearchRepository.search(queryStringQuery("id:" + globalConfigurations.getId())))
            .thenReturn(Collections.singletonList(globalConfigurations));

        // Search the globalConfigurations
        restGlobalConfigurationsMockMvc.perform(get("/api/_search/global-configurations?query=id:" + globalConfigurations.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(globalConfigurations.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].discription").value(hasItem(DEFAULT_DISCRIPTION)))
            .andExpect(jsonPath("$.[*].host").value(hasItem(DEFAULT_HOST)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }
}
