package com.app.derin.currency.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CurConfigMapperTest {

    private CurConfigMapper curConfigMapper;

    @BeforeEach
    public void setUp() {
        curConfigMapper = new CurConfigMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(curConfigMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(curConfigMapper.fromId(null)).isNull();
    }
}
