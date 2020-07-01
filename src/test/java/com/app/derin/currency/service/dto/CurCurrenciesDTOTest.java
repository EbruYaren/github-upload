package com.app.derin.currency.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.app.derin.currency.web.rest.TestUtil;

public class CurCurrenciesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CurCurrenciesDTO.class);
        CurCurrenciesDTO curCurrenciesDTO1 = new CurCurrenciesDTO();
        curCurrenciesDTO1.setId(1L);
        CurCurrenciesDTO curCurrenciesDTO2 = new CurCurrenciesDTO();
        assertThat(curCurrenciesDTO1).isNotEqualTo(curCurrenciesDTO2);
        curCurrenciesDTO2.setId(curCurrenciesDTO1.getId());
        assertThat(curCurrenciesDTO1).isEqualTo(curCurrenciesDTO2);
        curCurrenciesDTO2.setId(2L);
        assertThat(curCurrenciesDTO1).isNotEqualTo(curCurrenciesDTO2);
        curCurrenciesDTO1.setId(null);
        assertThat(curCurrenciesDTO1).isNotEqualTo(curCurrenciesDTO2);
    }
}
