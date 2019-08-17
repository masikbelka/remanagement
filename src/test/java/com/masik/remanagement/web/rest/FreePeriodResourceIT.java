package com.masik.remanagement.web.rest;

import com.masik.remanagement.RemanagementApp;
import com.masik.remanagement.domain.FreePeriod;
import com.masik.remanagement.repository.FreePeriodRepository;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.masik.remanagement.web.rest.TestUtil.sameInstant;
import static com.masik.remanagement.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FreePeriodResource} REST controller.
 */
@SpringBootTest(classes = RemanagementApp.class)
public class FreePeriodResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    @Autowired
    private FreePeriodRepository freePeriodRepository;

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

    private MockMvc restFreePeriodMockMvc;

    private FreePeriod freePeriod;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FreePeriodResource freePeriodResource = new FreePeriodResource(freePeriodRepository);
        this.restFreePeriodMockMvc = MockMvcBuilders.standaloneSetup(freePeriodResource)
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
    public static FreePeriod createEntity(EntityManager em) {
        FreePeriod freePeriod = new FreePeriod()
            .code(DEFAULT_CODE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return freePeriod;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FreePeriod createUpdatedEntity(EntityManager em) {
        FreePeriod freePeriod = new FreePeriod()
            .code(UPDATED_CODE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        return freePeriod;
    }

    @BeforeEach
    public void initTest() {
        freePeriod = createEntity(em);
    }

    @Test
    @Transactional
    public void createFreePeriod() throws Exception {
        int databaseSizeBeforeCreate = freePeriodRepository.findAll().size();

        // Create the FreePeriod
        restFreePeriodMockMvc.perform(post("/api/free-periods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(freePeriod)))
            .andExpect(status().isCreated());

        // Validate the FreePeriod in the database
        List<FreePeriod> freePeriodList = freePeriodRepository.findAll();
        assertThat(freePeriodList).hasSize(databaseSizeBeforeCreate + 1);
        FreePeriod testFreePeriod = freePeriodList.get(freePeriodList.size() - 1);
        assertThat(testFreePeriod.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testFreePeriod.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testFreePeriod.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void createFreePeriodWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = freePeriodRepository.findAll().size();

        // Create the FreePeriod with an existing ID
        freePeriod.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFreePeriodMockMvc.perform(post("/api/free-periods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(freePeriod)))
            .andExpect(status().isBadRequest());

        // Validate the FreePeriod in the database
        List<FreePeriod> freePeriodList = freePeriodRepository.findAll();
        assertThat(freePeriodList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFreePeriods() throws Exception {
        // Initialize the database
        freePeriodRepository.saveAndFlush(freePeriod);

        // Get all the freePeriodList
        restFreePeriodMockMvc.perform(get("/api/free-periods?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(freePeriod.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))));
    }
    
    @Test
    @Transactional
    public void getFreePeriod() throws Exception {
        // Initialize the database
        freePeriodRepository.saveAndFlush(freePeriod);

        // Get the freePeriod
        restFreePeriodMockMvc.perform(get("/api/free-periods/{id}", freePeriod.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(freePeriod.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.endDate").value(sameInstant(DEFAULT_END_DATE)));
    }

    @Test
    @Transactional
    public void getNonExistingFreePeriod() throws Exception {
        // Get the freePeriod
        restFreePeriodMockMvc.perform(get("/api/free-periods/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFreePeriod() throws Exception {
        // Initialize the database
        freePeriodRepository.saveAndFlush(freePeriod);

        int databaseSizeBeforeUpdate = freePeriodRepository.findAll().size();

        // Update the freePeriod
        FreePeriod updatedFreePeriod = freePeriodRepository.findById(freePeriod.getId()).get();
        // Disconnect from session so that the updates on updatedFreePeriod are not directly saved in db
        em.detach(updatedFreePeriod);
        updatedFreePeriod
            .code(UPDATED_CODE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);

        restFreePeriodMockMvc.perform(put("/api/free-periods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFreePeriod)))
            .andExpect(status().isOk());

        // Validate the FreePeriod in the database
        List<FreePeriod> freePeriodList = freePeriodRepository.findAll();
        assertThat(freePeriodList).hasSize(databaseSizeBeforeUpdate);
        FreePeriod testFreePeriod = freePeriodList.get(freePeriodList.size() - 1);
        assertThat(testFreePeriod.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFreePeriod.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testFreePeriod.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingFreePeriod() throws Exception {
        int databaseSizeBeforeUpdate = freePeriodRepository.findAll().size();

        // Create the FreePeriod

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFreePeriodMockMvc.perform(put("/api/free-periods")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(freePeriod)))
            .andExpect(status().isBadRequest());

        // Validate the FreePeriod in the database
        List<FreePeriod> freePeriodList = freePeriodRepository.findAll();
        assertThat(freePeriodList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFreePeriod() throws Exception {
        // Initialize the database
        freePeriodRepository.saveAndFlush(freePeriod);

        int databaseSizeBeforeDelete = freePeriodRepository.findAll().size();

        // Delete the freePeriod
        restFreePeriodMockMvc.perform(delete("/api/free-periods/{id}", freePeriod.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FreePeriod> freePeriodList = freePeriodRepository.findAll();
        assertThat(freePeriodList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FreePeriod.class);
        FreePeriod freePeriod1 = new FreePeriod();
        freePeriod1.setId(1L);
        FreePeriod freePeriod2 = new FreePeriod();
        freePeriod2.setId(freePeriod1.getId());
        assertThat(freePeriod1).isEqualTo(freePeriod2);
        freePeriod2.setId(2L);
        assertThat(freePeriod1).isNotEqualTo(freePeriod2);
        freePeriod1.setId(null);
        assertThat(freePeriod1).isNotEqualTo(freePeriod2);
    }
}
