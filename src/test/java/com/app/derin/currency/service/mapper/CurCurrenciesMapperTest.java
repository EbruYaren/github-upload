package com.app.derin.currency.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CurCurrenciesMapperTest {

    private CurCurrenciesMapper curCurrenciesMapper;

    @BeforeEach
    public void setUp() {
        curCurrenciesMapper = new CurCurrenciesMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(curCurrenciesMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(curCurrenciesMapper.fromId(null)).isNull();
    }
}
