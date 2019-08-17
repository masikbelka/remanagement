package com.masik.remanagement.web.rest;

import com.masik.remanagement.domain.FreePeriod;
import com.masik.remanagement.repository.FreePeriodRepository;
import com.masik.remanagement.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.masik.remanagement.domain.FreePeriod}.
 */
@RestController
@RequestMapping("/api")
public class FreePeriodResource {

    private final Logger log = LoggerFactory.getLogger(FreePeriodResource.class);

    private static final String ENTITY_NAME = "freePeriod";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FreePeriodRepository freePeriodRepository;

    public FreePeriodResource(FreePeriodRepository freePeriodRepository) {
        this.freePeriodRepository = freePeriodRepository;
    }

    /**
     * {@code POST  /free-periods} : Create a new freePeriod.
     *
     * @param freePeriod the freePeriod to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new freePeriod, or with status {@code 400 (Bad Request)} if the freePeriod has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/free-periods")
    public ResponseEntity<FreePeriod> createFreePeriod(@RequestBody FreePeriod freePeriod) throws URISyntaxException {
        log.debug("REST request to save FreePeriod : {}", freePeriod);
        if (freePeriod.getId() != null) {
            throw new BadRequestAlertException("A new freePeriod cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FreePeriod result = freePeriodRepository.save(freePeriod);
        return ResponseEntity.created(new URI("/api/free-periods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /free-periods} : Updates an existing freePeriod.
     *
     * @param freePeriod the freePeriod to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated freePeriod,
     * or with status {@code 400 (Bad Request)} if the freePeriod is not valid,
     * or with status {@code 500 (Internal Server Error)} if the freePeriod couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/free-periods")
    public ResponseEntity<FreePeriod> updateFreePeriod(@RequestBody FreePeriod freePeriod) throws URISyntaxException {
        log.debug("REST request to update FreePeriod : {}", freePeriod);
        if (freePeriod.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FreePeriod result = freePeriodRepository.save(freePeriod);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, freePeriod.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /free-periods} : get all the freePeriods.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of freePeriods in body.
     */
    @GetMapping("/free-periods")
    public ResponseEntity<List<FreePeriod>> getAllFreePeriods(Pageable pageable) {
        log.debug("REST request to get a page of FreePeriods");
        Page<FreePeriod> page = freePeriodRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /free-periods/:id} : get the "id" freePeriod.
     *
     * @param id the id of the freePeriod to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the freePeriod, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/free-periods/{id}")
    public ResponseEntity<FreePeriod> getFreePeriod(@PathVariable Long id) {
        log.debug("REST request to get FreePeriod : {}", id);
        Optional<FreePeriod> freePeriod = freePeriodRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(freePeriod);
    }

    /**
     * {@code DELETE  /free-periods/:id} : delete the "id" freePeriod.
     *
     * @param id the id of the freePeriod to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/free-periods/{id}")
    public ResponseEntity<Void> deleteFreePeriod(@PathVariable Long id) {
        log.debug("REST request to delete FreePeriod : {}", id);
        freePeriodRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
