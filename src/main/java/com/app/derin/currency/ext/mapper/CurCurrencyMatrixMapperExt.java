package com.app.derin.currency.ext.mapper;

import com.app.derin.currency.domain.CurCurrencyDate;
import com.app.derin.currency.domain.CurCurrencyMatrix;
import com.app.derin.currency.service.dto.CurCurrencyDateDTO;
import com.app.derin.currency.service.dto.CurCurrencyMatrixDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public class CurCurrencyMatrixMapperExt {
    /**
     * Convert CurCurrencyDateDTO to CurCurrencyMatrixDTO.
     *
     * @param curCurrencyDateDTO the entity to convert.
     * @return CurCurrencyMatrixDTO
     */
    public CurCurrencyMatrixDTO dateDtoToMatrixDto(CurCurrencyDateDTO curCurrencyDateDTO)
    {
        CurCurrencyMatrixDTO curCurrencyMatrixDTO = new CurCurrencyMatrixDTO();
        curCurrencyMatrixDTO.setId(curCurrencyDateDTO.getId());
        curCurrencyMatrixDTO.setLastCurrencyDate(curCurrencyDateDTO.getCurrencyDate());
        curCurrencyMatrixDTO.setBuyingRate(curCurrencyDateDTO.getBuyingRate());
        curCurrencyMatrixDTO.setSellingRate(curCurrencyDateDTO.getSellingRate());
        curCurrencyMatrixDTO.setEffectiveBuyingRate(curCurrencyDateDTO.getEffectiveBuyingRate());
        curCurrencyMatrixDTO.setEffectiveSellingRate(curCurrencyDateDTO.getEffectiveSellingRate());
        curCurrencyMatrixDTO.setSourceUnitValue(curCurrencyDateDTO.getSourceUnitValue());
        curCurrencyMatrixDTO.setResultUnitValue(curCurrencyDateDTO.getResultUnitValue());
        curCurrencyMatrixDTO.setSourceCurrencyId(curCurrencyDateDTO.getSourceCurrencyId());
        curCurrencyMatrixDTO.setResultCurrencyId(curCurrencyDateDTO.getResultCurrencyId());

        return curCurrencyMatrixDTO;
    }
    public CurCurrencyMatrixDTO setMatrixDto(CurCurrencyMatrixDTO curMatrix, CurCurrencyDateDTO curDate) {
        curMatrix.setLastCurrencyDate(curDate.getCurrencyDate());
        curMatrix.setBuyingRate(curDate.getBuyingRate());
        curMatrix.setSellingRate(curDate.getSellingRate());
        curMatrix.setEffectiveBuyingRate(curDate.getEffectiveBuyingRate());
        curMatrix.setEffectiveSellingRate(curDate.getEffectiveSellingRate());
        curMatrix.setSourceUnitValue(curDate.getSourceUnitValue());
        curMatrix.setResultUnitValue(curDate.getResultUnitValue());
        curMatrix.setSourceCurrencyId(curDate.getSourceCurrencyId());
        curMatrix.setResultCurrencyId(curDate.getResultCurrencyId());
        return curMatrix;
    }
}
