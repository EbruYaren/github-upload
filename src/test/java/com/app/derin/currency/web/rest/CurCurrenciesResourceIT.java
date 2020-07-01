package com.app.derin.currency.web.rest;

import com.app.derin.currency.DerincurrencyApp;
import com.app.derin.currency.config.SecurityBeanOverrideConfiguration;
import com.app.derin.currency.domain.CurCurrencies;
import com.app.derin.currency.ext.derinfw.McfClientCompanyBusServiceClient;
import com.app.derin.currency.ext.derinuaa.UaaBusServiceClient;
import com.app.derin.currency.ext.derinuaa.UaaServiceClient;
import com.app.derin.currency.ext.errors.MessageTranslator;
import com.app.derin.currency.ext.kafka.alerts.AlertConsumer;
import com.app.derin.currency.ext.kafka.alerts.AlertService;
import com.app.derin.currency.repository.CurCurrenciesRepository;
import com.app.derin.currency.service.CurCurrenciesService;
import com.app.derin.currency.service.CurCurrencyDateService;
import com.app.derin.currency.service.dto.CurCurrenciesDTO;
import com.app.derin.currency.service.mapper.CurCurrenciesMapper;
import com.app.derin.currency.web.rest.errors.ExceptionTranslator;

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

import static com.app.derin.currency.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CurCurrenciesResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, DerincurrencyApp.class})
public class CurCurrenciesResourceIT {

    private static final String DEFAULT_CURRENCY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENCY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENCY_SYMBOL = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_SYMBOL = "BBBBBBBBBB";

    @Autowired
    private CurCurrenciesRepository curCurrenciesRepository;

    @Autowired
    private CurCurrenciesMapper curCurrenciesMapper;

    @Autowired
    private CurCurrenciesService curCurrenciesService;

    @Autowired
    private McfClientCompanyBusServiceClient derinfwBusService;

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

    private MockMvc restCurCurrenciesMockMvc;

    private CurCurrencies curCurrencies;

    private CurCurrencyDateService curCurrencyDateService;

    private AlertService alertService ;

    private AlertConsumer alertConsumer;

    private UaaBusServiceClient uaaBusServiceClient;

    private UaaServiceClient serviceClient;
    private McfClientCompanyBusServiceClient mcfClientCompanyBusServiceClient;

    private MessageTranslator messageTranslator;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CurCurrenciesResource curCurrenciesResource = new CurCurrenciesResource(curCurrenciesService, curCurrencyDateService, alertService, alertConsumer, uaaBusServiceClient, mcfClientCompanyBusServiceClient, serviceClient, messageTranslator);
        this.restCurCurrenciesMockMvc = MockMvcBuilders.standaloneSetup(curCurrenciesResource)
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
    public static CurCurrencies createEntity(EntityManager em) {
        CurCurrencies curCurrencies = new CurCurrencies()
            .currencyCode(DEFAULT_CURRENCY_CODE)
            .currencyName(DEFAULT_CURRENCY_NAME)
            .currencySymbol(DEFAULT_CURRENCY_SYMBOL);
        return curCurrencies;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CurCurrencies createUpdatedEntity(EntityManager em) {
        CurCurrencies curCurrencies = new CurCurrencies()
            .currencyCode(UPDATED_CURRENCY_CODE)
            .currencyName(UPDATED_CURRENCY_NAME)
            .currencySymbol(UPDATED_CURRENCY_SYMBOL);
        return curCurrencies;
    }

    @BeforeEach
    public void initTest() {
        curCurrencies = createEntity(em);
    }

    @Test
    @Transactional
    public void createCurCurrencies() throws Exception {
        int databaseSizeBeforeCreate = curCurrenciesRepository.findAll().size();

        // Create the CurCurrencies
        CurCurrenciesDTO curCurrenciesDTO = curCurrenciesMapper.toDto(curCurrencies);
        restCurCurrenciesMockMvc.perform(post("/api/cur-currencies")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(curCurrenciesDTO)))
            .andExpect(status().isCreated());

        // Validate the CurCurrencies in the database
        List<CurCurrencies> curCurrenciesList = curCurrenciesRepository.findAll();
        assertThat(curCurrenciesList).hasSize(databaseSizeBeforeCreate + 1);
        CurCurrencies testCurCurrencies = curCurrenciesList.get(curCurrenciesList.size() - 1);
        assertThat(testCurCurrencies.getCurrencyCode()).isEqualTo(DEFAULT_CURRENCY_CODE);
        assertThat(testCurCurrencies.getCurrencyName()).isEqualTo(DEFAULT_CURRENCY_NAME);
        assertThat(testCurCurrencies.getCurrencySymbol()).isEqualTo(DEFAULT_CURRENCY_SYMBOL);
    }

    @Test
    @Transactional
    public void createCurCurrenciesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = curCurrenciesRepository.findAll().size();

        // Create the CurCurrencies with an existing ID
        curCurrencies.setId(1L);
        CurCurrenciesDTO curCurrenciesDTO = curCurrenciesMapper.toDto(curCurrencies);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCurCurrenciesMockMvc.perform(post("/api/cur-currencies")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(curCurrenciesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CurCurrencies in the database
        List<CurCurrencies> curCurrenciesList = curCurrenciesRepository.findAll();
        assertThat(curCurrenciesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCurCurrencies() throws Exception {
        // Initialize the database
        curCurrenciesRepository.saveAndFlush(curCurrencies);

        // Get all the curCurrenciesList
        restCurCurrenciesMockMvc.perform(get("/api/cur-currencies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(curCurrencies.getId().intValue())))
            .andExpect(jsonPath("$.[*].currencyCode").value(hasItem(DEFAULT_CURRENCY_CODE)))
            .andExpect(jsonPath("$.[*].currencyName").value(hasItem(DEFAULT_CURRENCY_NAME)))
            .andExpect(jsonPath("$.[*].currencySymbol").value(hasItem(DEFAULT_CURRENCY_SYMBOL)));
    }

    @Test
    @Transactional
    public void getCurCurrencies() throws Exception {
        // Initialize the database
        curCurrenciesRepository.saveAndFlush(curCurrencies);

        // Get the curCurrencies
        restCurCurrenciesMockMvc.perform(get("/api/cur-currencies/{id}", curCurrencies.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(curCurrencies.getId().intValue()))
            .andExpect(jsonPath("$.currencyCode").value(DEFAULT_CURRENCY_CODE))
            .andExpect(jsonPath("$.currencyName").value(DEFAULT_CURRENCY_NAME))
            .andExpect(jsonPath("$.currencySymbol").value(DEFAULT_CURRENCY_SYMBOL));
    }

    @Test
    @Transactional
    public void getNonExistingCurCurrencies() throws Exception {
        // Get the curCurrencies
        restCurCurrenciesMockMvc.perform(get("/api/cur-currencies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCurCurrencies() throws Exception {
        // Initialize the database
        curCurrenciesRepository.saveAndFlush(curCurrencies);

        int databaseSizeBeforeUpdate = curCurrenciesRepository.findAll().size();

        // Update the curCurrencies
        CurCurrencies updatedCurCurrencies = curCurrenciesRepository.findById(curCurrencies.getId()).get();
        // Disconnect from session so that the updates on updatedCurCurrencies are not directly saved in db
        em.detach(updatedCurCurrencies);
        updatedCurCurrencies
            .currencyCode(UPDATED_CURRENCY_CODE)
            .currencyName(UPDATED_CURRENCY_NAME)
            .currencySymbol(UPDATED_CURRENCY_SYMBOL);
        CurCurrenciesDTO curCurrenciesDTO = curCurrenciesMapper.toDto(updatedCurCurrencies);

        restCurCurrenciesMockMvc.perform(put("/api/cur-currencies")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(curCurrenciesDTO)))
            .andExpect(status().isOk());

        // Validate the CurCurrencies in the database
        List<CurCurrencies> curCurrenciesList = curCurrenciesRepository.findAll();
        assertThat(curCurrenciesList).hasSize(databaseSizeBeforeUpdate);
        CurCurrencies testCurCurrencies = curCurrenciesList.get(curCurrenciesList.size() - 1);
        assertThat(testCurCurrencies.getCurrencyCode()).isEqualTo(UPDATED_CURRENCY_CODE);
        assertThat(testCurCurrencies.getCurrencyName()).isEqualTo(UPDATED_CURRENCY_NAME);
        assertThat(testCurCurrencies.getCurrencySymbol()).isEqualTo(UPDATED_CURRENCY_SYMBOL);
    }

    @Test
    @Transactional
    public void updateNonExistingCurCurrencies() throws Exception {
        int databaseSizeBeforeUpdate = curCurrenciesRepository.findAll().size();

        // Create the CurCurrencies
        CurCurrenciesDTO curCurrenciesDTO = curCurrenciesMapper.toDto(curCurrencies);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCurCurrenciesMockMvc.perform(put("/api/cur-currencies")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(curCurrenciesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CurCurrencies in the database
        List<CurCurrencies> curCurrenciesList = curCurrenciesRepository.findAll();
        assertThat(curCurrenciesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCurCurrencies() throws Exception {
        // Initialize the database
        curCurrenciesRepository.saveAndFlush(curCurrencies);

        int databaseSizeBeforeDelete = curCurrenciesRepository.findAll().size();

        // Delete the curCurrencies
        restCurCurrenciesMockMvc.perform(delete("/api/cur-currencies/{id}", curCurrencies.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CurCurrencies> curCurrenciesList = curCurrenciesRepository.findAll();
        assertThat(curCurrenciesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
