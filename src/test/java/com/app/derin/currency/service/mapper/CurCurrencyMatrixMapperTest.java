package com.app.derin.currency.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CurCurrencyMatrixMapperTest {

    private CurCurrencyMatrixMapper curCurrencyMatrixMapper;

    @BeforeEach
    public void setUp() {
        curCurrencyMatrixMapper = new CurCurrencyMatrixMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(curCurrencyMatrixMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(curCurrencyMatrixMapper.fromId(null)).isNull();
    }
}
