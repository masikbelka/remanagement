package com.masik.remanagement.web.rest;

import com.masik.remanagement.RemanagementApp;
import com.masik.remanagement.domain.ContractDocumentEntryRow;
import com.masik.remanagement.repository.ContractDocumentEntryRowRepository;
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

/**
 * Integration tests for the {@link ContractDocumentEntryRowResource} REST controller.
 */
@SpringBootTest(classes = RemanagementApp.class)
public class ContractDocumentEntryRowResourceIT {

    private static final Integer DEFAULT_POSITION = 1;
    private static final Integer UPDATED_POSITION = 2;
    private static final Integer SMALLER_POSITION = 1 - 1;

    private static final String DEFAULT_TEXT_EN = "AAAAAAAAAA";
    private static final String UPDATED_TEXT_EN = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT_AR = "AAAAAAAAAA";
    private static final String UPDATED_TEXT_AR = "BBBBBBBBBB";

    @Autowired
    private ContractDocumentEntryRowRepository contractDocumentEntryRowRepository;

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

    private MockMvc restContractDocumentEntryRowMockMvc;

    private ContractDocumentEntryRow contractDocumentEntryRow;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContractDocumentEntryRowResource contractDocumentEntryRowResource = new ContractDocumentEntryRowResource(contractDocumentEntryRowRepository);
        this.restContractDocumentEntryRowMockMvc = MockMvcBuilders.standaloneSetup(contractDocumentEntryRowResource)
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
    public static ContractDocumentEntryRow createEntity(EntityManager em) {
        ContractDocumentEntryRow contractDocumentEntryRow = new ContractDocumentEntryRow()
            .position(DEFAULT_POSITION)
            .textEn(DEFAULT_TEXT_EN)
            .textAr(DEFAULT_TEXT_AR);
        return contractDocumentEntryRow;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContractDocumentEntryRow createUpdatedEntity(EntityManager em) {
        ContractDocumentEntryRow contractDocumentEntryRow = new ContractDocumentEntryRow()
            .position(UPDATED_POSITION)
            .textEn(UPDATED_TEXT_EN)
            .textAr(UPDATED_TEXT_AR);
        return contractDocumentEntryRow;
    }

    @BeforeEach
    public void initTest() {
        contractDocumentEntryRow = createEntity(em);
    }

    @Test
    @Transactional
    public void createContractDocumentEntryRow() throws Exception {
        int databaseSizeBeforeCreate = contractDocumentEntryRowRepository.findAll().size();

        // Create the ContractDocumentEntryRow
        restContractDocumentEntryRowMockMvc.perform(post("/api/contract-document-entry-rows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractDocumentEntryRow)))
            .andExpect(status().isCreated());

        // Validate the ContractDocumentEntryRow in the database
        List<ContractDocumentEntryRow> contractDocumentEntryRowList = contractDocumentEntryRowRepository.findAll();
        assertThat(contractDocumentEntryRowList).hasSize(databaseSizeBeforeCreate + 1);
        ContractDocumentEntryRow testContractDocumentEntryRow = contractDocumentEntryRowList.get(contractDocumentEntryRowList.size() - 1);
        assertThat(testContractDocumentEntryRow.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testContractDocumentEntryRow.getTextEn()).isEqualTo(DEFAULT_TEXT_EN);
        assertThat(testContractDocumentEntryRow.getTextAr()).isEqualTo(DEFAULT_TEXT_AR);
    }

    @Test
    @Transactional
    public void createContractDocumentEntryRowWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contractDocumentEntryRowRepository.findAll().size();

        // Create the ContractDocumentEntryRow with an existing ID
        contractDocumentEntryRow.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContractDocumentEntryRowMockMvc.perform(post("/api/contract-document-entry-rows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractDocumentEntryRow)))
            .andExpect(status().isBadRequest());

        // Validate the ContractDocumentEntryRow in the database
        List<ContractDocumentEntryRow> contractDocumentEntryRowList = contractDocumentEntryRowRepository.findAll();
        assertThat(contractDocumentEntryRowList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllContractDocumentEntryRows() throws Exception {
        // Initialize the database
        contractDocumentEntryRowRepository.saveAndFlush(contractDocumentEntryRow);

        // Get all the contractDocumentEntryRowList
        restContractDocumentEntryRowMockMvc.perform(get("/api/contract-document-entry-rows?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contractDocumentEntryRow.getId().intValue())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].textEn").value(hasItem(DEFAULT_TEXT_EN.toString())))
            .andExpect(jsonPath("$.[*].textAr").value(hasItem(DEFAULT_TEXT_AR.toString())));
    }
    
    @Test
    @Transactional
    public void getContractDocumentEntryRow() throws Exception {
        // Initialize the database
        contractDocumentEntryRowRepository.saveAndFlush(contractDocumentEntryRow);

        // Get the contractDocumentEntryRow
        restContractDocumentEntryRowMockMvc.perform(get("/api/contract-document-entry-rows/{id}", contractDocumentEntryRow.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contractDocumentEntryRow.getId().intValue()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION))
            .andExpect(jsonPath("$.textEn").value(DEFAULT_TEXT_EN.toString()))
            .andExpect(jsonPath("$.textAr").value(DEFAULT_TEXT_AR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingContractDocumentEntryRow() throws Exception {
        // Get the contractDocumentEntryRow
        restContractDocumentEntryRowMockMvc.perform(get("/api/contract-document-entry-rows/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContractDocumentEntryRow() throws Exception {
        // Initialize the database
        contractDocumentEntryRowRepository.saveAndFlush(contractDocumentEntryRow);

        int databaseSizeBeforeUpdate = contractDocumentEntryRowRepository.findAll().size();

        // Update the contractDocumentEntryRow
        ContractDocumentEntryRow updatedContractDocumentEntryRow = contractDocumentEntryRowRepository.findById(contractDocumentEntryRow.getId()).get();
        // Disconnect from session so that the updates on updatedContractDocumentEntryRow are not directly saved in db
        em.detach(updatedContractDocumentEntryRow);
        updatedContractDocumentEntryRow
            .position(UPDATED_POSITION)
            .textEn(UPDATED_TEXT_EN)
            .textAr(UPDATED_TEXT_AR);

        restContractDocumentEntryRowMockMvc.perform(put("/api/contract-document-entry-rows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedContractDocumentEntryRow)))
            .andExpect(status().isOk());

        // Validate the ContractDocumentEntryRow in the database
        List<ContractDocumentEntryRow> contractDocumentEntryRowList = contractDocumentEntryRowRepository.findAll();
        assertThat(contractDocumentEntryRowList).hasSize(databaseSizeBeforeUpdate);
        ContractDocumentEntryRow testContractDocumentEntryRow = contractDocumentEntryRowList.get(contractDocumentEntryRowList.size() - 1);
        assertThat(testContractDocumentEntryRow.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testContractDocumentEntryRow.getTextEn()).isEqualTo(UPDATED_TEXT_EN);
        assertThat(testContractDocumentEntryRow.getTextAr()).isEqualTo(UPDATED_TEXT_AR);
    }

    @Test
    @Transactional
    public void updateNonExistingContractDocumentEntryRow() throws Exception {
        int databaseSizeBeforeUpdate = contractDocumentEntryRowRepository.findAll().size();

        // Create the ContractDocumentEntryRow

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContractDocumentEntryRowMockMvc.perform(put("/api/contract-document-entry-rows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contractDocumentEntryRow)))
            .andExpect(status().isBadRequest());

        // Validate the ContractDocumentEntryRow in the database
        List<ContractDocumentEntryRow> contractDocumentEntryRowList = contractDocumentEntryRowRepository.findAll();
        assertThat(contractDocumentEntryRowList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContractDocumentEntryRow() throws Exception {
        // Initialize the database
        contractDocumentEntryRowRepository.saveAndFlush(contractDocumentEntryRow);

        int databaseSizeBeforeDelete = contractDocumentEntryRowRepository.findAll().size();

        // Delete the contractDocumentEntryRow
        restContractDocumentEntryRowMockMvc.perform(delete("/api/contract-document-entry-rows/{id}", contractDocumentEntryRow.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContractDocumentEntryRow> contractDocumentEntryRowList = contractDocumentEntryRowRepository.findAll();
        assertThat(contractDocumentEntryRowList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContractDocumentEntryRow.class);
        ContractDocumentEntryRow contractDocumentEntryRow1 = new ContractDocumentEntryRow();
        contractDocumentEntryRow1.setId(1L);
        ContractDocumentEntryRow contractDocumentEntryRow2 = new ContractDocumentEntryRow();
        contractDocumentEntryRow2.setId(contractDocumentEntryRow1.getId());
        assertThat(contractDocumentEntryRow1).isEqualTo(contractDocumentEntryRow2);
        contractDocumentEntryRow2.setId(2L);
        assertThat(contractDocumentEntryRow1).isNotEqualTo(contractDocumentEntryRow2);
        contractDocumentEntryRow1.setId(null);
        assertThat(contractDocumentEntryRow1).isNotEqualTo(contractDocumentEntryRow2);
    }
}
