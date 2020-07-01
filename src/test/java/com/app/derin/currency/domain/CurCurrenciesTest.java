package com.app.derin.currency.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.app.derin.currency.web.rest.TestUtil;

public class CurCurrenciesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CurCurrencies.class);
        CurCurrencies curCurrencies1 = new CurCurrencies();
        curCurrencies1.setId(1L);
        CurCurrencies curCurrencies2 = new CurCurrencies();
        curCurrencies2.setId(curCurrencies1.getId());
        assertThat(curCurrencies1).isEqualTo(curCurrencies2);
        curCurrencies2.setId(2L);
        assertThat(curCurrencies1).isNotEqualTo(curCurrencies2);
        curCurrencies1.setId(null);
        assertThat(curCurrencies1).isNotEqualTo(curCurrencies2);
    }
}
