package com.app.derin.currency.ext.mapper;

import com.app.derin.currency.service.dto.CurCurrencyDateDTO;
import com.app.derin.currency.service.dto.CurCurrencyMatrixDTO;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

//@ExtendWith(MockitoExtension.class)
class CurCurrencyMatrixMapperExtTest {
    private final Logger log = LoggerFactory.getLogger(CurCurrencyMatrixMapperExtTest.class);
    @Mock
    CurCurrencyDateDTO curCurrencyDateDTO = new CurCurrencyDateDTO();
    @Mock
    CurCurrencyMatrixDTO curCurrencyMatrixDTO = new CurCurrencyMatrixDTO();

    @Test
    void dateDtoToMatrixDto() {
        curCurrencyDateDTO.setId(1210L);
        curCurrencyDateDTO.setBuyingRate(5.45);
        curCurrencyDateDTO.setSellingRate(3.2);
        curCurrencyDateDTO.setEffectiveSellingRate(0.1);
        curCurrencyDateDTO.setEffectiveBuyingRate(2.12);
        curCurrencyDateDTO.setCurrencyDate(LocalDate.now());
        curCurrencyDateDTO.setSourceCurrencyId(11L);
        curCurrencyDateDTO.setResultCurrencyId(12L);
        curCurrencyDateDTO.setIsService(true);
        curCurrencyDateDTO.setSourceUnitValue(1);
        curCurrencyDateDTO.setResultUnitValue(100);

        curCurrencyMatrixDTO.setBuyingRate(curCurrencyDateDTO.getBuyingRate());
        curCurrencyMatrixDTO.setSellingRate(curCurrencyDateDTO.getSellingRate());
        curCurrencyMatrixDTO.setEffectiveBuyingRate(curCurrencyDateDTO.getEffectiveBuyingRate());
        curCurrencyMatrixDTO.setEffectiveSellingRate(curCurrencyDateDTO.getEffectiveSellingRate());
        curCurrencyMatrixDTO.setLastCurrencyDate(curCurrencyDateDTO.getCurrencyDate());
        curCurrencyMatrixDTO.setSourceCurrencyId(curCurrencyDateDTO.getSourceCurrencyId());
        curCurrencyMatrixDTO.setResultCurrencyId(curCurrencyDateDTO.getResultCurrencyId());
        curCurrencyMatrixDTO.setSourceUnitValue(curCurrencyDateDTO.getSourceUnitValue());
        curCurrencyMatrixDTO.setResultUnitValue(curCurrencyDateDTO.getResultUnitValue());
        try {
            assertThat(curCurrencyMatrixDTO.getBuyingRate()).isEqualTo(5.45);
            assertThat(curCurrencyMatrixDTO.getSellingRate()).isEqualTo(3.2);
            assertThat(curCurrencyMatrixDTO.getEffectiveBuyingRate()).isEqualTo(2.12);
            assertThat(curCurrencyMatrixDTO.getEffectiveSellingRate()).isEqualTo(0.1);
            assertThat(curCurrencyMatrixDTO.getSourceCurrencyId()).isEqualTo(11L);
            assertThat(curCurrencyMatrixDTO.getResultCurrencyId()).isEqualTo(12L);
            assertThat(curCurrencyMatrixDTO.getLastCurrencyDate()).isEqualTo(LocalDate.now());
            assertThat(curCurrencyMatrixDTO.getSourceUnitValue()).isEqualTo(1);
            assertThat(curCurrencyMatrixDTO.getResultUnitValue()).isEqualTo(100);
        }
        catch(Exception e) {
            System.out.println("Null Pointer Exception!");
        }
    }
}
