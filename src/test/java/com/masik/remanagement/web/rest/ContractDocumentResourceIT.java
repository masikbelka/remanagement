package com.masik.remanagement.web.rest;

import com.masik.remanagement.RemanagementApp;
import com.masik.remanagement.domain.ContractDocument;
import com.masik.remanagement.repository.ContractDocumentRepository;
import com.masik.remanagement.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.masik.remanagement.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.masik.remanagement.domain.enumeration.ContractDocumentType;
/**
 * Integration tests for the {@link ContractDocumentResource} REST controller.
 */
@SpringBootTest(classes = RemanagementApp.class)
public class ContractDocumentResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final ContractDocumentType DEFAULT_TYPE = ContractDocumentType.LEASE;
    private static final ContractDocumentType UPDATED_TYPE = ContractDocumentType.RENT;

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    @Autowired
    private ContractDocumentRepository contractDocumentRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restContractDocumentMockMvc;

    private ContractDocument contractDocument;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContractDocumentResource contractDocumentResource = new ContractDocumentResource(contractDocumentRepository);
        this.restContractDocumentMockMvc = MockMvcBuilders.standaloneSetup(contractDocumentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContractDocument createEntity(EntityManager em) {
        ContractDocument contractDocument = new ContractDocument()
            .code(DEFAULT_CODE)
            .type(DEFAULT_TYPE)
            .fileName(DEFAULT_FILE_NAME);
        return contractDocument;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContractDocument createUpdatedEntity(EntityManager em) {
        ContractDocument contractDocument = new ContractDocument()
            .code(UPDATED_CODE)
            .type(UPDATED_TYPE)
            .fileName(UPDATED_FILE_NAME);
        return contractDocument;
    }

    @BeforeEach
    public void initTest() {
        contractDocument = createEntity(em);
    }

    @Test
    @Transactional
    public void createContractDocument() throws Exception {
        int databaseSizeBeforeCreate = contractDocumentRepository.findAll().size();

        // Create the ContractDocument
        restContractDocumentMockMvc.perform(post("/api/contract-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractDocument)))
            .andExpect(status().isCreated());

        // Validate the ContractDocument in the database
        List<ContractDocument> contractDocumentList = contractDocumentRepository.findAll();
        assertThat(contractDocumentList).hasSize(databaseSizeBeforeCreate + 1);
        ContractDocument testContractDocument = contractDocumentList.get(contractDocumentList.size() - 1);
        assertThat(testContractDocument.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testContractDocument.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testContractDocument.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
    }

    @Test
    @Transactional
    public void createContractDocumentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contractDocumentRepository.findAll().size();

        // Create the ContractDocument with an existing ID
        contractDocument.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContractDocumentMockMvc.perform(post("/api/contract-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractDocument)))
            .andExpect(status().isBadRequest());

        // Validate the ContractDocument in the database
        List<ContractDocument> contractDocumentList = contractDocumentRepository.findAll();
        assertThat(contractDocumentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllContractDocuments() throws Exception {
        // Initialize the database
        contractDocumentRepository.saveAndFlush(contractDocument);

        // Get all the contractDocumentList
        restContractDocumentMockMvc.perform(get("/api/contract-documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contractDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getContractDocument() throws Exception {
        // Initialize the database
        contractDocumentRepository.saveAndFlush(contractDocument);

        // Get the contractDocument
        restContractDocumentMockMvc.perform(get("/api/contract-documents/{id}", contractDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contractDocument.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingContractDocument() throws Exception {
        // Get the contractDocument
        restContractDocumentMockMvc.perform(get("/api/contract-documents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContractDocument() throws Exception {
        // Initialize the database
        contractDocumentRepository.saveAndFlush(contractDocument);

        int databaseSizeBeforeUpdate = contractDocumentRepository.findAll().size();

        // Update the contractDocument
        ContractDocument updatedContractDocument = contractDocumentRepository.findById(contractDocument.getId()).get();
        // Disconnect from session so that the updates on updatedContractDocument are not directly saved in db
        em.detach(updatedContractDocument);
        updatedContractDocument
            .code(UPDATED_CODE)
            .type(UPDATED_TYPE)
            .fileName(UPDATED_FILE_NAME);

        restContractDocumentMockMvc.perform(put("/api/contract-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedContractDocument)))
            .andExpect(status().isOk());

        // Validate the ContractDocument in the database
        List<ContractDocument> contractDocumentList = contractDocumentRepository.findAll();
        assertThat(contractDocumentList).hasSize(databaseSizeBeforeUpdate);
        ContractDocument testContractDocument = contractDocumentList.get(contractDocumentList.size() - 1);
        assertThat(testContractDocument.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testContractDocument.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testContractDocument.getFileName()).isEqualTo(UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingContractDocument() throws Exception {
        int databaseSizeBeforeUpdate = contractDocumentRepository.findAll().size();

        // Create the ContractDocument

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContractDocumentMockMvc.perform(put("/api/contract-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractDocument)))
            .andExpect(status().isBadRequest());

        // Validate the ContractDocument in the database
        List<ContractDocument> contractDocumentList = contractDocumentRepository.findAll();
        assertThat(contractDocumentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContractDocument() throws Exception {
        // Initialize the database
        contractDocumentRepository.saveAndFlush(contractDocument);

        int databaseSizeBeforeDelete = contractDocumentRepository.findAll().size();

        // Delete the contractDocument
        restContractDocumentMockMvc.perform(delete("/api/contract-documents/{id}", contractDocument.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContractDocument> contractDocumentList = contractDocumentRepository.findAll();
        assertThat(contractDocumentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContractDocument.class);
        ContractDocument contractDocument1 = new ContractDocument();
        contractDocument1.setId(1L);
        ContractDocument contractDocument2 = new ContractDocument();
        contractDocument2.setId(contractDocument1.getId());
        assertThat(contractDocument1).isEqualTo(contractDocument2);
        contractDocument2.setId(2L);
        assertThat(contractDocument1).isNotEqualTo(contractDocument2);
        contractDocument1.setId(null);
        assertThat(contractDocument1).isNotEqualTo(contractDocument2);
    }
}
