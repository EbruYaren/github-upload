package com.app.derin.currency.service.mapper;


import com.app.derin.currency.domain.*;
import com.app.derin.currency.service.dto.CurCurrencyDateDTO;

import org.json.JSONObject;
import org.mapstruct.*;

import java.time.LocalDate;

/**
 * Mapper for the entity {@link CurCurrencyDate} and its DTO {@link CurCurrencyDateDTO}.
 */
@Mapper(componentModel = "spring", uses = {CurCurrenciesMapper.class})
public interface CurCurrencyDateMapper extends EntityMapper<CurCurrencyDateDTO, CurCurrencyDate> {

    @Mapping(source = "sourceCurrency.id", target = "sourceCurrencyId")
    @Mapping(source = "resultCurrency.id", target = "resultCurrencyId")
    CurCurrencyDateDTO toDto(CurCurrencyDate curCurrencyDate);

    @Mapping(source = "sourceCurrencyId", target = "sourceCurrency")
    @Mapping(source = "resultCurrencyId", target = "resultCurrency")
    CurCurrencyDate toEntity(CurCurrencyDateDTO curCurrencyDateDTO);

    default CurCurrencyDate fromId(Long id) {
        if (id == null) {
            return null;
        }
        CurCurrencyDate curCurrencyDate = new CurCurrencyDate();
        curCurrencyDate.setId(id);
        return curCurrencyDate;
    }

    }

