package com.app.derin.currency.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.app.derin.currency.web.rest.TestUtil;

public class CurCurrencyMatrixDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CurCurrencyMatrixDTO.class);
        CurCurrencyMatrixDTO curCurrencyMatrixDTO1 = new CurCurrencyMatrixDTO();
        curCurrencyMatrixDTO1.setId(1L);
        CurCurrencyMatrixDTO curCurrencyMatrixDTO2 = new CurCurrencyMatrixDTO();
        assertThat(curCurrencyMatrixDTO1).isNotEqualTo(curCurrencyMatrixDTO2);
        curCurrencyMatrixDTO2.setId(curCurrencyMatrixDTO1.getId());
        assertThat(curCurrencyMatrixDTO1).isEqualTo(curCurrencyMatrixDTO2);
        curCurrencyMatrixDTO2.setId(2L);
        assertThat(curCurrencyMatrixDTO1).isNotEqualTo(curCurrencyMatrixDTO2);
        curCurrencyMatrixDTO1.setId(null);
        assertThat(curCurrencyMatrixDTO1).isNotEqualTo(curCurrencyMatrixDTO2);
    }
}
