package com.masik.remanagement.web.rest;

import com.masik.remanagement.domain.ContractDocumentEntryRow;
import com.masik.remanagement.repository.ContractDocumentEntryRowRepository;
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
 * REST controller for managing {@link com.masik.remanagement.domain.ContractDocumentEntryRow}.
 */
@RestController
@RequestMapping("/api")
public class ContractDocumentEntryRowResource {

    private final Logger log = LoggerFactory.getLogger(ContractDocumentEntryRowResource.class);

    private static final String ENTITY_NAME = "contractDocumentEntryRow";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContractDocumentEntryRowRepository contractDocumentEntryRowRepository;

    public ContractDocumentEntryRowResource(ContractDocumentEntryRowRepository contractDocumentEntryRowRepository) {
        this.contractDocumentEntryRowRepository = contractDocumentEntryRowRepository;
    }

    /**
     * {@code POST  /contract-document-entry-rows} : Create a new contractDocumentEntryRow.
     *
     * @param contractDocumentEntryRow the contractDocumentEntryRow to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contractDocumentEntryRow, or with status {@code 400 (Bad Request)} if the contractDocumentEntryRow has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contract-document-entry-rows")
    public ResponseEntity<ContractDocumentEntryRow> createContractDocumentEntryRow(@RequestBody ContractDocumentEntryRow contractDocumentEntryRow) throws URISyntaxException {
        log.debug("REST request to save ContractDocumentEntryRow : {}", contractDocumentEntryRow);
        if (contractDocumentEntryRow.getId() != null) {
            throw new BadRequestAlertException("A new contractDocumentEntryRow cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContractDocumentEntryRow result = contractDocumentEntryRowRepository.save(contractDocumentEntryRow);
        return ResponseEntity.created(new URI("/api/contract-document-entry-rows/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contract-document-entry-rows} : Updates an existing contractDocumentEntryRow.
     *
     * @param contractDocumentEntryRow the contractDocumentEntryRow to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contractDocumentEntryRow,
     * or with status {@code 400 (Bad Request)} if the contractDocumentEntryRow is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contractDocumentEntryRow couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contract-document-entry-rows")
    public ResponseEntity<ContractDocumentEntryRow> updateContractDocumentEntryRow(@RequestBody ContractDocumentEntryRow contractDocumentEntryRow) throws URISyntaxException {
        log.debug("REST request to update ContractDocumentEntryRow : {}", contractDocumentEntryRow);
        if (contractDocumentEntryRow.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContractDocumentEntryRow result = contractDocumentEntryRowRepository.save(contractDocumentEntryRow);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contractDocumentEntryRow.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /contract-document-entry-rows} : get all the contractDocumentEntryRows.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contractDocumentEntryRows in body.
     */
    @GetMapping("/contract-document-entry-rows")
    public ResponseEntity<List<ContractDocumentEntryRow>> getAllContractDocumentEntryRows(Pageable pageable) {
        log.debug("REST request to get a page of ContractDocumentEntryRows");
        Page<ContractDocumentEntryRow> page = contractDocumentEntryRowRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /contract-document-entry-rows/:id} : get the "id" contractDocumentEntryRow.
     *
     * @param id the id of the contractDocumentEntryRow to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contractDocumentEntryRow, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contract-document-entry-rows/{id}")
    public ResponseEntity<ContractDocumentEntryRow> getContractDocumentEntryRow(@PathVariable Long id) {
        log.debug("REST request to get ContractDocumentEntryRow : {}", id);
        Optional<ContractDocumentEntryRow> contractDocumentEntryRow = contractDocumentEntryRowRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(contractDocumentEntryRow);
    }

    /**
     * {@code DELETE  /contract-document-entry-rows/:id} : delete the "id" contractDocumentEntryRow.
     *
     * @param id the id of the contractDocumentEntryRow to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contract-document-entry-rows/{id}")
    public ResponseEntity<Void> deleteContractDocumentEntryRow(@PathVariable Long id) {
        log.debug("REST request to delete ContractDocumentEntryRow : {}", id);
        contractDocumentEntryRowRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
