package com.app.derin.currency.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.app.derin.currency.web.rest.TestUtil;

public class CurCurrencyDateTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CurCurrencyDate.class);
        CurCurrencyDate curCurrencyDate1 = new CurCurrencyDate();
        curCurrencyDate1.setId(1L);
        CurCurrencyDate curCurrencyDate2 = new CurCurrencyDate();
        curCurrencyDate2.setId(curCurrencyDate1.getId());
        assertThat(curCurrencyDate1).isEqualTo(curCurrencyDate2);
        curCurrencyDate2.setId(2L);
        assertThat(curCurrencyDate1).isNotEqualTo(curCurrencyDate2);
        curCurrencyDate1.setId(null);
        assertThat(curCurrencyDate1).isNotEqualTo(curCurrencyDate2);
    }
}
