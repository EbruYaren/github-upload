package com.app.derin.currency.service.impl;

import com.app.derin.currency.domain.CurCurrencies;
import com.app.derin.currency.domain.CurCurrencyMatrix;
import com.app.derin.currency.ext.mapper.CurCurrencyMatrixMapperExt;
import com.app.derin.currency.repository.CurCurrenciesRepository;
import com.app.derin.currency.repository.CurCurrencyDateRepository;
import com.app.derin.currency.repository.CurCurrencyMatrixRepository;
import com.app.derin.currency.service.CurCurrencyMatrixService;
import com.app.derin.currency.service.dto.CurCurrenciesDTO;
import com.app.derin.currency.service.dto.CurCurrencyDateDTO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.app.derin.currency.service.dto.CurCurrencyMatrixDTO;
import com.app.derin.currency.service.mapper.CurCurrencyMatrixMapper;
import org.checkerframework.checker.units.qual.C;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class CurCurrencyMatrixServiceImplTest {

    private final Logger log = LoggerFactory.getLogger(CurCurrencyMatrixServiceImplTest.class);

    private static final Long DEFAULT_ID = 10L;
    private static final String DEFAULT_CUR_CODE = "BLA";
    private static final String DEFAULT_CUR_NAME = "Bla";
    private static final LocalDate DEFAULT_DATE = LocalDate.now().minusDays(1);
    private static final Double DEFAULT_BUYING = 5.455;
    private static final Double DEFAULT_SELLING = 4.1;
    private static final Double DEFAULT_EFFECTIVE_BUYING = 0.14;
    private static final Double DEFAULT_EFFECTIVE_SELLING = 0.85;

    private static final Long DEFAULT_SOURCE_ID = 1205L;
    private static final String DEFAULT_SOURCE_CUR_CODE = "SO";
    private static final String DEFAULT_SOURCE_CUR_NAME = "Source";
    private static final String DEFAULT_SOURCE_CUR_SYMBOL = "S";

    private static final Long DEFAULT_RESULT_ID = 1201L;
    private static final String DEFAULT_RESULT_CUR_CODE = "RE";
    private static final String DEFAULT_RESULT_CUR_NAME = "Result";
    private static final String DEFAULT_RESULT_CUR_SYMBOL = "R";

    @Mock
    CurCurrencyMatrix curCurrencyMatrix = new CurCurrencyMatrix();
    @Mock
    CurCurrencyMatrix curCurrencyMatrix2 = null;
    @Mock
    CurCurrencyMatrix curCurrencyMatrix3 = new CurCurrencyMatrix();

    @Mock
    CurCurrencyMatrixDTO curCurrencyMatrixDTO = new CurCurrencyMatrixDTO();

    @Mock
    CurCurrencyDateDTO curCurrencyDateDTO = new CurCurrencyDateDTO();
    @Mock
    CurCurrencyDateDTO curCurrencyDateDTO2 = new CurCurrencyDateDTO();

    private List<CurCurrencyDateDTO> curCurrencyDateDTOList = new ArrayList<>();

    @Mock
    CurCurrencyMatrixRepository curCurrencyMatrixRepository;

    @Mock
    CurCurrencyMatrixMapper curCurrencyMatrixMapper;

    @Mock
    CurCurrencyMatrixMapperExt curCurrencyMatrixMapperExt;

    @Mock
    CurCurrencies sourceCurrency;
    @Mock
    CurCurrencies resultCurrency;
    @Mock
    CurCurrencies sourceCurrency2;
    @Mock
    CurCurrencies resultCurrency2;

    @Before
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    public void init() {
        curCurrencyMatrix = new CurCurrencyMatrix();
        curCurrencyMatrix.setId(DEFAULT_ID);
        curCurrencyMatrix.setBuyingRate(DEFAULT_BUYING);
        curCurrencyMatrix.setSellingRate(DEFAULT_SELLING);
        curCurrencyMatrix.setLastCurrencyDate(DEFAULT_DATE);
        curCurrencyMatrix.setEffectiveSellingRate(DEFAULT_EFFECTIVE_SELLING);
        curCurrencyMatrix.setEffectiveBuyingRate(DEFAULT_EFFECTIVE_BUYING);
        curCurrencyMatrix.setSourceCurrency(sourceCurrency);
        curCurrencyMatrix.setResultCurrency(resultCurrency);

        sourceCurrency = new CurCurrencies();
        sourceCurrency.setId(DEFAULT_SOURCE_ID);
        sourceCurrency.setCurrencyCode(DEFAULT_SOURCE_CUR_CODE);
        sourceCurrency.setCurrencyName(DEFAULT_SOURCE_CUR_NAME);
        sourceCurrency.setCurrencySymbol(DEFAULT_SOURCE_CUR_SYMBOL);

        resultCurrency = new CurCurrencies();
        resultCurrency.setId(DEFAULT_RESULT_ID);
        resultCurrency.setCurrencyCode(DEFAULT_RESULT_CUR_CODE);
        resultCurrency.setCurrencyName(DEFAULT_RESULT_CUR_NAME);
        resultCurrency.setCurrencySymbol(DEFAULT_RESULT_CUR_SYMBOL);

        sourceCurrency2 = new CurCurrencies();
        sourceCurrency2.setId(10L);

        resultCurrency2 = new CurCurrencies();
        resultCurrency2.setId(11L);

        curCurrencyMatrix3.setSourceCurrency(sourceCurrency2);
        curCurrencyMatrix3.setResultCurrency(resultCurrency2);
        curCurrencyMatrix3.setLastCurrencyDate(LocalDate.now().minusDays(2));
        curCurrencyMatrix3.setBuyingRate(3.52);
        curCurrencyMatrix3.setSellingRate(9.85);
        curCurrencyMatrix3.setEffectiveBuyingRate(0.21);
        curCurrencyMatrix3.setEffectiveSellingRate(12.12);

        curCurrencyMatrix.setSourceCurrency(sourceCurrency);
        curCurrencyMatrix.setResultCurrency(resultCurrency);
    }

    @Test
    void save() {
    }

    @Test
    void updateTable() {
        curCurrencyDateDTO = new CurCurrencyDateDTO();
        curCurrencyDateDTO.setId(1L);
        curCurrencyDateDTO.setBuyingRate(6.454);
        curCurrencyDateDTO.setSellingRate(6.544);
        curCurrencyDateDTO.setCurrencyDate(LocalDate.now());
        curCurrencyDateDTO.setSourceCurrencyId(1205L);
        curCurrencyDateDTO.setResultCurrencyId(1201L);
        curCurrencyDateDTO.setEffectiveBuyingRate(5.455);
        curCurrencyDateDTO.setEffectiveSellingRate(5.474);
        curCurrencyDateDTO.setIsService(true);
        curCurrencyDateDTOList.add(curCurrencyDateDTO);

        curCurrencyDateDTO2 = new CurCurrencyDateDTO();
        curCurrencyDateDTO2.setId(2L);
        curCurrencyDateDTO2.setBuyingRate(3.52);
        curCurrencyDateDTO2.setSellingRate(9.85);
        curCurrencyDateDTO2.setCurrencyDate(LocalDate.now());
        curCurrencyDateDTO2.setSourceCurrencyId(10L);
        curCurrencyDateDTO2.setResultCurrencyId(11L);
        curCurrencyDateDTO2.setEffectiveBuyingRate(0.21);
        curCurrencyDateDTO2.setEffectiveSellingRate(12.12);
        curCurrencyDateDTO2.setIsService(true);
        curCurrencyDateDTOList.add(curCurrencyDateDTO2);

        assertNotNull(curCurrencyDateDTO.getSourceCurrencyId());
        assertNotNull(curCurrencyDateDTO.getResultCurrencyId());
        assertNotNull(curCurrencyDateDTO2.getSourceCurrencyId());
        assertNotNull(curCurrencyDateDTO2.getResultCurrencyId());

            try {
                when(curCurrencyMatrixRepository.findBySourceCurrencyAndResultCurrency(curCurrencyDateDTO.getSourceCurrencyId(), curCurrencyDateDTO.getResultCurrencyId())).thenReturn(curCurrencyMatrix);
                when(curCurrencyMatrixRepository.findBySourceCurrencyAndResultCurrency(curCurrencyDateDTO2.getSourceCurrencyId(), curCurrencyDateDTO2.getResultCurrencyId())).thenReturn(curCurrencyMatrix2);
                //INSERT
                    when(curCurrencyMatrixMapperExt.dateDtoToMatrixDto(curCurrencyDateDTO2)).thenReturn(curCurrencyMatrixDTO);

                    //UPDATE
                    when(curCurrencyMatrixMapper.toDto(curCurrencyMatrix)).thenReturn(curCurrencyMatrixDTO);
                    curCurrencyMatrixDTO.setLastCurrencyDate(curCurrencyDateDTO.getCurrencyDate());
                    curCurrencyMatrixDTO.setBuyingRate(curCurrencyDateDTO.getBuyingRate());
                    curCurrencyMatrixDTO.setSellingRate(curCurrencyDateDTO.getSellingRate());
                    curCurrencyMatrixDTO.setEffectiveSellingRate(curCurrencyDateDTO.getEffectiveSellingRate());
                    curCurrencyMatrixDTO.setEffectiveBuyingRate(curCurrencyDateDTO.getEffectiveBuyingRate());
                    when(curCurrencyMatrixRepository.save(curCurrencyMatrix)).thenReturn(curCurrencyMatrix);
            } catch (Exception e) {
                System.out.println("Null Pointer Exception!");
            }
        }

    @Test
    void findAll() {
    }

    @Test
    void findOne() {
    }

    @Test
    void delete() {
    }
}
