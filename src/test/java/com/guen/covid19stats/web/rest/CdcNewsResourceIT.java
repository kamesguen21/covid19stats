package com.guen.covid19stats.web.rest;

import com.guen.covid19stats.Covid19StatsApp;
import com.guen.covid19stats.domain.CdcNews;
import com.guen.covid19stats.repository.CdcNewsRepository;
import com.guen.covid19stats.repository.search.CdcNewsSearchRepository;
import com.guen.covid19stats.service.CdcNewsService;

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
 * Integration tests for the {@link CdcNewsResource} REST controller.
 */
@SpringBootTest(classes = Covid19StatsApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CdcNewsResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PUB_DATE = "AAAAAAAAAA";
    private static final String UPDATED_PUB_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_BUILD_DATE = "AAAAAAAAAA";
    private static final String UPDATED_LAST_BUILD_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    @Autowired
    private CdcNewsRepository cdcNewsRepository;

    @Autowired
    private CdcNewsService cdcNewsService;

    /**
     * This repository is mocked in the com.guen.covid19stats.repository.search test package.
     *
     * @see com.guen.covid19stats.repository.search.CdcNewsSearchRepositoryMockConfiguration
     */
    @Autowired
    private CdcNewsSearchRepository mockCdcNewsSearchRepository;

    @Autowired
    private MockMvc restCdcNewsMockMvc;

    private CdcNews cdcNews;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CdcNews createEntity() {
        CdcNews cdcNews = new CdcNews()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .pubDate(DEFAULT_PUB_DATE)
            .lastBuildDate(DEFAULT_LAST_BUILD_DATE)
            .link(DEFAULT_LINK);
        return cdcNews;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CdcNews createUpdatedEntity() {
        CdcNews cdcNews = new CdcNews()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .pubDate(UPDATED_PUB_DATE)
            .lastBuildDate(UPDATED_LAST_BUILD_DATE)
            .link(UPDATED_LINK);
        return cdcNews;
    }

    @BeforeEach
    public void initTest() {
        cdcNewsRepository.deleteAll();
        cdcNews = createEntity();
    }

    @Test
    public void createCdcNews() throws Exception {
        int databaseSizeBeforeCreate = cdcNewsRepository.findAll().size();
        // Create the CdcNews
        restCdcNewsMockMvc.perform(post("/api/cdc-news")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cdcNews)))
            .andExpect(status().isCreated());

        // Validate the CdcNews in the database
        List<CdcNews> cdcNewsList = cdcNewsRepository.findAll();
        assertThat(cdcNewsList).hasSize(databaseSizeBeforeCreate + 1);
        CdcNews testCdcNews = cdcNewsList.get(cdcNewsList.size() - 1);
        assertThat(testCdcNews.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testCdcNews.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCdcNews.getPubDate()).isEqualTo(DEFAULT_PUB_DATE);
        assertThat(testCdcNews.getLastBuildDate()).isEqualTo(DEFAULT_LAST_BUILD_DATE);
        assertThat(testCdcNews.getLink()).isEqualTo(DEFAULT_LINK);

        // Validate the CdcNews in Elasticsearch
        verify(mockCdcNewsSearchRepository, times(1)).save(testCdcNews);
    }

    @Test
    public void createCdcNewsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cdcNewsRepository.findAll().size();

        // Create the CdcNews with an existing ID
        cdcNews.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restCdcNewsMockMvc.perform(post("/api/cdc-news")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cdcNews)))
            .andExpect(status().isBadRequest());

        // Validate the CdcNews in the database
        List<CdcNews> cdcNewsList = cdcNewsRepository.findAll();
        assertThat(cdcNewsList).hasSize(databaseSizeBeforeCreate);

        // Validate the CdcNews in Elasticsearch
        verify(mockCdcNewsSearchRepository, times(0)).save(cdcNews);
    }


    @Test
    public void getAllCdcNews() throws Exception {
        // Initialize the database
        cdcNewsRepository.save(cdcNews);

        // Get all the cdcNewsList
        restCdcNewsMockMvc.perform(get("/api/cdc-news?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cdcNews.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].pubDate").value(hasItem(DEFAULT_PUB_DATE)))
            .andExpect(jsonPath("$.[*].lastBuildDate").value(hasItem(DEFAULT_LAST_BUILD_DATE)))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK)));
    }
    
    @Test
    public void getCdcNews() throws Exception {
        // Initialize the database
        cdcNewsRepository.save(cdcNews);

        // Get the cdcNews
        restCdcNewsMockMvc.perform(get("/api/cdc-news/{id}", cdcNews.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cdcNews.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.pubDate").value(DEFAULT_PUB_DATE))
            .andExpect(jsonPath("$.lastBuildDate").value(DEFAULT_LAST_BUILD_DATE))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK));
    }
    @Test
    public void getNonExistingCdcNews() throws Exception {
        // Get the cdcNews
        restCdcNewsMockMvc.perform(get("/api/cdc-news/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCdcNews() throws Exception {
        // Initialize the database
        cdcNewsService.save(cdcNews);

        int databaseSizeBeforeUpdate = cdcNewsRepository.findAll().size();

        // Update the cdcNews
        CdcNews updatedCdcNews = cdcNewsRepository.findById(cdcNews.getId()).get();
        updatedCdcNews
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .pubDate(UPDATED_PUB_DATE)
            .lastBuildDate(UPDATED_LAST_BUILD_DATE)
            .link(UPDATED_LINK);

        restCdcNewsMockMvc.perform(put("/api/cdc-news")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCdcNews)))
            .andExpect(status().isOk());

        // Validate the CdcNews in the database
        List<CdcNews> cdcNewsList = cdcNewsRepository.findAll();
        assertThat(cdcNewsList).hasSize(databaseSizeBeforeUpdate);
        CdcNews testCdcNews = cdcNewsList.get(cdcNewsList.size() - 1);
        assertThat(testCdcNews.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testCdcNews.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCdcNews.getPubDate()).isEqualTo(UPDATED_PUB_DATE);
        assertThat(testCdcNews.getLastBuildDate()).isEqualTo(UPDATED_LAST_BUILD_DATE);
        assertThat(testCdcNews.getLink()).isEqualTo(UPDATED_LINK);

        // Validate the CdcNews in Elasticsearch
        verify(mockCdcNewsSearchRepository, times(2)).save(testCdcNews);
    }

    @Test
    public void updateNonExistingCdcNews() throws Exception {
        int databaseSizeBeforeUpdate = cdcNewsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCdcNewsMockMvc.perform(put("/api/cdc-news")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cdcNews)))
            .andExpect(status().isBadRequest());

        // Validate the CdcNews in the database
        List<CdcNews> cdcNewsList = cdcNewsRepository.findAll();
        assertThat(cdcNewsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CdcNews in Elasticsearch
        verify(mockCdcNewsSearchRepository, times(0)).save(cdcNews);
    }

    @Test
    public void deleteCdcNews() throws Exception {
        // Initialize the database
        cdcNewsService.save(cdcNews);

        int databaseSizeBeforeDelete = cdcNewsRepository.findAll().size();

        // Delete the cdcNews
        restCdcNewsMockMvc.perform(delete("/api/cdc-news/{id}", cdcNews.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CdcNews> cdcNewsList = cdcNewsRepository.findAll();
        assertThat(cdcNewsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CdcNews in Elasticsearch
        verify(mockCdcNewsSearchRepository, times(1)).deleteById(cdcNews.getId());
    }

    @Test
    public void searchCdcNews() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        cdcNewsService.save(cdcNews);
        when(mockCdcNewsSearchRepository.search(queryStringQuery("id:" + cdcNews.getId())))
            .thenReturn(Collections.singletonList(cdcNews));

        // Search the cdcNews
        restCdcNewsMockMvc.perform(get("/api/_search/cdc-news?query=id:" + cdcNews.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cdcNews.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].pubDate").value(hasItem(DEFAULT_PUB_DATE)))
            .andExpect(jsonPath("$.[*].lastBuildDate").value(hasItem(DEFAULT_LAST_BUILD_DATE)))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK)));
    }
}
