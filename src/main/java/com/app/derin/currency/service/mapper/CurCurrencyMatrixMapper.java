package com.app.derin.currency.service.mapper;


import com.app.derin.currency.domain.*;
import com.app.derin.currency.service.dto.CurCurrencyMatrixDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CurCurrencyMatrix} and its DTO {@link CurCurrencyMatrixDTO}.
 */
@Mapper(componentModel = "spring", uses = {CurCurrenciesMapper.class})
public interface CurCurrencyMatrixMapper extends EntityMapper<CurCurrencyMatrixDTO, CurCurrencyMatrix> {

    @Mapping(source = "sourceCurrency.id", target = "sourceCurrencyId")
    @Mapping(source = "resultCurrency.id", target = "resultCurrencyId")
    CurCurrencyMatrixDTO toDto(CurCurrencyMatrix curCurrencyMatrix);

    @Mapping(source = "sourceCurrencyId", target = "sourceCurrency")
    @Mapping(source = "resultCurrencyId", target = "resultCurrency")
    CurCurrencyMatrix toEntity(CurCurrencyMatrixDTO curCurrencyMatrixDTO);

    default CurCurrencyMatrix fromId(Long id) {
        if (id == null) {
            return null;
        }
        CurCurrencyMatrix curCurrencyMatrix = new CurCurrencyMatrix();
        curCurrencyMatrix.setId(id);
        return curCurrencyMatrix;
    }
}
