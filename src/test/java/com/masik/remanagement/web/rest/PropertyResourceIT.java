package com.masik.remanagement.web.rest;

import com.masik.remanagement.RemanagementApp;
import com.masik.remanagement.domain.Property;
import com.masik.remanagement.repository.PropertyRepository;
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

import com.masik.remanagement.domain.enumeration.UnitType;
import com.masik.remanagement.domain.enumeration.Furnishing;
/**
 * Integration tests for the {@link PropertyResource} REST controller.
 */
@SpringBootTest(classes = RemanagementApp.class)
public class PropertyResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final UnitType DEFAULT_TYPE = UnitType.VILLA;
    private static final UnitType UPDATED_TYPE = UnitType.APPARTMENT;

    private static final Integer DEFAULT_BEDROOMS = 1;
    private static final Integer UPDATED_BEDROOMS = 2;
    private static final Integer SMALLER_BEDROOMS = 1 - 1;

    private static final Furnishing DEFAULT_FURNISHING = Furnishing.FURNISHED;
    private static final Furnishing UPDATED_FURNISHING = Furnishing.UNFURNISHED;

    private static final Integer DEFAULT_ELECTRICITY = 1;
    private static final Integer UPDATED_ELECTRICITY = 2;
    private static final Integer SMALLER_ELECTRICITY = 1 - 1;

    private static final Integer DEFAULT_WATER = 1;
    private static final Integer UPDATED_WATER = 2;
    private static final Integer SMALLER_WATER = 1 - 1;

    private static final Integer DEFAULT_PARKING = 1;
    private static final Integer UPDATED_PARKING = 2;
    private static final Integer SMALLER_PARKING = 1 - 1;

    @Autowired
    private PropertyRepository propertyRepository;

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

    private MockMvc restPropertyMockMvc;

    private Property property;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PropertyResource propertyResource = new PropertyResource(propertyRepository);
        this.restPropertyMockMvc = MockMvcBuilders.standaloneSetup(propertyResource)
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
    public static Property createEntity(EntityManager em) {
        Property property = new Property()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .number(DEFAULT_NUMBER)
            .type(DEFAULT_TYPE)
            .bedrooms(DEFAULT_BEDROOMS)
            .furnishing(DEFAULT_FURNISHING)
            .electricity(DEFAULT_ELECTRICITY)
            .water(DEFAULT_WATER)
            .parking(DEFAULT_PARKING);
        return property;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Property createUpdatedEntity(EntityManager em) {
        Property property = new Property()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .number(UPDATED_NUMBER)
            .type(UPDATED_TYPE)
            .bedrooms(UPDATED_BEDROOMS)
            .furnishing(UPDATED_FURNISHING)
            .electricity(UPDATED_ELECTRICITY)
            .water(UPDATED_WATER)
            .parking(UPDATED_PARKING);
        return property;
    }

    @BeforeEach
    public void initTest() {
        property = createEntity(em);
    }

    @Test
    @Transactional
    public void createProperty() throws Exception {
        int databaseSizeBeforeCreate = propertyRepository.findAll().size();

        // Create the Property
        restPropertyMockMvc.perform(post("/api/properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(property)))
            .andExpect(status().isCreated());

        // Validate the Property in the database
        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeCreate + 1);
        Property testProperty = propertyList.get(propertyList.size() - 1);
        assertThat(testProperty.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProperty.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProperty.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testProperty.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProperty.getBedrooms()).isEqualTo(DEFAULT_BEDROOMS);
        assertThat(testProperty.getFurnishing()).isEqualTo(DEFAULT_FURNISHING);
        assertThat(testProperty.getElectricity()).isEqualTo(DEFAULT_ELECTRICITY);
        assertThat(testProperty.getWater()).isEqualTo(DEFAULT_WATER);
        assertThat(testProperty.getParking()).isEqualTo(DEFAULT_PARKING);
    }

    @Test
    @Transactional
    public void createPropertyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = propertyRepository.findAll().size();

        // Create the Property with an existing ID
        property.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPropertyMockMvc.perform(post("/api/properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(property)))
            .andExpect(status().isBadRequest());

        // Validate the Property in the database
        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProperties() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertyList
        restPropertyMockMvc.perform(get("/api/properties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(property.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].bedrooms").value(hasItem(DEFAULT_BEDROOMS)))
            .andExpect(jsonPath("$.[*].furnishing").value(hasItem(DEFAULT_FURNISHING.toString())))
            .andExpect(jsonPath("$.[*].electricity").value(hasItem(DEFAULT_ELECTRICITY)))
            .andExpect(jsonPath("$.[*].water").value(hasItem(DEFAULT_WATER)))
            .andExpect(jsonPath("$.[*].parking").value(hasItem(DEFAULT_PARKING)));
    }
    
    @Test
    @Transactional
    public void getProperty() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get the property
        restPropertyMockMvc.perform(get("/api/properties/{id}", property.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(property.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.bedrooms").value(DEFAULT_BEDROOMS))
            .andExpect(jsonPath("$.furnishing").value(DEFAULT_FURNISHING.toString()))
            .andExpect(jsonPath("$.electricity").value(DEFAULT_ELECTRICITY))
            .andExpect(jsonPath("$.water").value(DEFAULT_WATER))
            .andExpect(jsonPath("$.parking").value(DEFAULT_PARKING));
    }

    @Test
    @Transactional
    public void getNonExistingProperty() throws Exception {
        // Get the property
        restPropertyMockMvc.perform(get("/api/properties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProperty() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        int databaseSizeBeforeUpdate = propertyRepository.findAll().size();

        // Update the property
        Property updatedProperty = propertyRepository.findById(property.getId()).get();
        // Disconnect from session so that the updates on updatedProperty are not directly saved in db
        em.detach(updatedProperty);
        updatedProperty
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .number(UPDATED_NUMBER)
            .type(UPDATED_TYPE)
            .bedrooms(UPDATED_BEDROOMS)
            .furnishing(UPDATED_FURNISHING)
            .electricity(UPDATED_ELECTRICITY)
            .water(UPDATED_WATER)
            .parking(UPDATED_PARKING);

        restPropertyMockMvc.perform(put("/api/properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProperty)))
            .andExpect(status().isOk());

        // Validate the Property in the database
        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeUpdate);
        Property testProperty = propertyList.get(propertyList.size() - 1);
        assertThat(testProperty.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProperty.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProperty.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testProperty.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProperty.getBedrooms()).isEqualTo(UPDATED_BEDROOMS);
        assertThat(testProperty.getFurnishing()).isEqualTo(UPDATED_FURNISHING);
        assertThat(testProperty.getElectricity()).isEqualTo(UPDATED_ELECTRICITY);
        assertThat(testProperty.getWater()).isEqualTo(UPDATED_WATER);
        assertThat(testProperty.getParking()).isEqualTo(UPDATED_PARKING);
    }

    @Test
    @Transactional
    public void updateNonExistingProperty() throws Exception {
        int databaseSizeBeforeUpdate = propertyRepository.findAll().size();

        // Create the Property

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPropertyMockMvc.perform(put("/api/properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(property)))
            .andExpect(status().isBadRequest());

        // Validate the Property in the database
        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProperty() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        int databaseSizeBeforeDelete = propertyRepository.findAll().size();

        // Delete the property
        restPropertyMockMvc.perform(delete("/api/properties/{id}", property.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Property.class);
        Property property1 = new Property();
        property1.setId(1L);
        Property property2 = new Property();
        property2.setId(property1.getId());
        assertThat(property1).isEqualTo(property2);
        property2.setId(2L);
        assertThat(property1).isNotEqualTo(property2);
        property1.setId(null);
        assertThat(property1).isNotEqualTo(property2);
    }
}
