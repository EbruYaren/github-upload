package com.app.derin.currency.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.app.derin.currency.web.rest.TestUtil;

public class CurCurrencyMatrixTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CurCurrencyMatrix.class);
        CurCurrencyMatrix curCurrencyMatrix1 = new CurCurrencyMatrix();
        curCurrencyMatrix1.setId(1L);
        CurCurrencyMatrix curCurrencyMatrix2 = new CurCurrencyMatrix();
        curCurrencyMatrix2.setId(curCurrencyMatrix1.getId());
        assertThat(curCurrencyMatrix1).isEqualTo(curCurrencyMatrix2);
        curCurrencyMatrix2.setId(2L);
        assertThat(curCurrencyMatrix1).isNotEqualTo(curCurrencyMatrix2);
        curCurrencyMatrix1.setId(null);
        assertThat(curCurrencyMatrix1).isNotEqualTo(curCurrencyMatrix2);
    }
}
