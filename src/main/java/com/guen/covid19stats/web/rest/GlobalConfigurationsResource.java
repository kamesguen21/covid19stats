package com.guen.covid19stats.web.rest;

import com.guen.covid19stats.domain.GlobalConfigurations;
import com.guen.covid19stats.service.GlobalConfigurationsService;
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
 * REST controller for managing {@link com.guen.covid19stats.domain.GlobalConfigurations}.
 */
@RestController
@RequestMapping("/api")
public class GlobalConfigurationsResource {

    private final Logger log = LoggerFactory.getLogger(GlobalConfigurationsResource.class);

    private static final String ENTITY_NAME = "globalConfigurations";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GlobalConfigurationsService globalConfigurationsService;

    public GlobalConfigurationsResource(GlobalConfigurationsService globalConfigurationsService) {
        this.globalConfigurationsService = globalConfigurationsService;
    }

    /**
     * {@code POST  /global-configurations} : Create a new globalConfigurations.
     *
     * @param globalConfigurations the globalConfigurations to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new globalConfigurations, or with status {@code 400 (Bad Request)} if the globalConfigurations has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/global-configurations")
    public ResponseEntity<GlobalConfigurations> createGlobalConfigurations(@RequestBody GlobalConfigurations globalConfigurations) throws URISyntaxException {
        log.debug("REST request to save GlobalConfigurations : {}", globalConfigurations);
        if (globalConfigurations.getId() != null) {
            throw new BadRequestAlertException("A new globalConfigurations cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GlobalConfigurations result = globalConfigurationsService.save(globalConfigurations);
        return ResponseEntity.created(new URI("/api/global-configurations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /global-configurations} : Updates an existing globalConfigurations.
     *
     * @param globalConfigurations the globalConfigurations to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated globalConfigurations,
     * or with status {@code 400 (Bad Request)} if the globalConfigurations is not valid,
     * or with status {@code 500 (Internal Server Error)} if the globalConfigurations couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/global-configurations")
    public ResponseEntity<GlobalConfigurations> updateGlobalConfigurations(@RequestBody GlobalConfigurations globalConfigurations) throws URISyntaxException {
        log.debug("REST request to update GlobalConfigurations : {}", globalConfigurations);
        if (globalConfigurations.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GlobalConfigurations result = globalConfigurationsService.save(globalConfigurations);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, globalConfigurations.getId()))
            .body(result);
    }

    /**
     * {@code GET  /global-configurations} : get all the globalConfigurations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of globalConfigurations in body.
     */
    @GetMapping("/global-configurations")
    public List<GlobalConfigurations> getAllGlobalConfigurations() {
        log.debug("REST request to get all GlobalConfigurations");
        return globalConfigurationsService.findAll();
    }

    /**
     * {@code GET  /global-configurations/:id} : get the "id" globalConfigurations.
     *
     * @param id the id of the globalConfigurations to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the globalConfigurations, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/global-configurations/{id}")
    public ResponseEntity<GlobalConfigurations> getGlobalConfigurations(@PathVariable String id) {
        log.debug("REST request to get GlobalConfigurations : {}", id);
        Optional<GlobalConfigurations> globalConfigurations = globalConfigurationsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(globalConfigurations);
    }

    /**
     * {@code DELETE  /global-configurations/:id} : delete the "id" globalConfigurations.
     *
     * @param id the id of the globalConfigurations to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/global-configurations/{id}")
    public ResponseEntity<Void> deleteGlobalConfigurations(@PathVariable String id) {
        log.debug("REST request to delete GlobalConfigurations : {}", id);
        globalConfigurationsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/global-configurations?query=:query} : search for the globalConfigurations corresponding
     * to the query.
     *
     * @param query the query of the globalConfigurations search.
     * @return the result of the search.
     */
    @GetMapping("/_search/global-configurations")
    public List<GlobalConfigurations> searchGlobalConfigurations(@RequestParam String query) {
        log.debug("REST request to search GlobalConfigurations for query {}", query);
        return globalConfigurationsService.search(query);
    }
}
