package com.app.derin.currency.web.rest;

import com.app.derin.currency.DerincurrencyApp;
import com.app.derin.currency.config.SecurityBeanOverrideConfiguration;
import com.app.derin.currency.domain.CurConfig;
import com.app.derin.currency.ext.errors.MessageTranslator;
import com.app.derin.currency.repository.CurConfigRepository;
import com.app.derin.currency.service.CurConfigService;
import com.app.derin.currency.service.dto.CurConfigDTO;
import com.app.derin.currency.service.mapper.CurConfigMapper;
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
 * Integration tests for the {@link CurConfigResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, DerincurrencyApp.class})
public class CurConfigResourceIT {

    private static final String DEFAULT_CURRENCY_IMPORT_URL = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_IMPORT_URL = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENCY_IMPORT_TIME = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY_IMPORT_TIME = "BBBBBBBBBB";

    @Autowired
    private CurConfigRepository curConfigRepository;

    @Autowired
    private CurConfigMapper curConfigMapper;

    @Autowired
    private CurConfigService curConfigService;

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

    private MockMvc restCurConfigMockMvc;

    private CurConfig curConfig;

    private MessageTranslator messageTranslator;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CurConfigResource curConfigResource = new CurConfigResource(curConfigService, messageTranslator);
        this.restCurConfigMockMvc = MockMvcBuilders.standaloneSetup(curConfigResource)
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
    public static CurConfig createEntity(EntityManager em) {
        CurConfig curConfig = new CurConfig()
            .currencyImportUrl(DEFAULT_CURRENCY_IMPORT_URL)
            .currencyImportTime(DEFAULT_CURRENCY_IMPORT_TIME);
        return curConfig;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CurConfig createUpdatedEntity(EntityManager em) {
        CurConfig curConfig = new CurConfig()
            .currencyImportUrl(UPDATED_CURRENCY_IMPORT_URL)
            .currencyImportTime(UPDATED_CURRENCY_IMPORT_TIME);
        return curConfig;
    }

    @BeforeEach
    public void initTest() {
        curConfig = createEntity(em);
    }

    @Test
    @Transactional
    public void createCurConfig() throws Exception {
        int databaseSizeBeforeCreate = curConfigRepository.findAll().size();

        // Create the CurConfig
        CurConfigDTO curConfigDTO = curConfigMapper.toDto(curConfig);
        restCurConfigMockMvc.perform(post("/api/cur-configs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(curConfigDTO)))
            .andExpect(status().isCreated());

        // Validate the CurConfig in the database
        List<CurConfig> curConfigList = curConfigRepository.findAll();
        assertThat(curConfigList).hasSize(databaseSizeBeforeCreate + 1);
        CurConfig testCurConfig = curConfigList.get(curConfigList.size() - 1);
        assertThat(testCurConfig.getCurrencyImportUrl()).isEqualTo(DEFAULT_CURRENCY_IMPORT_URL);
        assertThat(testCurConfig.getCurrencyImportTime()).isEqualTo(DEFAULT_CURRENCY_IMPORT_TIME);
    }

    @Test
    @Transactional
    public void createCurConfigWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = curConfigRepository.findAll().size();

        // Create the CurConfig with an existing ID
        curConfig.setId(1L);
        CurConfigDTO curConfigDTO = curConfigMapper.toDto(curConfig);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCurConfigMockMvc.perform(post("/api/cur-configs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(curConfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CurConfig in the database
        List<CurConfig> curConfigList = curConfigRepository.findAll();
        assertThat(curConfigList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCurConfigs() throws Exception {
        // Initialize the database
        curConfigRepository.saveAndFlush(curConfig);

        // Get all the curConfigList
        restCurConfigMockMvc.perform(get("/api/cur-configs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(curConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].currencyImportUrl").value(hasItem(DEFAULT_CURRENCY_IMPORT_URL)))
            .andExpect(jsonPath("$.[*].currencyImportTime").value(hasItem(DEFAULT_CURRENCY_IMPORT_TIME)));
    }

    @Test
    @Transactional
    public void getCurConfig() throws Exception {
        // Initialize the database
        curConfigRepository.saveAndFlush(curConfig);

        // Get the curConfig
        restCurConfigMockMvc.perform(get("/api/cur-configs/{id}", curConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(curConfig.getId().intValue()))
            .andExpect(jsonPath("$.currencyImportUrl").value(DEFAULT_CURRENCY_IMPORT_URL))
            .andExpect(jsonPath("$.currencyImportTime").value(DEFAULT_CURRENCY_IMPORT_TIME));
    }

    @Test
    @Transactional
    public void getNonExistingCurConfig() throws Exception {
        // Get the curConfig
        restCurConfigMockMvc.perform(get("/api/cur-configs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCurConfig() throws Exception {
        // Initialize the database
        curConfigRepository.saveAndFlush(curConfig);

        int databaseSizeBeforeUpdate = curConfigRepository.findAll().size();

        // Update the curConfig
        CurConfig updatedCurConfig = curConfigRepository.findById(curConfig.getId()).get();
        // Disconnect from session so that the updates on updatedCurConfig are not directly saved in db
        em.detach(updatedCurConfig);
        updatedCurConfig
            .currencyImportUrl(UPDATED_CURRENCY_IMPORT_URL)
            .currencyImportTime(UPDATED_CURRENCY_IMPORT_TIME);
        CurConfigDTO curConfigDTO = curConfigMapper.toDto(updatedCurConfig);

        restCurConfigMockMvc.perform(put("/api/cur-configs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(curConfigDTO)))
            .andExpect(status().isOk());

        // Validate the CurConfig in the database
        List<CurConfig> curConfigList = curConfigRepository.findAll();
        assertThat(curConfigList).hasSize(databaseSizeBeforeUpdate);
        CurConfig testCurConfig = curConfigList.get(curConfigList.size() - 1);
        assertThat(testCurConfig.getCurrencyImportUrl()).isEqualTo(UPDATED_CURRENCY_IMPORT_URL);
        assertThat(testCurConfig.getCurrencyImportTime()).isEqualTo(UPDATED_CURRENCY_IMPORT_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingCurConfig() throws Exception {
        int databaseSizeBeforeUpdate = curConfigRepository.findAll().size();

        // Create the CurConfig
        CurConfigDTO curConfigDTO = curConfigMapper.toDto(curConfig);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCurConfigMockMvc.perform(put("/api/cur-configs")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(curConfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CurConfig in the database
        List<CurConfig> curConfigList = curConfigRepository.findAll();
        assertThat(curConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCurConfig() throws Exception {
        // Initialize the database
        curConfigRepository.saveAndFlush(curConfig);

        int databaseSizeBeforeDelete = curConfigRepository.findAll().size();

        // Delete the curConfig
        restCurConfigMockMvc.perform(delete("/api/cur-configs/{id}", curConfig.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CurConfig> curConfigList = curConfigRepository.findAll();
        assertThat(curConfigList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
