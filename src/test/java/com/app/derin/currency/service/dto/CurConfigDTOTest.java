package com.app.derin.currency.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.app.derin.currency.web.rest.TestUtil;

public class CurConfigDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CurConfigDTO.class);
        CurConfigDTO curConfigDTO1 = new CurConfigDTO();
        curConfigDTO1.setId(1L);
        CurConfigDTO curConfigDTO2 = new CurConfigDTO();
        assertThat(curConfigDTO1).isNotEqualTo(curConfigDTO2);
        curConfigDTO2.setId(curConfigDTO1.getId());
        assertThat(curConfigDTO1).isEqualTo(curConfigDTO2);
        curConfigDTO2.setId(2L);
        assertThat(curConfigDTO1).isNotEqualTo(curConfigDTO2);
        curConfigDTO1.setId(null);
        assertThat(curConfigDTO1).isNotEqualTo(curConfigDTO2);
    }
}
