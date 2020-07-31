package com.guen.covid19stats.web.rest;

import com.guen.covid19stats.domain.DailyCases;
import com.guen.covid19stats.service.DailyCasesService;
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
 * REST controller for managing {@link com.guen.covid19stats.domain.DailyCases}.
 */
@RestController
@RequestMapping("/api")
public class DailyCasesResource {

    private final Logger log = LoggerFactory.getLogger(DailyCasesResource.class);

    private static final String ENTITY_NAME = "dailyCases";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DailyCasesService dailyCasesService;

    public DailyCasesResource(DailyCasesService dailyCasesService) {
        this.dailyCasesService = dailyCasesService;
    }

    /**
     * {@code POST  /daily-cases} : Create a new dailyCases.
     *
     * @param dailyCases the dailyCases to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dailyCases, or with status {@code 400 (Bad Request)} if the dailyCases has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/daily-cases")
    public ResponseEntity<DailyCases> createDailyCases(@RequestBody DailyCases dailyCases) throws URISyntaxException {
        log.debug("REST request to save DailyCases : {}", dailyCases);
        if (dailyCases.getId() != null) {
            throw new BadRequestAlertException("A new dailyCases cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DailyCases result = dailyCasesService.save(dailyCases);
        return ResponseEntity.created(new URI("/api/daily-cases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /daily-cases} : Updates an existing dailyCases.
     *
     * @param dailyCases the dailyCases to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dailyCases,
     * or with status {@code 400 (Bad Request)} if the dailyCases is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dailyCases couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/daily-cases")
    public ResponseEntity<DailyCases> updateDailyCases(@RequestBody DailyCases dailyCases) throws URISyntaxException {
        log.debug("REST request to update DailyCases : {}", dailyCases);
        if (dailyCases.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DailyCases result = dailyCasesService.save(dailyCases);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dailyCases.getId()))
            .body(result);
    }

    /**
     * {@code GET  /daily-cases} : get all the dailyCases.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dailyCases in body.
     */
    @GetMapping("/daily-cases")
    public List<DailyCases> getAllDailyCases() {
        log.debug("REST request to get all DailyCases");
        return dailyCasesService.findAll();
    }

    /**
     * {@code GET  /daily-cases/:id} : get the "id" dailyCases.
     *
     * @param id the id of the dailyCases to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dailyCases, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/daily-cases/{id}")
    public ResponseEntity<DailyCases> getDailyCases(@PathVariable String id) {
        log.debug("REST request to get DailyCases : {}", id);
        Optional<DailyCases> dailyCases = dailyCasesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dailyCases);
    }

    /**
     * {@code DELETE  /daily-cases/:id} : delete the "id" dailyCases.
     *
     * @param id the id of the dailyCases to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/daily-cases/{id}")
    public ResponseEntity<Void> deleteDailyCases(@PathVariable String id) {
        log.debug("REST request to delete DailyCases : {}", id);
        dailyCasesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/daily-cases?query=:query} : search for the dailyCases corresponding
     * to the query.
     *
     * @param query the query of the dailyCases search.
     * @return the result of the search.
     */
    @GetMapping("/_search/daily-cases")
    public List<DailyCases> searchDailyCases(@RequestParam String query) {
        log.debug("REST request to search DailyCases for query {}", query);
        return dailyCasesService.search(query);
    }
}
