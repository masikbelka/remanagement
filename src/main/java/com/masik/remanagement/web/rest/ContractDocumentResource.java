package com.masik.remanagement.web.rest;

import com.masik.remanagement.domain.ContractDocument;
import com.masik.remanagement.repository.ContractDocumentRepository;
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
 * REST controller for managing {@link com.masik.remanagement.domain.ContractDocument}.
 */
@RestController
@RequestMapping("/api")
public class ContractDocumentResource {

    private final Logger log = LoggerFactory.getLogger(ContractDocumentResource.class);

    private static final String ENTITY_NAME = "contractDocument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContractDocumentRepository contractDocumentRepository;

    public ContractDocumentResource(ContractDocumentRepository contractDocumentRepository) {
        this.contractDocumentRepository = contractDocumentRepository;
    }

    /**
     * {@code POST  /contract-documents} : Create a new contractDocument.
     *
     * @param contractDocument the contractDocument to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contractDocument, or with status {@code 400 (Bad Request)} if the contractDocument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contract-documents")
    public ResponseEntity<ContractDocument> createContractDocument(@Valid @RequestBody ContractDocument contractDocument) throws URISyntaxException {
        log.debug("REST request to save ContractDocument : {}", contractDocument);
        if (contractDocument.getId() != null) {
            throw new BadRequestAlertException("A new contractDocument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContractDocument result = contractDocumentRepository.save(contractDocument);
        return ResponseEntity.created(new URI("/api/contract-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contract-documents} : Updates an existing contractDocument.
     *
     * @param contractDocument the contractDocument to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contractDocument,
     * or with status {@code 400 (Bad Request)} if the contractDocument is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contractDocument couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contract-documents")
    public ResponseEntity<ContractDocument> updateContractDocument(@Valid @RequestBody ContractDocument contractDocument) throws URISyntaxException {
        log.debug("REST request to update ContractDocument : {}", contractDocument);
        if (contractDocument.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContractDocument result = contractDocumentRepository.save(contractDocument);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contractDocument.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /contract-documents} : get all the contractDocuments.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contractDocuments in body.
     */
    @GetMapping("/contract-documents")
    public ResponseEntity<List<ContractDocument>> getAllContractDocuments(Pageable pageable) {
        log.debug("REST request to get a page of ContractDocuments");
        Page<ContractDocument> page = contractDocumentRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /contract-documents/:id} : get the "id" contractDocument.
     *
     * @param id the id of the contractDocument to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contractDocument, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contract-documents/{id}")
    public ResponseEntity<ContractDocument> getContractDocument(@PathVariable Long id) {
        log.debug("REST request to get ContractDocument : {}", id);
        Optional<ContractDocument> contractDocument = contractDocumentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(contractDocument);
    }

    /**
     * {@code DELETE  /contract-documents/:id} : delete the "id" contractDocument.
     *
     * @param id the id of the contractDocument to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contract-documents/{id}")
    public ResponseEntity<Void> deleteContractDocument(@PathVariable Long id) {
        log.debug("REST request to delete ContractDocument : {}", id);
        contractDocumentRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
