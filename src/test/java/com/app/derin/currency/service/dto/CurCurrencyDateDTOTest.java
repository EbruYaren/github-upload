package com.app.derin.currency.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.app.derin.currency.web.rest.TestUtil;

public class CurCurrencyDateDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CurCurrencyDateDTO.class);
        CurCurrencyDateDTO curCurrencyDateDTO1 = new CurCurrencyDateDTO();
        curCurrencyDateDTO1.setId(1L);
        CurCurrencyDateDTO curCurrencyDateDTO2 = new CurCurrencyDateDTO();
        assertThat(curCurrencyDateDTO1).isNotEqualTo(curCurrencyDateDTO2);
        curCurrencyDateDTO2.setId(curCurrencyDateDTO1.getId());
        assertThat(curCurrencyDateDTO1).isEqualTo(curCurrencyDateDTO2);
        curCurrencyDateDTO2.setId(2L);
        assertThat(curCurrencyDateDTO1).isNotEqualTo(curCurrencyDateDTO2);
        curCurrencyDateDTO1.setId(null);
        assertThat(curCurrencyDateDTO1).isNotEqualTo(curCurrencyDateDTO2);
    }
}
