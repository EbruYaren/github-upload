package com.app.derin.currency.web.rest;

import com.app.derin.currency.DerincurrencyApp;
import com.app.derin.currency.config.SecurityBeanOverrideConfiguration;
import com.app.derin.currency.domain.CurCurrencyDate;
import com.app.derin.currency.domain.CurCurrencies;
import com.app.derin.currency.ext.errors.MessageTranslator;
import com.app.derin.currency.repository.CurCurrencyDateRepository;
import com.app.derin.currency.service.CurCurrencyDateService;
import com.app.derin.currency.service.dto.CurCurrencyDateDTO;
import com.app.derin.currency.service.mapper.CurCurrencyDateMapper;
import com.app.derin.currency.web.rest.errors.ExceptionTranslator;
import com.app.derin.currency.service.CurCurrencyDateQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.app.derin.currency.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CurCurrencyDateResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, DerincurrencyApp.class})
public class CurCurrencyDateResourceIT {

    private static final LocalDate DEFAULT_CURRENCY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CURRENCY_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CURRENCY_DATE = LocalDate.ofEpochDay(-1L);

    private static final Integer DEFAULT_SOURCE_UNIT_VALUE = 1;
    private static final Integer UPDATED_SOURCE_UNIT_VALUE = 2;
    private static final Integer SMALLER_SOURCE_UNIT_VALUE = 1 - 1;

    private static final Integer DEFAULT_RESULT_UNIT_VALUE = 1;
    private static final Integer UPDATED_RESULT_UNIT_VALUE = 2;
    private static final Integer SMALLER_RESULT_UNIT_VALUE = 1 - 1;

    private static final Double DEFAULT_BUYING_RATE = 1D;
    private static final Double UPDATED_BUYING_RATE = 2D;
    private static final Double SMALLER_BUYING_RATE = 1D - 1D;

    private static final Double DEFAULT_SELLING_RATE = 1D;
    private static final Double UPDATED_SELLING_RATE = 2D;
    private static final Double SMALLER_SELLING_RATE = 1D - 1D;

    private static final Double DEFAULT_EFFECTIVE_BUYING_RATE = 1D;
    private static final Double UPDATED_EFFECTIVE_BUYING_RATE = 2D;
    private static final Double SMALLER_EFFECTIVE_BUYING_RATE = 1D - 1D;

    private static final Double DEFAULT_EFFECTIVE_SELLING_RATE = 1D;
    private static final Double UPDATED_EFFECTIVE_SELLING_RATE = 2D;
    private static final Double SMALLER_EFFECTIVE_SELLING_RATE = 1D - 1D;

    private static final Boolean DEFAULT_IS_SERVICE = false;
    private static final Boolean UPDATED_IS_SERVICE = true;

    @Autowired
    private CurCurrencyDateRepository curCurrencyDateRepository;

    @Autowired
    private CurCurrencyDateMapper curCurrencyDateMapper;

    @Autowired
    private CurCurrencyDateService curCurrencyDateService;

    @Autowired
    private CurCurrencyDateQueryService curCurrencyDateQueryService;

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

    private MockMvc restCurCurrencyDateMockMvc;

    private CurCurrencyDate curCurrencyDate;

    private MessageTranslator messageTranslator;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CurCurrencyDateResource curCurrencyDateResource = new CurCurrencyDateResource(curCurrencyDateService, curCurrencyDateQueryService, messageTranslator);
        this.restCurCurrencyDateMockMvc = MockMvcBuilders.standaloneSetup(curCurrencyDateResource)
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
    public static CurCurrencyDate createEntity(EntityManager em) {
        CurCurrencyDate curCurrencyDate = new CurCurrencyDate()
            .currencyDate(DEFAULT_CURRENCY_DATE)
            .sourceUnitValue(DEFAULT_SOURCE_UNIT_VALUE)
            .resultUnitValue(DEFAULT_RESULT_UNIT_VALUE)
            .buyingRate(DEFAULT_BUYING_RATE)
            .sellingRate(DEFAULT_SELLING_RATE)
            .effectiveBuyingRate(DEFAULT_EFFECTIVE_BUYING_RATE)
            .effectiveSellingRate(DEFAULT_EFFECTIVE_SELLING_RATE)
            .isService(DEFAULT_IS_SERVICE);
        return curCurrencyDate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CurCurrencyDate createUpdatedEntity(EntityManager em) {
        CurCurrencyDate curCurrencyDate = new CurCurrencyDate()
            .currencyDate(UPDATED_CURRENCY_DATE)
            .sourceUnitValue(UPDATED_SOURCE_UNIT_VALUE)
            .resultUnitValue(UPDATED_RESULT_UNIT_VALUE)
            .buyingRate(UPDATED_BUYING_RATE)
            .sellingRate(UPDATED_SELLING_RATE)
            .effectiveBuyingRate(UPDATED_EFFECTIVE_BUYING_RATE)
            .effectiveSellingRate(UPDATED_EFFECTIVE_SELLING_RATE)
            .isService(UPDATED_IS_SERVICE);
        return curCurrencyDate;
    }

    @BeforeEach
    public void initTest() {
        curCurrencyDate = createEntity(em);
    }

    @Test
    @Transactional
    public void createCurCurrencyDate() throws Exception {
        int databaseSizeBeforeCreate = curCurrencyDateRepository.findAll().size();

        // Create the CurCurrencyDate
        CurCurrencyDateDTO curCurrencyDateDTO = curCurrencyDateMapper.toDto(curCurrencyDate);
        restCurCurrencyDateMockMvc.perform(post("/api/cur-currency-dates")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(curCurrencyDateDTO)))
            .andExpect(status().isCreated());

        // Validate the CurCurrencyDate in the database
        List<CurCurrencyDate> curCurrencyDateList = curCurrencyDateRepository.findAll();
        assertThat(curCurrencyDateList).hasSize(databaseSizeBeforeCreate + 1);
        CurCurrencyDate testCurCurrencyDate = curCurrencyDateList.get(curCurrencyDateList.size() - 1);
        assertThat(testCurCurrencyDate.getCurrencyDate()).isEqualTo(DEFAULT_CURRENCY_DATE);
        assertThat(testCurCurrencyDate.getSourceUnitValue()).isEqualTo(DEFAULT_SOURCE_UNIT_VALUE);
        assertThat(testCurCurrencyDate.getResultUnitValue()).isEqualTo(DEFAULT_RESULT_UNIT_VALUE);
        assertThat(testCurCurrencyDate.getBuyingRate()).isEqualTo(DEFAULT_BUYING_RATE);
        assertThat(testCurCurrencyDate.getSellingRate()).isEqualTo(DEFAULT_SELLING_RATE);
        assertThat(testCurCurrencyDate.getEffectiveBuyingRate()).isEqualTo(DEFAULT_EFFECTIVE_BUYING_RATE);
        assertThat(testCurCurrencyDate.getEffectiveSellingRate()).isEqualTo(DEFAULT_EFFECTIVE_SELLING_RATE);
        assertThat(testCurCurrencyDate.isIsService()).isEqualTo(DEFAULT_IS_SERVICE);
    }

    @Test
    @Transactional
    public void createCurCurrencyDateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = curCurrencyDateRepository.findAll().size();

        // Create the CurCurrencyDate with an existing ID
        curCurrencyDate.setId(1L);
        CurCurrencyDateDTO curCurrencyDateDTO = curCurrencyDateMapper.toDto(curCurrencyDate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCurCurrencyDateMockMvc.perform(post("/api/cur-currency-dates")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(curCurrencyDateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CurCurrencyDate in the database
        List<CurCurrencyDate> curCurrencyDateList = curCurrencyDateRepository.findAll();
        assertThat(curCurrencyDateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCurCurrencyDates() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList
        restCurCurrencyDateMockMvc.perform(get("/api/cur-currency-dates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(curCurrencyDate.getId().intValue())))
            .andExpect(jsonPath("$.[*].currencyDate").value(hasItem(DEFAULT_CURRENCY_DATE.toString())))
            .andExpect(jsonPath("$.[*].sourceUnitValue").value(hasItem(DEFAULT_SOURCE_UNIT_VALUE)))
            .andExpect(jsonPath("$.[*].resultUnitValue").value(hasItem(DEFAULT_RESULT_UNIT_VALUE)))
            .andExpect(jsonPath("$.[*].buyingRate").value(hasItem(DEFAULT_BUYING_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].sellingRate").value(hasItem(DEFAULT_SELLING_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].effectiveBuyingRate").value(hasItem(DEFAULT_EFFECTIVE_BUYING_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].effectiveSellingRate").value(hasItem(DEFAULT_EFFECTIVE_SELLING_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].isService").value(hasItem(DEFAULT_IS_SERVICE.booleanValue())));
    }

    @Test
    @Transactional
    public void getCurCurrencyDate() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get the curCurrencyDate
        restCurCurrencyDateMockMvc.perform(get("/api/cur-currency-dates/{id}", curCurrencyDate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(curCurrencyDate.getId().intValue()))
            .andExpect(jsonPath("$.currencyDate").value(DEFAULT_CURRENCY_DATE.toString()))
            .andExpect(jsonPath("$.sourceUnitValue").value(DEFAULT_SOURCE_UNIT_VALUE))
            .andExpect(jsonPath("$.resultUnitValue").value(DEFAULT_RESULT_UNIT_VALUE))
            .andExpect(jsonPath("$.buyingRate").value(DEFAULT_BUYING_RATE.doubleValue()))
            .andExpect(jsonPath("$.sellingRate").value(DEFAULT_SELLING_RATE.doubleValue()))
            .andExpect(jsonPath("$.effectiveBuyingRate").value(DEFAULT_EFFECTIVE_BUYING_RATE.doubleValue()))
            .andExpect(jsonPath("$.effectiveSellingRate").value(DEFAULT_EFFECTIVE_SELLING_RATE.doubleValue()))
            .andExpect(jsonPath("$.isService").value(DEFAULT_IS_SERVICE.booleanValue()));
    }


    @Test
    @Transactional
    public void getCurCurrencyDatesByIdFiltering() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        Long id = curCurrencyDate.getId();

        defaultCurCurrencyDateShouldBeFound("id.equals=" + id);
        defaultCurCurrencyDateShouldNotBeFound("id.notEquals=" + id);

        defaultCurCurrencyDateShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCurCurrencyDateShouldNotBeFound("id.greaterThan=" + id);

        defaultCurCurrencyDateShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCurCurrencyDateShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCurCurrencyDatesByCurrencyDateIsEqualToSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where currencyDate equals to DEFAULT_CURRENCY_DATE
        defaultCurCurrencyDateShouldBeFound("currencyDate.equals=" + DEFAULT_CURRENCY_DATE);

        // Get all the curCurrencyDateList where currencyDate equals to UPDATED_CURRENCY_DATE
        defaultCurCurrencyDateShouldNotBeFound("currencyDate.equals=" + UPDATED_CURRENCY_DATE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesByCurrencyDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where currencyDate not equals to DEFAULT_CURRENCY_DATE
        defaultCurCurrencyDateShouldNotBeFound("currencyDate.notEquals=" + DEFAULT_CURRENCY_DATE);

        // Get all the curCurrencyDateList where currencyDate not equals to UPDATED_CURRENCY_DATE
        defaultCurCurrencyDateShouldBeFound("currencyDate.notEquals=" + UPDATED_CURRENCY_DATE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesByCurrencyDateIsInShouldWork() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where currencyDate in DEFAULT_CURRENCY_DATE or UPDATED_CURRENCY_DATE
        defaultCurCurrencyDateShouldBeFound("currencyDate.in=" + DEFAULT_CURRENCY_DATE + "," + UPDATED_CURRENCY_DATE);

        // Get all the curCurrencyDateList where currencyDate equals to UPDATED_CURRENCY_DATE
        defaultCurCurrencyDateShouldNotBeFound("currencyDate.in=" + UPDATED_CURRENCY_DATE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesByCurrencyDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where currencyDate is not null
        defaultCurCurrencyDateShouldBeFound("currencyDate.specified=true");

        // Get all the curCurrencyDateList where currencyDate is null
        defaultCurCurrencyDateShouldNotBeFound("currencyDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesByCurrencyDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where currencyDate is greater than or equal to DEFAULT_CURRENCY_DATE
        defaultCurCurrencyDateShouldBeFound("currencyDate.greaterThanOrEqual=" + DEFAULT_CURRENCY_DATE);

        // Get all the curCurrencyDateList where currencyDate is greater than or equal to UPDATED_CURRENCY_DATE
        defaultCurCurrencyDateShouldNotBeFound("currencyDate.greaterThanOrEqual=" + UPDATED_CURRENCY_DATE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesByCurrencyDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where currencyDate is less than or equal to DEFAULT_CURRENCY_DATE
        defaultCurCurrencyDateShouldBeFound("currencyDate.lessThanOrEqual=" + DEFAULT_CURRENCY_DATE);

        // Get all the curCurrencyDateList where currencyDate is less than or equal to SMALLER_CURRENCY_DATE
        defaultCurCurrencyDateShouldNotBeFound("currencyDate.lessThanOrEqual=" + SMALLER_CURRENCY_DATE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesByCurrencyDateIsLessThanSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where currencyDate is less than DEFAULT_CURRENCY_DATE
        defaultCurCurrencyDateShouldNotBeFound("currencyDate.lessThan=" + DEFAULT_CURRENCY_DATE);

        // Get all the curCurrencyDateList where currencyDate is less than UPDATED_CURRENCY_DATE
        defaultCurCurrencyDateShouldBeFound("currencyDate.lessThan=" + UPDATED_CURRENCY_DATE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesByCurrencyDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where currencyDate is greater than DEFAULT_CURRENCY_DATE
        defaultCurCurrencyDateShouldNotBeFound("currencyDate.greaterThan=" + DEFAULT_CURRENCY_DATE);

        // Get all the curCurrencyDateList where currencyDate is greater than SMALLER_CURRENCY_DATE
        defaultCurCurrencyDateShouldBeFound("currencyDate.greaterThan=" + SMALLER_CURRENCY_DATE);
    }


    @Test
    @Transactional
    public void getAllCurCurrencyDatesBySourceUnitValueIsEqualToSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where sourceUnitValue equals to DEFAULT_SOURCE_UNIT_VALUE
        defaultCurCurrencyDateShouldBeFound("sourceUnitValue.equals=" + DEFAULT_SOURCE_UNIT_VALUE);

        // Get all the curCurrencyDateList where sourceUnitValue equals to UPDATED_SOURCE_UNIT_VALUE
        defaultCurCurrencyDateShouldNotBeFound("sourceUnitValue.equals=" + UPDATED_SOURCE_UNIT_VALUE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesBySourceUnitValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where sourceUnitValue not equals to DEFAULT_SOURCE_UNIT_VALUE
        defaultCurCurrencyDateShouldNotBeFound("sourceUnitValue.notEquals=" + DEFAULT_SOURCE_UNIT_VALUE);

        // Get all the curCurrencyDateList where sourceUnitValue not equals to UPDATED_SOURCE_UNIT_VALUE
        defaultCurCurrencyDateShouldBeFound("sourceUnitValue.notEquals=" + UPDATED_SOURCE_UNIT_VALUE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesBySourceUnitValueIsInShouldWork() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where sourceUnitValue in DEFAULT_SOURCE_UNIT_VALUE or UPDATED_SOURCE_UNIT_VALUE
        defaultCurCurrencyDateShouldBeFound("sourceUnitValue.in=" + DEFAULT_SOURCE_UNIT_VALUE + "," + UPDATED_SOURCE_UNIT_VALUE);

        // Get all the curCurrencyDateList where sourceUnitValue equals to UPDATED_SOURCE_UNIT_VALUE
        defaultCurCurrencyDateShouldNotBeFound("sourceUnitValue.in=" + UPDATED_SOURCE_UNIT_VALUE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesBySourceUnitValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where sourceUnitValue is not null
        defaultCurCurrencyDateShouldBeFound("sourceUnitValue.specified=true");

        // Get all the curCurrencyDateList where sourceUnitValue is null
        defaultCurCurrencyDateShouldNotBeFound("sourceUnitValue.specified=false");
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesBySourceUnitValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where sourceUnitValue is greater than or equal to DEFAULT_SOURCE_UNIT_VALUE
        defaultCurCurrencyDateShouldBeFound("sourceUnitValue.greaterThanOrEqual=" + DEFAULT_SOURCE_UNIT_VALUE);

        // Get all the curCurrencyDateList where sourceUnitValue is greater than or equal to UPDATED_SOURCE_UNIT_VALUE
        defaultCurCurrencyDateShouldNotBeFound("sourceUnitValue.greaterThanOrEqual=" + UPDATED_SOURCE_UNIT_VALUE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesBySourceUnitValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where sourceUnitValue is less than or equal to DEFAULT_SOURCE_UNIT_VALUE
        defaultCurCurrencyDateShouldBeFound("sourceUnitValue.lessThanOrEqual=" + DEFAULT_SOURCE_UNIT_VALUE);

        // Get all the curCurrencyDateList where sourceUnitValue is less than or equal to SMALLER_SOURCE_UNIT_VALUE
        defaultCurCurrencyDateShouldNotBeFound("sourceUnitValue.lessThanOrEqual=" + SMALLER_SOURCE_UNIT_VALUE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesBySourceUnitValueIsLessThanSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where sourceUnitValue is less than DEFAULT_SOURCE_UNIT_VALUE
        defaultCurCurrencyDateShouldNotBeFound("sourceUnitValue.lessThan=" + DEFAULT_SOURCE_UNIT_VALUE);

        // Get all the curCurrencyDateList where sourceUnitValue is less than UPDATED_SOURCE_UNIT_VALUE
        defaultCurCurrencyDateShouldBeFound("sourceUnitValue.lessThan=" + UPDATED_SOURCE_UNIT_VALUE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesBySourceUnitValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where sourceUnitValue is greater than DEFAULT_SOURCE_UNIT_VALUE
        defaultCurCurrencyDateShouldNotBeFound("sourceUnitValue.greaterThan=" + DEFAULT_SOURCE_UNIT_VALUE);

        // Get all the curCurrencyDateList where sourceUnitValue is greater than SMALLER_SOURCE_UNIT_VALUE
        defaultCurCurrencyDateShouldBeFound("sourceUnitValue.greaterThan=" + SMALLER_SOURCE_UNIT_VALUE);
    }


    @Test
    @Transactional
    public void getAllCurCurrencyDatesByResultUnitValueIsEqualToSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where resultUnitValue equals to DEFAULT_RESULT_UNIT_VALUE
        defaultCurCurrencyDateShouldBeFound("resultUnitValue.equals=" + DEFAULT_RESULT_UNIT_VALUE);

        // Get all the curCurrencyDateList where resultUnitValue equals to UPDATED_RESULT_UNIT_VALUE
        defaultCurCurrencyDateShouldNotBeFound("resultUnitValue.equals=" + UPDATED_RESULT_UNIT_VALUE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesByResultUnitValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where resultUnitValue not equals to DEFAULT_RESULT_UNIT_VALUE
        defaultCurCurrencyDateShouldNotBeFound("resultUnitValue.notEquals=" + DEFAULT_RESULT_UNIT_VALUE);

        // Get all the curCurrencyDateList where resultUnitValue not equals to UPDATED_RESULT_UNIT_VALUE
        defaultCurCurrencyDateShouldBeFound("resultUnitValue.notEquals=" + UPDATED_RESULT_UNIT_VALUE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesByResultUnitValueIsInShouldWork() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where resultUnitValue in DEFAULT_RESULT_UNIT_VALUE or UPDATED_RESULT_UNIT_VALUE
        defaultCurCurrencyDateShouldBeFound("resultUnitValue.in=" + DEFAULT_RESULT_UNIT_VALUE + "," + UPDATED_RESULT_UNIT_VALUE);

        // Get all the curCurrencyDateList where resultUnitValue equals to UPDATED_RESULT_UNIT_VALUE
        defaultCurCurrencyDateShouldNotBeFound("resultUnitValue.in=" + UPDATED_RESULT_UNIT_VALUE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesByResultUnitValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where resultUnitValue is not null
        defaultCurCurrencyDateShouldBeFound("resultUnitValue.specified=true");

        // Get all the curCurrencyDateList where resultUnitValue is null
        defaultCurCurrencyDateShouldNotBeFound("resultUnitValue.specified=false");
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesByResultUnitValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where resultUnitValue is greater than or equal to DEFAULT_RESULT_UNIT_VALUE
        defaultCurCurrencyDateShouldBeFound("resultUnitValue.greaterThanOrEqual=" + DEFAULT_RESULT_UNIT_VALUE);

        // Get all the curCurrencyDateList where resultUnitValue is greater than or equal to UPDATED_RESULT_UNIT_VALUE
        defaultCurCurrencyDateShouldNotBeFound("resultUnitValue.greaterThanOrEqual=" + UPDATED_RESULT_UNIT_VALUE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesByResultUnitValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where resultUnitValue is less than or equal to DEFAULT_RESULT_UNIT_VALUE
        defaultCurCurrencyDateShouldBeFound("resultUnitValue.lessThanOrEqual=" + DEFAULT_RESULT_UNIT_VALUE);

        // Get all the curCurrencyDateList where resultUnitValue is less than or equal to SMALLER_RESULT_UNIT_VALUE
        defaultCurCurrencyDateShouldNotBeFound("resultUnitValue.lessThanOrEqual=" + SMALLER_RESULT_UNIT_VALUE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesByResultUnitValueIsLessThanSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where resultUnitValue is less than DEFAULT_RESULT_UNIT_VALUE
        defaultCurCurrencyDateShouldNotBeFound("resultUnitValue.lessThan=" + DEFAULT_RESULT_UNIT_VALUE);

        // Get all the curCurrencyDateList where resultUnitValue is less than UPDATED_RESULT_UNIT_VALUE
        defaultCurCurrencyDateShouldBeFound("resultUnitValue.lessThan=" + UPDATED_RESULT_UNIT_VALUE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesByResultUnitValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where resultUnitValue is greater than DEFAULT_RESULT_UNIT_VALUE
        defaultCurCurrencyDateShouldNotBeFound("resultUnitValue.greaterThan=" + DEFAULT_RESULT_UNIT_VALUE);

        // Get all the curCurrencyDateList where resultUnitValue is greater than SMALLER_RESULT_UNIT_VALUE
        defaultCurCurrencyDateShouldBeFound("resultUnitValue.greaterThan=" + SMALLER_RESULT_UNIT_VALUE);
    }


    @Test
    @Transactional
    public void getAllCurCurrencyDatesByBuyingRateIsEqualToSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where buyingRate equals to DEFAULT_BUYING_RATE
        defaultCurCurrencyDateShouldBeFound("buyingRate.equals=" + DEFAULT_BUYING_RATE);

        // Get all the curCurrencyDateList where buyingRate equals to UPDATED_BUYING_RATE
        defaultCurCurrencyDateShouldNotBeFound("buyingRate.equals=" + UPDATED_BUYING_RATE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesByBuyingRateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where buyingRate not equals to DEFAULT_BUYING_RATE
        defaultCurCurrencyDateShouldNotBeFound("buyingRate.notEquals=" + DEFAULT_BUYING_RATE);

        // Get all the curCurrencyDateList where buyingRate not equals to UPDATED_BUYING_RATE
        defaultCurCurrencyDateShouldBeFound("buyingRate.notEquals=" + UPDATED_BUYING_RATE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesByBuyingRateIsInShouldWork() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where buyingRate in DEFAULT_BUYING_RATE or UPDATED_BUYING_RATE
        defaultCurCurrencyDateShouldBeFound("buyingRate.in=" + DEFAULT_BUYING_RATE + "," + UPDATED_BUYING_RATE);

        // Get all the curCurrencyDateList where buyingRate equals to UPDATED_BUYING_RATE
        defaultCurCurrencyDateShouldNotBeFound("buyingRate.in=" + UPDATED_BUYING_RATE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesByBuyingRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where buyingRate is not null
        defaultCurCurrencyDateShouldBeFound("buyingRate.specified=true");

        // Get all the curCurrencyDateList where buyingRate is null
        defaultCurCurrencyDateShouldNotBeFound("buyingRate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesByBuyingRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where buyingRate is greater than or equal to DEFAULT_BUYING_RATE
        defaultCurCurrencyDateShouldBeFound("buyingRate.greaterThanOrEqual=" + DEFAULT_BUYING_RATE);

        // Get all the curCurrencyDateList where buyingRate is greater than or equal to UPDATED_BUYING_RATE
        defaultCurCurrencyDateShouldNotBeFound("buyingRate.greaterThanOrEqual=" + UPDATED_BUYING_RATE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesByBuyingRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where buyingRate is less than or equal to DEFAULT_BUYING_RATE
        defaultCurCurrencyDateShouldBeFound("buyingRate.lessThanOrEqual=" + DEFAULT_BUYING_RATE);

        // Get all the curCurrencyDateList where buyingRate is less than or equal to SMALLER_BUYING_RATE
        defaultCurCurrencyDateShouldNotBeFound("buyingRate.lessThanOrEqual=" + SMALLER_BUYING_RATE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesByBuyingRateIsLessThanSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where buyingRate is less than DEFAULT_BUYING_RATE
        defaultCurCurrencyDateShouldNotBeFound("buyingRate.lessThan=" + DEFAULT_BUYING_RATE);

        // Get all the curCurrencyDateList where buyingRate is less than UPDATED_BUYING_RATE
        defaultCurCurrencyDateShouldBeFound("buyingRate.lessThan=" + UPDATED_BUYING_RATE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesByBuyingRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where buyingRate is greater than DEFAULT_BUYING_RATE
        defaultCurCurrencyDateShouldNotBeFound("buyingRate.greaterThan=" + DEFAULT_BUYING_RATE);

        // Get all the curCurrencyDateList where buyingRate is greater than SMALLER_BUYING_RATE
        defaultCurCurrencyDateShouldBeFound("buyingRate.greaterThan=" + SMALLER_BUYING_RATE);
    }


    @Test
    @Transactional
    public void getAllCurCurrencyDatesBySellingRateIsEqualToSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where sellingRate equals to DEFAULT_SELLING_RATE
        defaultCurCurrencyDateShouldBeFound("sellingRate.equals=" + DEFAULT_SELLING_RATE);

        // Get all the curCurrencyDateList where sellingRate equals to UPDATED_SELLING_RATE
        defaultCurCurrencyDateShouldNotBeFound("sellingRate.equals=" + UPDATED_SELLING_RATE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesBySellingRateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where sellingRate not equals to DEFAULT_SELLING_RATE
        defaultCurCurrencyDateShouldNotBeFound("sellingRate.notEquals=" + DEFAULT_SELLING_RATE);

        // Get all the curCurrencyDateList where sellingRate not equals to UPDATED_SELLING_RATE
        defaultCurCurrencyDateShouldBeFound("sellingRate.notEquals=" + UPDATED_SELLING_RATE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesBySellingRateIsInShouldWork() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where sellingRate in DEFAULT_SELLING_RATE or UPDATED_SELLING_RATE
        defaultCurCurrencyDateShouldBeFound("sellingRate.in=" + DEFAULT_SELLING_RATE + "," + UPDATED_SELLING_RATE);

        // Get all the curCurrencyDateList where sellingRate equals to UPDATED_SELLING_RATE
        defaultCurCurrencyDateShouldNotBeFound("sellingRate.in=" + UPDATED_SELLING_RATE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesBySellingRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where sellingRate is not null
        defaultCurCurrencyDateShouldBeFound("sellingRate.specified=true");

        // Get all the curCurrencyDateList where sellingRate is null
        defaultCurCurrencyDateShouldNotBeFound("sellingRate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesBySellingRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where sellingRate is greater than or equal to DEFAULT_SELLING_RATE
        defaultCurCurrencyDateShouldBeFound("sellingRate.greaterThanOrEqual=" + DEFAULT_SELLING_RATE);

        // Get all the curCurrencyDateList where sellingRate is greater than or equal to UPDATED_SELLING_RATE
        defaultCurCurrencyDateShouldNotBeFound("sellingRate.greaterThanOrEqual=" + UPDATED_SELLING_RATE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesBySellingRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where sellingRate is less than or equal to DEFAULT_SELLING_RATE
        defaultCurCurrencyDateShouldBeFound("sellingRate.lessThanOrEqual=" + DEFAULT_SELLING_RATE);

        // Get all the curCurrencyDateList where sellingRate is less than or equal to SMALLER_SELLING_RATE
        defaultCurCurrencyDateShouldNotBeFound("sellingRate.lessThanOrEqual=" + SMALLER_SELLING_RATE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesBySellingRateIsLessThanSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where sellingRate is less than DEFAULT_SELLING_RATE
        defaultCurCurrencyDateShouldNotBeFound("sellingRate.lessThan=" + DEFAULT_SELLING_RATE);

        // Get all the curCurrencyDateList where sellingRate is less than UPDATED_SELLING_RATE
        defaultCurCurrencyDateShouldBeFound("sellingRate.lessThan=" + UPDATED_SELLING_RATE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesBySellingRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where sellingRate is greater than DEFAULT_SELLING_RATE
        defaultCurCurrencyDateShouldNotBeFound("sellingRate.greaterThan=" + DEFAULT_SELLING_RATE);

        // Get all the curCurrencyDateList where sellingRate is greater than SMALLER_SELLING_RATE
        defaultCurCurrencyDateShouldBeFound("sellingRate.greaterThan=" + SMALLER_SELLING_RATE);
    }


    @Test
    @Transactional
    public void getAllCurCurrencyDatesByEffectiveBuyingRateIsEqualToSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where effectiveBuyingRate equals to DEFAULT_EFFECTIVE_BUYING_RATE
        defaultCurCurrencyDateShouldBeFound("effectiveBuyingRate.equals=" + DEFAULT_EFFECTIVE_BUYING_RATE);

        // Get all the curCurrencyDateList where effectiveBuyingRate equals to UPDATED_EFFECTIVE_BUYING_RATE
        defaultCurCurrencyDateShouldNotBeFound("effectiveBuyingRate.equals=" + UPDATED_EFFECTIVE_BUYING_RATE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesByEffectiveBuyingRateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where effectiveBuyingRate not equals to DEFAULT_EFFECTIVE_BUYING_RATE
        defaultCurCurrencyDateShouldNotBeFound("effectiveBuyingRate.notEquals=" + DEFAULT_EFFECTIVE_BUYING_RATE);

        // Get all the curCurrencyDateList where effectiveBuyingRate not equals to UPDATED_EFFECTIVE_BUYING_RATE
        defaultCurCurrencyDateShouldBeFound("effectiveBuyingRate.notEquals=" + UPDATED_EFFECTIVE_BUYING_RATE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesByEffectiveBuyingRateIsInShouldWork() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where effectiveBuyingRate in DEFAULT_EFFECTIVE_BUYING_RATE or UPDATED_EFFECTIVE_BUYING_RATE
        defaultCurCurrencyDateShouldBeFound("effectiveBuyingRate.in=" + DEFAULT_EFFECTIVE_BUYING_RATE + "," + UPDATED_EFFECTIVE_BUYING_RATE);

        // Get all the curCurrencyDateList where effectiveBuyingRate equals to UPDATED_EFFECTIVE_BUYING_RATE
        defaultCurCurrencyDateShouldNotBeFound("effectiveBuyingRate.in=" + UPDATED_EFFECTIVE_BUYING_RATE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesByEffectiveBuyingRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where effectiveBuyingRate is not null
        defaultCurCurrencyDateShouldBeFound("effectiveBuyingRate.specified=true");

        // Get all the curCurrencyDateList where effectiveBuyingRate is null
        defaultCurCurrencyDateShouldNotBeFound("effectiveBuyingRate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesByEffectiveBuyingRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where effectiveBuyingRate is greater than or equal to DEFAULT_EFFECTIVE_BUYING_RATE
        defaultCurCurrencyDateShouldBeFound("effectiveBuyingRate.greaterThanOrEqual=" + DEFAULT_EFFECTIVE_BUYING_RATE);

        // Get all the curCurrencyDateList where effectiveBuyingRate is greater than or equal to UPDATED_EFFECTIVE_BUYING_RATE
        defaultCurCurrencyDateShouldNotBeFound("effectiveBuyingRate.greaterThanOrEqual=" + UPDATED_EFFECTIVE_BUYING_RATE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesByEffectiveBuyingRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where effectiveBuyingRate is less than or equal to DEFAULT_EFFECTIVE_BUYING_RATE
        defaultCurCurrencyDateShouldBeFound("effectiveBuyingRate.lessThanOrEqual=" + DEFAULT_EFFECTIVE_BUYING_RATE);

        // Get all the curCurrencyDateList where effectiveBuyingRate is less than or equal to SMALLER_EFFECTIVE_BUYING_RATE
        defaultCurCurrencyDateShouldNotBeFound("effectiveBuyingRate.lessThanOrEqual=" + SMALLER_EFFECTIVE_BUYING_RATE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesByEffectiveBuyingRateIsLessThanSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where effectiveBuyingRate is less than DEFAULT_EFFECTIVE_BUYING_RATE
        defaultCurCurrencyDateShouldNotBeFound("effectiveBuyingRate.lessThan=" + DEFAULT_EFFECTIVE_BUYING_RATE);

        // Get all the curCurrencyDateList where effectiveBuyingRate is less than UPDATED_EFFECTIVE_BUYING_RATE
        defaultCurCurrencyDateShouldBeFound("effectiveBuyingRate.lessThan=" + UPDATED_EFFECTIVE_BUYING_RATE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesByEffectiveBuyingRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where effectiveBuyingRate is greater than DEFAULT_EFFECTIVE_BUYING_RATE
        defaultCurCurrencyDateShouldNotBeFound("effectiveBuyingRate.greaterThan=" + DEFAULT_EFFECTIVE_BUYING_RATE);

        // Get all the curCurrencyDateList where effectiveBuyingRate is greater than SMALLER_EFFECTIVE_BUYING_RATE
        defaultCurCurrencyDateShouldBeFound("effectiveBuyingRate.greaterThan=" + SMALLER_EFFECTIVE_BUYING_RATE);
    }


    @Test
    @Transactional
    public void getAllCurCurrencyDatesByEffectiveSellingRateIsEqualToSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where effectiveSellingRate equals to DEFAULT_EFFECTIVE_SELLING_RATE
        defaultCurCurrencyDateShouldBeFound("effectiveSellingRate.equals=" + DEFAULT_EFFECTIVE_SELLING_RATE);

        // Get all the curCurrencyDateList where effectiveSellingRate equals to UPDATED_EFFECTIVE_SELLING_RATE
        defaultCurCurrencyDateShouldNotBeFound("effectiveSellingRate.equals=" + UPDATED_EFFECTIVE_SELLING_RATE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesByEffectiveSellingRateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where effectiveSellingRate not equals to DEFAULT_EFFECTIVE_SELLING_RATE
        defaultCurCurrencyDateShouldNotBeFound("effectiveSellingRate.notEquals=" + DEFAULT_EFFECTIVE_SELLING_RATE);

        // Get all the curCurrencyDateList where effectiveSellingRate not equals to UPDATED_EFFECTIVE_SELLING_RATE
        defaultCurCurrencyDateShouldBeFound("effectiveSellingRate.notEquals=" + UPDATED_EFFECTIVE_SELLING_RATE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesByEffectiveSellingRateIsInShouldWork() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where effectiveSellingRate in DEFAULT_EFFECTIVE_SELLING_RATE or UPDATED_EFFECTIVE_SELLING_RATE
        defaultCurCurrencyDateShouldBeFound("effectiveSellingRate.in=" + DEFAULT_EFFECTIVE_SELLING_RATE + "," + UPDATED_EFFECTIVE_SELLING_RATE);

        // Get all the curCurrencyDateList where effectiveSellingRate equals to UPDATED_EFFECTIVE_SELLING_RATE
        defaultCurCurrencyDateShouldNotBeFound("effectiveSellingRate.in=" + UPDATED_EFFECTIVE_SELLING_RATE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesByEffectiveSellingRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where effectiveSellingRate is not null
        defaultCurCurrencyDateShouldBeFound("effectiveSellingRate.specified=true");

        // Get all the curCurrencyDateList where effectiveSellingRate is null
        defaultCurCurrencyDateShouldNotBeFound("effectiveSellingRate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesByEffectiveSellingRateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where effectiveSellingRate is greater than or equal to DEFAULT_EFFECTIVE_SELLING_RATE
        defaultCurCurrencyDateShouldBeFound("effectiveSellingRate.greaterThanOrEqual=" + DEFAULT_EFFECTIVE_SELLING_RATE);

        // Get all the curCurrencyDateList where effectiveSellingRate is greater than or equal to UPDATED_EFFECTIVE_SELLING_RATE
        defaultCurCurrencyDateShouldNotBeFound("effectiveSellingRate.greaterThanOrEqual=" + UPDATED_EFFECTIVE_SELLING_RATE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesByEffectiveSellingRateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where effectiveSellingRate is less than or equal to DEFAULT_EFFECTIVE_SELLING_RATE
        defaultCurCurrencyDateShouldBeFound("effectiveSellingRate.lessThanOrEqual=" + DEFAULT_EFFECTIVE_SELLING_RATE);

        // Get all the curCurrencyDateList where effectiveSellingRate is less than or equal to SMALLER_EFFECTIVE_SELLING_RATE
        defaultCurCurrencyDateShouldNotBeFound("effectiveSellingRate.lessThanOrEqual=" + SMALLER_EFFECTIVE_SELLING_RATE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesByEffectiveSellingRateIsLessThanSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where effectiveSellingRate is less than DEFAULT_EFFECTIVE_SELLING_RATE
        defaultCurCurrencyDateShouldNotBeFound("effectiveSellingRate.lessThan=" + DEFAULT_EFFECTIVE_SELLING_RATE);

        // Get all the curCurrencyDateList where effectiveSellingRate is less than UPDATED_EFFECTIVE_SELLING_RATE
        defaultCurCurrencyDateShouldBeFound("effectiveSellingRate.lessThan=" + UPDATED_EFFECTIVE_SELLING_RATE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesByEffectiveSellingRateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where effectiveSellingRate is greater than DEFAULT_EFFECTIVE_SELLING_RATE
        defaultCurCurrencyDateShouldNotBeFound("effectiveSellingRate.greaterThan=" + DEFAULT_EFFECTIVE_SELLING_RATE);

        // Get all the curCurrencyDateList where effectiveSellingRate is greater than SMALLER_EFFECTIVE_SELLING_RATE
        defaultCurCurrencyDateShouldBeFound("effectiveSellingRate.greaterThan=" + SMALLER_EFFECTIVE_SELLING_RATE);
    }


    @Test
    @Transactional
    public void getAllCurCurrencyDatesByIsServiceIsEqualToSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where isService equals to DEFAULT_IS_SERVICE
        defaultCurCurrencyDateShouldBeFound("isService.equals=" + DEFAULT_IS_SERVICE);

        // Get all the curCurrencyDateList where isService equals to UPDATED_IS_SERVICE
        defaultCurCurrencyDateShouldNotBeFound("isService.equals=" + UPDATED_IS_SERVICE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesByIsServiceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where isService not equals to DEFAULT_IS_SERVICE
        defaultCurCurrencyDateShouldNotBeFound("isService.notEquals=" + DEFAULT_IS_SERVICE);

        // Get all the curCurrencyDateList where isService not equals to UPDATED_IS_SERVICE
        defaultCurCurrencyDateShouldBeFound("isService.notEquals=" + UPDATED_IS_SERVICE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesByIsServiceIsInShouldWork() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where isService in DEFAULT_IS_SERVICE or UPDATED_IS_SERVICE
        defaultCurCurrencyDateShouldBeFound("isService.in=" + DEFAULT_IS_SERVICE + "," + UPDATED_IS_SERVICE);

        // Get all the curCurrencyDateList where isService equals to UPDATED_IS_SERVICE
        defaultCurCurrencyDateShouldNotBeFound("isService.in=" + UPDATED_IS_SERVICE);
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesByIsServiceIsNullOrNotNull() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        // Get all the curCurrencyDateList where isService is not null
        defaultCurCurrencyDateShouldBeFound("isService.specified=true");

        // Get all the curCurrencyDateList where isService is null
        defaultCurCurrencyDateShouldNotBeFound("isService.specified=false");
    }

    @Test
    @Transactional
    public void getAllCurCurrencyDatesBySourceCurrencyIsEqualToSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);
        CurCurrencies sourceCurrency = CurCurrenciesResourceIT.createEntity(em);
        em.persist(sourceCurrency);
        em.flush();
        curCurrencyDate.setSourceCurrency(sourceCurrency);
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);
        Long sourceCurrencyId = sourceCurrency.getId();

        // Get all the curCurrencyDateList where sourceCurrency equals to sourceCurrencyId
        defaultCurCurrencyDateShouldBeFound("sourceCurrencyId.equals=" + sourceCurrencyId);

        // Get all the curCurrencyDateList where sourceCurrency equals to sourceCurrencyId + 1
        defaultCurCurrencyDateShouldNotBeFound("sourceCurrencyId.equals=" + (sourceCurrencyId + 1));
    }


    @Test
    @Transactional
    public void getAllCurCurrencyDatesByResultCurrencyIsEqualToSomething() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);
        CurCurrencies resultCurrency = CurCurrenciesResourceIT.createEntity(em);
        em.persist(resultCurrency);
        em.flush();
        curCurrencyDate.setResultCurrency(resultCurrency);
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);
        Long resultCurrencyId = resultCurrency.getId();

        // Get all the curCurrencyDateList where resultCurrency equals to resultCurrencyId
        defaultCurCurrencyDateShouldBeFound("resultCurrencyId.equals=" + resultCurrencyId);

        // Get all the curCurrencyDateList where resultCurrency equals to resultCurrencyId + 1
        defaultCurCurrencyDateShouldNotBeFound("resultCurrencyId.equals=" + (resultCurrencyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCurCurrencyDateShouldBeFound(String filter) throws Exception {
        restCurCurrencyDateMockMvc.perform(get("/api/cur-currency-dates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(curCurrencyDate.getId().intValue())))
            .andExpect(jsonPath("$.[*].currencyDate").value(hasItem(DEFAULT_CURRENCY_DATE.toString())))
            .andExpect(jsonPath("$.[*].sourceUnitValue").value(hasItem(DEFAULT_SOURCE_UNIT_VALUE)))
            .andExpect(jsonPath("$.[*].resultUnitValue").value(hasItem(DEFAULT_RESULT_UNIT_VALUE)))
            .andExpect(jsonPath("$.[*].buyingRate").value(hasItem(DEFAULT_BUYING_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].sellingRate").value(hasItem(DEFAULT_SELLING_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].effectiveBuyingRate").value(hasItem(DEFAULT_EFFECTIVE_BUYING_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].effectiveSellingRate").value(hasItem(DEFAULT_EFFECTIVE_SELLING_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].isService").value(hasItem(DEFAULT_IS_SERVICE.booleanValue())));

        // Check, that the count call also returns 1
        restCurCurrencyDateMockMvc.perform(get("/api/cur-currency-dates/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCurCurrencyDateShouldNotBeFound(String filter) throws Exception {
        restCurCurrencyDateMockMvc.perform(get("/api/cur-currency-dates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCurCurrencyDateMockMvc.perform(get("/api/cur-currency-dates/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCurCurrencyDate() throws Exception {
        // Get the curCurrencyDate
        restCurCurrencyDateMockMvc.perform(get("/api/cur-currency-dates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCurCurrencyDate() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        int databaseSizeBeforeUpdate = curCurrencyDateRepository.findAll().size();

        // Update the curCurrencyDate
        CurCurrencyDate updatedCurCurrencyDate = curCurrencyDateRepository.findById(curCurrencyDate.getId()).get();
        // Disconnect from session so that the updates on updatedCurCurrencyDate are not directly saved in db
        em.detach(updatedCurCurrencyDate);
        updatedCurCurrencyDate
            .currencyDate(UPDATED_CURRENCY_DATE)
            .sourceUnitValue(UPDATED_SOURCE_UNIT_VALUE)
            .resultUnitValue(UPDATED_RESULT_UNIT_VALUE)
            .buyingRate(UPDATED_BUYING_RATE)
            .sellingRate(UPDATED_SELLING_RATE)
            .effectiveBuyingRate(UPDATED_EFFECTIVE_BUYING_RATE)
            .effectiveSellingRate(UPDATED_EFFECTIVE_SELLING_RATE)
            .isService(UPDATED_IS_SERVICE);
        CurCurrencyDateDTO curCurrencyDateDTO = curCurrencyDateMapper.toDto(updatedCurCurrencyDate);

        restCurCurrencyDateMockMvc.perform(put("/api/cur-currency-dates")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(curCurrencyDateDTO)))
            .andExpect(status().isOk());

        // Validate the CurCurrencyDate in the database
        List<CurCurrencyDate> curCurrencyDateList = curCurrencyDateRepository.findAll();
        assertThat(curCurrencyDateList).hasSize(databaseSizeBeforeUpdate);
        CurCurrencyDate testCurCurrencyDate = curCurrencyDateList.get(curCurrencyDateList.size() - 1);
        assertThat(testCurCurrencyDate.getCurrencyDate()).isEqualTo(UPDATED_CURRENCY_DATE);
        assertThat(testCurCurrencyDate.getSourceUnitValue()).isEqualTo(UPDATED_SOURCE_UNIT_VALUE);
        assertThat(testCurCurrencyDate.getResultUnitValue()).isEqualTo(UPDATED_RESULT_UNIT_VALUE);
        assertThat(testCurCurrencyDate.getBuyingRate()).isEqualTo(UPDATED_BUYING_RATE);
        assertThat(testCurCurrencyDate.getSellingRate()).isEqualTo(UPDATED_SELLING_RATE);
        assertThat(testCurCurrencyDate.getEffectiveBuyingRate()).isEqualTo(UPDATED_EFFECTIVE_BUYING_RATE);
        assertThat(testCurCurrencyDate.getEffectiveSellingRate()).isEqualTo(UPDATED_EFFECTIVE_SELLING_RATE);
        assertThat(testCurCurrencyDate.isIsService()).isEqualTo(UPDATED_IS_SERVICE);
    }

    @Test
    @Transactional
    public void updateNonExistingCurCurrencyDate() throws Exception {
        int databaseSizeBeforeUpdate = curCurrencyDateRepository.findAll().size();

        // Create the CurCurrencyDate
        CurCurrencyDateDTO curCurrencyDateDTO = curCurrencyDateMapper.toDto(curCurrencyDate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCurCurrencyDateMockMvc.perform(put("/api/cur-currency-dates")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(curCurrencyDateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CurCurrencyDate in the database
        List<CurCurrencyDate> curCurrencyDateList = curCurrencyDateRepository.findAll();
        assertThat(curCurrencyDateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCurCurrencyDate() throws Exception {
        // Initialize the database
        curCurrencyDateRepository.saveAndFlush(curCurrencyDate);

        int databaseSizeBeforeDelete = curCurrencyDateRepository.findAll().size();

        // Delete the curCurrencyDate
        restCurCurrencyDateMockMvc.perform(delete("/api/cur-currency-dates/{id}", curCurrencyDate.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CurCurrencyDate> curCurrencyDateList = curCurrencyDateRepository.findAll();
        assertThat(curCurrencyDateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
