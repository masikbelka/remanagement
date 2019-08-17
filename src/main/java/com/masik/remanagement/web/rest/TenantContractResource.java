package com.masik.remanagement.web.rest;

import com.masik.remanagement.domain.TenantContract;
import com.masik.remanagement.repository.TenantContractRepository;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.masik.remanagement.domain.TenantContract}.
 */
@RestController
@RequestMapping("/api")
public class TenantContractResource {

    private final Logger log = LoggerFactory.getLogger(TenantContractResource.class);

    private static final String ENTITY_NAME = "tenantContract";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TenantContractRepository tenantContractRepository;

    public TenantContractResource(TenantContractRepository tenantContractRepository) {
        this.tenantContractRepository = tenantContractRepository;
    }

    /**
     * {@code POST  /tenant-contracts} : Create a new tenantContract.
     *
     * @param tenantContract the tenantContract to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tenantContract, or with status {@code 400 (Bad Request)} if the tenantContract has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tenant-contracts")
    public ResponseEntity<TenantContract> createTenantContract(@Valid @RequestBody TenantContract tenantContract) throws URISyntaxException {
        log.debug("REST request to save TenantContract : {}", tenantContract);
        if (tenantContract.getId() != null) {
            throw new BadRequestAlertException("A new tenantContract cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TenantContract result = tenantContractRepository.save(tenantContract);
        return ResponseEntity.created(new URI("/api/tenant-contracts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tenant-contracts} : Updates an existing tenantContract.
     *
     * @param tenantContract the tenantContract to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tenantContract,
     * or with status {@code 400 (Bad Request)} if the tenantContract is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tenantContract couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tenant-contracts")
    public ResponseEntity<TenantContract> updateTenantContract(@Valid @RequestBody TenantContract tenantContract) throws URISyntaxException {
        log.debug("REST request to update TenantContract : {}", tenantContract);
        if (tenantContract.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TenantContract result = tenantContractRepository.save(tenantContract);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tenantContract.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tenant-contracts} : get all the tenantContracts.
     *

     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tenantContracts in body.
     */
    @GetMapping("/tenant-contracts")
    public ResponseEntity<List<TenantContract>> getAllTenantContracts(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of TenantContracts");
        Page<TenantContract> page;
        if (eagerload) {
            page = tenantContractRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = tenantContractRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tenant-contracts/:id} : get the "id" tenantContract.
     *
     * @param id the id of the tenantContract to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tenantContract, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tenant-contracts/{id}")
    public ResponseEntity<TenantContract> getTenantContract(@PathVariable Long id) {
        log.debug("REST request to get TenantContract : {}", id);
        Optional<TenantContract> tenantContract = tenantContractRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(tenantContract);
    }

    /**
     * {@code GET  /tenant-contracts/:id} : get the "id" tenantContract.
     *
     * @param id the id of the tenantContract to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tenantContract, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tenant-contracts/document/{id}")
    public ResponseEntity<TenantContract> getTenantContractDocument(@PathVariable Long id) {
        log.debug("REST request to get TenantContract : {}", id);
        Optional<TenantContract> tenantContract = tenantContractRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(tenantContract);
    }

    /**
     * {@code DELETE  /tenant-contracts/:id} : delete the "id" tenantContract.
     *
     * @param id the id of the tenantContract to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tenant-contracts/{id}")
    public ResponseEntity<Void> deleteTenantContract(@PathVariable Long id) {
        log.debug("REST request to delete TenantContract : {}", id);
        tenantContractRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
