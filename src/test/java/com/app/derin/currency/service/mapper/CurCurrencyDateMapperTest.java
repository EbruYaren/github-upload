package com.app.derin.currency.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CurCurrencyDateMapperTest {

    private CurCurrencyDateMapper curCurrencyDateMapper;

    @BeforeEach
    public void setUp() {
        curCurrencyDateMapper = new CurCurrencyDateMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(curCurrencyDateMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(curCurrencyDateMapper.fromId(null)).isNull();
    }
}
