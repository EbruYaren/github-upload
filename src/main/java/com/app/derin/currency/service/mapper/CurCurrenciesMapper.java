package com.app.derin.currency.service.mapper;


import com.app.derin.currency.domain.*;
import com.app.derin.currency.service.dto.CurCurrenciesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CurCurrencies} and its DTO {@link CurCurrenciesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CurCurrenciesMapper extends EntityMapper<CurCurrenciesDTO, CurCurrencies> {



    default CurCurrencies fromId(Long id) {
        if (id == null) {
            return null;
        }
        CurCurrencies curCurrencies = new CurCurrencies();
        curCurrencies.setId(id);
        return curCurrencies;
    }
}
