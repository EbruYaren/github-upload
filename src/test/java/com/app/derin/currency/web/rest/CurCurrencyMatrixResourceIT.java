package com.app.derin.currency.web.rest;

import com.app.derin.currency.DerincurrencyApp;
import com.app.derin.currency.config.SecurityBeanOverrideConfiguration;
import com.app.derin.currency.domain.CurCurrencyMatrix;
import com.app.derin.currency.ext.errors.MessageTranslator;
import com.app.derin.currency.repository.CurCurrencyMatrixRepository;
import com.app.derin.currency.service.CurCurrencyMatrixService;
import com.app.derin.currency.service.dto.CurCurrencyMatrixDTO;
import com.app.derin.currency.service.mapper.CurCurrencyMatrixMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.app.derin.currency.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CurCurrencyMatrixResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, DerincurrencyApp.class})
public class CurCurrencyMatrixResourceIT {

    private static final Integer DEFAULT_SOURCE_UNIT_VALUE = 1;
    private static final Integer UPDATED_SOURCE_UNIT_VALUE = 2;

    private static final Integer DEFAULT_RESULT_UNIT_VALUE = 1;
    private static final Integer UPDATED_RESULT_UNIT_VALUE = 2;

    private static final Double DEFAULT_BUYING_RATE = 1D;
    private static final Double UPDATED_BUYING_RATE = 2D;

    private static final Double DEFAULT_SELLING_RATE = 1D;
    private static final Double UPDATED_SELLING_RATE = 2D;

    private static final Double DEFAULT_EFFECTIVE_BUYING_RATE = 1D;
    private static final Double UPDATED_EFFECTIVE_BUYING_RATE = 2D;

    private static final Double DEFAULT_EFFECTIVE_SELLING_RATE = 1D;
    private static final Double UPDATED_EFFECTIVE_SELLING_RATE = 2D;

    private static final LocalDate DEFAULT_LAST_CURRENCY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_CURRENCY_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CurCurrencyMatrixRepository curCurrencyMatrixRepository;

    @Autowired
    private CurCurrencyMatrixMapper curCurrencyMatrixMapper;

    @Autowired
    private CurCurrencyMatrixService curCurrencyMatrixService;

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

    private MockMvc restCurCurrencyMatrixMockMvc;

    private CurCurrencyMatrix curCurrencyMatrix;

    private MessageTranslator messageTranslator;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CurCurrencyMatrixResource curCurrencyMatrixResource = new CurCurrencyMatrixResource(curCurrencyMatrixService, messageTranslator);
        this.restCurCurrencyMatrixMockMvc = MockMvcBuilders.standaloneSetup(curCurrencyMatrixResource)
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
    public static CurCurrencyMatrix createEntity(EntityManager em) {
        CurCurrencyMatrix curCurrencyMatrix = new CurCurrencyMatrix()
            .sourceUnitValue(DEFAULT_SOURCE_UNIT_VALUE)
            .resultUnitValue(DEFAULT_RESULT_UNIT_VALUE)
            .buyingRate(DEFAULT_BUYING_RATE)
            .sellingRate(DEFAULT_SELLING_RATE)
            .effectiveBuyingRate(DEFAULT_EFFECTIVE_BUYING_RATE)
            .effectiveSellingRate(DEFAULT_EFFECTIVE_SELLING_RATE)
            .lastCurrencyDate(DEFAULT_LAST_CURRENCY_DATE);
        return curCurrencyMatrix;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CurCurrencyMatrix createUpdatedEntity(EntityManager em) {
        CurCurrencyMatrix curCurrencyMatrix = new CurCurrencyMatrix()
            .sourceUnitValue(UPDATED_SOURCE_UNIT_VALUE)
            .resultUnitValue(UPDATED_RESULT_UNIT_VALUE)
            .buyingRate(UPDATED_BUYING_RATE)
            .sellingRate(UPDATED_SELLING_RATE)
            .effectiveBuyingRate(UPDATED_EFFECTIVE_BUYING_RATE)
            .effectiveSellingRate(UPDATED_EFFECTIVE_SELLING_RATE)
            .lastCurrencyDate(UPDATED_LAST_CURRENCY_DATE);
        return curCurrencyMatrix;
    }

    @BeforeEach
    public void initTest() {
        curCurrencyMatrix = createEntity(em);
    }

    @Test
    @Transactional
    public void createCurCurrencyMatrix() throws Exception {
        int databaseSizeBeforeCreate = curCurrencyMatrixRepository.findAll().size();

        // Create the CurCurrencyMatrix
        CurCurrencyMatrixDTO curCurrencyMatrixDTO = curCurrencyMatrixMapper.toDto(curCurrencyMatrix);
        restCurCurrencyMatrixMockMvc.perform(post("/api/cur-currency-matrices")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(curCurrencyMatrixDTO)))
            .andExpect(status().isCreated());

        // Validate the CurCurrencyMatrix in the database
        List<CurCurrencyMatrix> curCurrencyMatrixList = curCurrencyMatrixRepository.findAll();
        assertThat(curCurrencyMatrixList).hasSize(databaseSizeBeforeCreate + 1);
        CurCurrencyMatrix testCurCurrencyMatrix = curCurrencyMatrixList.get(curCurrencyMatrixList.size() - 1);
        assertThat(testCurCurrencyMatrix.getSourceUnitValue()).isEqualTo(DEFAULT_SOURCE_UNIT_VALUE);
        assertThat(testCurCurrencyMatrix.getResultUnitValue()).isEqualTo(DEFAULT_RESULT_UNIT_VALUE);
        assertThat(testCurCurrencyMatrix.getBuyingRate()).isEqualTo(DEFAULT_BUYING_RATE);
        assertThat(testCurCurrencyMatrix.getSellingRate()).isEqualTo(DEFAULT_SELLING_RATE);
        assertThat(testCurCurrencyMatrix.getEffectiveBuyingRate()).isEqualTo(DEFAULT_EFFECTIVE_BUYING_RATE);
        assertThat(testCurCurrencyMatrix.getEffectiveSellingRate()).isEqualTo(DEFAULT_EFFECTIVE_SELLING_RATE);
        assertThat(testCurCurrencyMatrix.getLastCurrencyDate()).isEqualTo(DEFAULT_LAST_CURRENCY_DATE);
    }

    @Test
    @Transactional
    public void createCurCurrencyMatrixWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = curCurrencyMatrixRepository.findAll().size();

        // Create the CurCurrencyMatrix with an existing ID
        curCurrencyMatrix.setId(1L);
        CurCurrencyMatrixDTO curCurrencyMatrixDTO = curCurrencyMatrixMapper.toDto(curCurrencyMatrix);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCurCurrencyMatrixMockMvc.perform(post("/api/cur-currency-matrices")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(curCurrencyMatrixDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CurCurrencyMatrix in the database
        List<CurCurrencyMatrix> curCurrencyMatrixList = curCurrencyMatrixRepository.findAll();
        assertThat(curCurrencyMatrixList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCurCurrencyMatrices() throws Exception {
        // Initialize the database
        curCurrencyMatrixRepository.saveAndFlush(curCurrencyMatrix);

        // Get all the curCurrencyMatrixList
        restCurCurrencyMatrixMockMvc.perform(get("/api/cur-currency-matrices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(curCurrencyMatrix.getId().intValue())))
            .andExpect(jsonPath("$.[*].sourceUnitValue").value(hasItem(DEFAULT_SOURCE_UNIT_VALUE)))
            .andExpect(jsonPath("$.[*].resultUnitValue").value(hasItem(DEFAULT_RESULT_UNIT_VALUE)))
            .andExpect(jsonPath("$.[*].buyingRate").value(hasItem(DEFAULT_BUYING_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].sellingRate").value(hasItem(DEFAULT_SELLING_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].effectiveBuyingRate").value(hasItem(DEFAULT_EFFECTIVE_BUYING_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].effectiveSellingRate").value(hasItem(DEFAULT_EFFECTIVE_SELLING_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].lastCurrencyDate").value(hasItem(DEFAULT_LAST_CURRENCY_DATE.toString())));
    }

    @Test
    @Transactional
    public void getCurCurrencyMatrix() throws Exception {
        // Initialize the database
        curCurrencyMatrixRepository.saveAndFlush(curCurrencyMatrix);

        // Get the curCurrencyMatrix
        restCurCurrencyMatrixMockMvc.perform(get("/api/cur-currency-matrices/{id}", curCurrencyMatrix.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(curCurrencyMatrix.getId().intValue()))
            .andExpect(jsonPath("$.sourceUnitValue").value(DEFAULT_SOURCE_UNIT_VALUE))
            .andExpect(jsonPath("$.resultUnitValue").value(DEFAULT_RESULT_UNIT_VALUE))
            .andExpect(jsonPath("$.buyingRate").value(DEFAULT_BUYING_RATE.doubleValue()))
            .andExpect(jsonPath("$.sellingRate").value(DEFAULT_SELLING_RATE.doubleValue()))
            .andExpect(jsonPath("$.effectiveBuyingRate").value(DEFAULT_EFFECTIVE_BUYING_RATE.doubleValue()))
            .andExpect(jsonPath("$.effectiveSellingRate").value(DEFAULT_EFFECTIVE_SELLING_RATE.doubleValue()))
            .andExpect(jsonPath("$.lastCurrencyDate").value(DEFAULT_LAST_CURRENCY_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCurCurrencyMatrix() throws Exception {
        // Get the curCurrencyMatrix
        restCurCurrencyMatrixMockMvc.perform(get("/api/cur-currency-matrices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCurCurrencyMatrix() throws Exception {
        // Initialize the database
        curCurrencyMatrixRepository.saveAndFlush(curCurrencyMatrix);

        int databaseSizeBeforeUpdate = curCurrencyMatrixRepository.findAll().size();

        // Update the curCurrencyMatrix
        CurCurrencyMatrix updatedCurCurrencyMatrix = curCurrencyMatrixRepository.findById(curCurrencyMatrix.getId()).get();
        // Disconnect from session so that the updates on updatedCurCurrencyMatrix are not directly saved in db
        em.detach(updatedCurCurrencyMatrix);
        updatedCurCurrencyMatrix
            .sourceUnitValue(UPDATED_SOURCE_UNIT_VALUE)
            .resultUnitValue(UPDATED_RESULT_UNIT_VALUE)
            .buyingRate(UPDATED_BUYING_RATE)
            .sellingRate(UPDATED_SELLING_RATE)
            .effectiveBuyingRate(UPDATED_EFFECTIVE_BUYING_RATE)
            .effectiveSellingRate(UPDATED_EFFECTIVE_SELLING_RATE)
            .lastCurrencyDate(UPDATED_LAST_CURRENCY_DATE);
        CurCurrencyMatrixDTO curCurrencyMatrixDTO = curCurrencyMatrixMapper.toDto(updatedCurCurrencyMatrix);

        restCurCurrencyMatrixMockMvc.perform(put("/api/cur-currency-matrices")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(curCurrencyMatrixDTO)))
            .andExpect(status().isOk());

        // Validate the CurCurrencyMatrix in the database
        List<CurCurrencyMatrix> curCurrencyMatrixList = curCurrencyMatrixRepository.findAll();
        assertThat(curCurrencyMatrixList).hasSize(databaseSizeBeforeUpdate);
        CurCurrencyMatrix testCurCurrencyMatrix = curCurrencyMatrixList.get(curCurrencyMatrixList.size() - 1);
        assertThat(testCurCurrencyMatrix.getSourceUnitValue()).isEqualTo(UPDATED_SOURCE_UNIT_VALUE);
        assertThat(testCurCurrencyMatrix.getResultUnitValue()).isEqualTo(UPDATED_RESULT_UNIT_VALUE);
        assertThat(testCurCurrencyMatrix.getBuyingRate()).isEqualTo(UPDATED_BUYING_RATE);
        assertThat(testCurCurrencyMatrix.getSellingRate()).isEqualTo(UPDATED_SELLING_RATE);
        assertThat(testCurCurrencyMatrix.getEffectiveBuyingRate()).isEqualTo(UPDATED_EFFECTIVE_BUYING_RATE);
        assertThat(testCurCurrencyMatrix.getEffectiveSellingRate()).isEqualTo(UPDATED_EFFECTIVE_SELLING_RATE);
        assertThat(testCurCurrencyMatrix.getLastCurrencyDate()).isEqualTo(UPDATED_LAST_CURRENCY_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCurCurrencyMatrix() throws Exception {
        int databaseSizeBeforeUpdate = curCurrencyMatrixRepository.findAll().size();

        // Create the CurCurrencyMatrix
        CurCurrencyMatrixDTO curCurrencyMatrixDTO = curCurrencyMatrixMapper.toDto(curCurrencyMatrix);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCurCurrencyMatrixMockMvc.perform(put("/api/cur-currency-matrices")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(curCurrencyMatrixDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CurCurrencyMatrix in the database
        List<CurCurrencyMatrix> curCurrencyMatrixList = curCurrencyMatrixRepository.findAll();
        assertThat(curCurrencyMatrixList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCurCurrencyMatrix() throws Exception {
        // Initialize the database
        curCurrencyMatrixRepository.saveAndFlush(curCurrencyMatrix);

        int databaseSizeBeforeDelete = curCurrencyMatrixRepository.findAll().size();

        // Delete the curCurrencyMatrix
        restCurCurrencyMatrixMockMvc.perform(delete("/api/cur-currency-matrices/{id}", curCurrencyMatrix.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CurCurrencyMatrix> curCurrencyMatrixList = curCurrencyMatrixRepository.findAll();
        assertThat(curCurrencyMatrixList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
