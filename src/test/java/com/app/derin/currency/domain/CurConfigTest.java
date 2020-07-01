package com.app.derin.currency.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.app.derin.currency.web.rest.TestUtil;

public class CurConfigTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CurConfig.class);
        CurConfig curConfig1 = new CurConfig();
        curConfig1.setId(1L);
        CurConfig curConfig2 = new CurConfig();
        curConfig2.setId(curConfig1.getId());
        assertThat(curConfig1).isEqualTo(curConfig2);
        curConfig2.setId(2L);
        assertThat(curConfig1).isNotEqualTo(curConfig2);
        curConfig1.setId(null);
        assertThat(curConfig1).isNotEqualTo(curConfig2);
    }
}
