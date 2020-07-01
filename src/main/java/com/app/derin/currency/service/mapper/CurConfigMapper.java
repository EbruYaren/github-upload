package com.app.derin.currency.service.mapper;


import com.app.derin.currency.domain.*;
import com.app.derin.currency.service.dto.CurConfigDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CurConfig} and its DTO {@link CurConfigDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CurConfigMapper extends EntityMapper<CurConfigDTO, CurConfig> {



    default CurConfig fromId(Long id) {
        if (id == null) {
            return null;
        }
        CurConfig curConfig = new CurConfig();
        curConfig.setId(id);
        return curConfig;
    }
}
