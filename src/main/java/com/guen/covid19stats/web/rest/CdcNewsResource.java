package com.guen.covid19stats.web.rest;

import com.guen.covid19stats.domain.CdcNews;
import com.guen.covid19stats.service.CdcNewsService;
import com.guen.covid19stats.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.guen.covid19stats.domain.CdcNews}.
 */
@RestController
@RequestMapping("/api")
public class CdcNewsResource {

    private final Logger log = LoggerFactory.getLogger(CdcNewsResource.class);

    private static final String ENTITY_NAME = "cdcNews";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CdcNewsService cdcNewsService;

    public CdcNewsResource(CdcNewsService cdcNewsService) {
        this.cdcNewsService = cdcNewsService;
    }

    /**
     * {@code POST  /cdc-news} : Create a new cdcNews.
     *
     * @param cdcNews the cdcNews to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cdcNews, or with status {@code 400 (Bad Request)} if the cdcNews has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cdc-news")
    public ResponseEntity<CdcNews> createCdcNews(@RequestBody CdcNews cdcNews) throws URISyntaxException {
        log.debug("REST request to save CdcNews : {}", cdcNews);
        if (cdcNews.getId() != null) {
            throw new BadRequestAlertException("A new cdcNews cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CdcNews result = cdcNewsService.save(cdcNews);
        return ResponseEntity.created(new URI("/api/cdc-news/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /cdc-news} : Updates an existing cdcNews.
     *
     * @param cdcNews the cdcNews to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cdcNews,
     * or with status {@code 400 (Bad Request)} if the cdcNews is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cdcNews couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cdc-news")
    public ResponseEntity<CdcNews> updateCdcNews(@RequestBody CdcNews cdcNews) throws URISyntaxException {
        log.debug("REST request to update CdcNews : {}", cdcNews);
        if (cdcNews.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CdcNews result = cdcNewsService.save(cdcNews);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cdcNews.getId()))
            .body(result);
    }

    /**
     * {@code GET  /cdc-news} : get all the cdcNews.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cdcNews in body.
     */
    @GetMapping("/cdc-news")
    public List<CdcNews> getAllCdcNews() {
        log.debug("REST request to get all CdcNews");
        return cdcNewsService.findAll();
    }

    /**
     * {@code GET  /cdc-news/:id} : get the "id" cdcNews.
     *
     * @param id the id of the cdcNews to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cdcNews, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cdc-news/{id}")
    public ResponseEntity<CdcNews> getCdcNews(@PathVariable String id) {
        log.debug("REST request to get CdcNews : {}", id);
        Optional<CdcNews> cdcNews = cdcNewsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cdcNews);
    }

    /**
     * {@code DELETE  /cdc-news/:id} : delete the "id" cdcNews.
     *
     * @param id the id of the cdcNews to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cdc-news/{id}")
    public ResponseEntity<Void> deleteCdcNews(@PathVariable String id) {
        log.debug("REST request to delete CdcNews : {}", id);
        cdcNewsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/cdc-news?query=:query} : search for the cdcNews corresponding
     * to the query.
     *
     * @param query the query of the cdcNews search.
     * @return the result of the search.
     */
    @GetMapping("/_search/cdc-news")
    public List<CdcNews> searchCdcNews(@RequestParam String query) {
        log.debug("REST request to search CdcNews for query {}", query);
        return cdcNewsService.search(query);
    }
}
