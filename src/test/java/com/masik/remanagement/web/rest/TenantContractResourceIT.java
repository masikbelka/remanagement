package com.masik.remanagement.web.rest;

import com.masik.remanagement.RemanagementApp;
import com.masik.remanagement.domain.TenantContract;
import com.masik.remanagement.repository.TenantContractRepository;
import com.masik.remanagement.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.masik.remanagement.web.rest.TestUtil.sameInstant;
import static com.masik.remanagement.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TenantContractResource} REST controller.
 */
@SpringBootTest(classes = RemanagementApp.class)
public class TenantContractResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_EFFECTIVE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EFFECTIVE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_EFFECTIVE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Double DEFAULT_RENT = 1D;
    private static final Double UPDATED_RENT = 2D;
    private static final Double SMALLER_RENT = 1D - 1D;

    private static final Double DEFAULT_DEPOSIT = 1D;
    private static final Double UPDATED_DEPOSIT = 2D;
    private static final Double SMALLER_DEPOSIT = 1D - 1D;

    private static final String DEFAULT_UTILITIES = "AAAAAAAAAA";
    private static final String UPDATED_UTILITIES = "BBBBBBBBBB";

    @Autowired
    private TenantContractRepository tenantContractRepository;

    @Mock
    private TenantContractRepository tenantContractRepositoryMock;

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

    private MockMvc restTenantContractMockMvc;

    private TenantContract tenantContract;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TenantContractResource tenantContractResource = new TenantContractResource(tenantContractRepository);
        this.restTenantContractMockMvc = MockMvcBuilders.standaloneSetup(tenantContractResource)
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
    public static TenantContract createEntity(EntityManager em) {
        TenantContract tenantContract = new TenantContract()
            .code(DEFAULT_CODE)
            .effectiveDate(DEFAULT_EFFECTIVE_DATE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .rent(DEFAULT_RENT)
            .deposit(DEFAULT_DEPOSIT)
            .utilities(DEFAULT_UTILITIES);
        return tenantContract;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TenantContract createUpdatedEntity(EntityManager em) {
        TenantContract tenantContract = new TenantContract()
            .code(UPDATED_CODE)
            .effectiveDate(UPDATED_EFFECTIVE_DATE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .rent(UPDATED_RENT)
            .deposit(UPDATED_DEPOSIT)
            .utilities(UPDATED_UTILITIES);
        return tenantContract;
    }

    @BeforeEach
    public void initTest() {
        tenantContract = createEntity(em);
    }

    @Test
    @Transactional
    public void createTenantContract() throws Exception {
        int databaseSizeBeforeCreate = tenantContractRepository.findAll().size();

        // Create the TenantContract
        restTenantContractMockMvc.perform(post("/api/tenant-contracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tenantContract)))
            .andExpect(status().isCreated());

        // Validate the TenantContract in the database
        List<TenantContract> tenantContractList = tenantContractRepository.findAll();
        assertThat(tenantContractList).hasSize(databaseSizeBeforeCreate + 1);
        TenantContract testTenantContract = tenantContractList.get(tenantContractList.size() - 1);
        assertThat(testTenantContract.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTenantContract.getEffectiveDate()).isEqualTo(DEFAULT_EFFECTIVE_DATE);
        assertThat(testTenantContract.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testTenantContract.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testTenantContract.getRent()).isEqualTo(DEFAULT_RENT);
        assertThat(testTenantContract.getDeposit()).isEqualTo(DEFAULT_DEPOSIT);
        assertThat(testTenantContract.getUtilities()).isEqualTo(DEFAULT_UTILITIES);
    }

    @Test
    @Transactional
    public void createTenantContractWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tenantContractRepository.findAll().size();

        // Create the TenantContract with an existing ID
        tenantContract.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTenantContractMockMvc.perform(post("/api/tenant-contracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tenantContract)))
            .andExpect(status().isBadRequest());

        // Validate the TenantContract in the database
        List<TenantContract> tenantContractList = tenantContractRepository.findAll();
        assertThat(tenantContractList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTenantContracts() throws Exception {
        // Initialize the database
        tenantContractRepository.saveAndFlush(tenantContract);

        // Get all the tenantContractList
        restTenantContractMockMvc.perform(get("/api/tenant-contracts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tenantContract.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].effectiveDate").value(hasItem(sameInstant(DEFAULT_EFFECTIVE_DATE))))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].rent").value(hasItem(DEFAULT_RENT.doubleValue())))
            .andExpect(jsonPath("$.[*].deposit").value(hasItem(DEFAULT_DEPOSIT.doubleValue())))
            .andExpect(jsonPath("$.[*].utilities").value(hasItem(DEFAULT_UTILITIES.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllTenantContractsWithEagerRelationshipsIsEnabled() throws Exception {
        TenantContractResource tenantContractResource = new TenantContractResource(tenantContractRepositoryMock);
        when(tenantContractRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restTenantContractMockMvc = MockMvcBuilders.standaloneSetup(tenantContractResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restTenantContractMockMvc.perform(get("/api/tenant-contracts?eagerload=true"))
        .andExpect(status().isOk());

        verify(tenantContractRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllTenantContractsWithEagerRelationshipsIsNotEnabled() throws Exception {
        TenantContractResource tenantContractResource = new TenantContractResource(tenantContractRepositoryMock);
            when(tenantContractRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restTenantContractMockMvc = MockMvcBuilders.standaloneSetup(tenantContractResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restTenantContractMockMvc.perform(get("/api/tenant-contracts?eagerload=true"))
        .andExpect(status().isOk());

            verify(tenantContractRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getTenantContract() throws Exception {
        // Initialize the database
        tenantContractRepository.saveAndFlush(tenantContract);

        // Get the tenantContract
        restTenantContractMockMvc.perform(get("/api/tenant-contracts/{id}", tenantContract.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tenantContract.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.effectiveDate").value(sameInstant(DEFAULT_EFFECTIVE_DATE)))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.endDate").value(sameInstant(DEFAULT_END_DATE)))
            .andExpect(jsonPath("$.rent").value(DEFAULT_RENT.doubleValue()))
            .andExpect(jsonPath("$.deposit").value(DEFAULT_DEPOSIT.doubleValue()))
            .andExpect(jsonPath("$.utilities").value(DEFAULT_UTILITIES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTenantContract() throws Exception {
        // Get the tenantContract
        restTenantContractMockMvc.perform(get("/api/tenant-contracts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTenantContract() throws Exception {
        // Initialize the database
        tenantContractRepository.saveAndFlush(tenantContract);

        int databaseSizeBeforeUpdate = tenantContractRepository.findAll().size();

        // Update the tenantContract
        TenantContract updatedTenantContract = tenantContractRepository.findById(tenantContract.getId()).get();
        // Disconnect from session so that the updates on updatedTenantContract are not directly saved in db
        em.detach(updatedTenantContract);
        updatedTenantContract
            .code(UPDATED_CODE)
            .effectiveDate(UPDATED_EFFECTIVE_DATE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .rent(UPDATED_RENT)
            .deposit(UPDATED_DEPOSIT)
            .utilities(UPDATED_UTILITIES);

        restTenantContractMockMvc.perform(put("/api/tenant-contracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTenantContract)))
            .andExpect(status().isOk());

        // Validate the TenantContract in the database
        List<TenantContract> tenantContractList = tenantContractRepository.findAll();
        assertThat(tenantContractList).hasSize(databaseSizeBeforeUpdate);
        TenantContract testTenantContract = tenantContractList.get(tenantContractList.size() - 1);
        assertThat(testTenantContract.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTenantContract.getEffectiveDate()).isEqualTo(UPDATED_EFFECTIVE_DATE);
        assertThat(testTenantContract.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTenantContract.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testTenantContract.getRent()).isEqualTo(UPDATED_RENT);
        assertThat(testTenantContract.getDeposit()).isEqualTo(UPDATED_DEPOSIT);
        assertThat(testTenantContract.getUtilities()).isEqualTo(UPDATED_UTILITIES);
    }

    @Test
    @Transactional
    public void updateNonExistingTenantContract() throws Exception {
        int databaseSizeBeforeUpdate = tenantContractRepository.findAll().size();

        // Create the TenantContract

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTenantContractMockMvc.perform(put("/api/tenant-contracts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tenantContract)))
            .andExpect(status().isBadRequest());

        // Validate the TenantContract in the database
        List<TenantContract> tenantContractList = tenantContractRepository.findAll();
        assertThat(tenantContractList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTenantContract() throws Exception {
        // Initialize the database
        tenantContractRepository.saveAndFlush(tenantContract);

        int databaseSizeBeforeDelete = tenantContractRepository.findAll().size();

        // Delete the tenantContract
        restTenantContractMockMvc.perform(delete("/api/tenant-contracts/{id}", tenantContract.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TenantContract> tenantContractList = tenantContractRepository.findAll();
        assertThat(tenantContractList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TenantContract.class);
        TenantContract tenantContract1 = new TenantContract();
        tenantContract1.setId(1L);
        TenantContract tenantContract2 = new TenantContract();
        tenantContract2.setId(tenantContract1.getId());
        assertThat(tenantContract1).isEqualTo(tenantContract2);
        tenantContract2.setId(2L);
        assertThat(tenantContract1).isNotEqualTo(tenantContract2);
        tenantContract1.setId(null);
        assertThat(tenantContract1).isNotEqualTo(tenantContract2);
    }
}
