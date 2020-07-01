package com.app.derin.currency.ext.mapper;

import com.app.derin.currency.service.dto.CurCurrenciesDTO;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Mapper
@Component
public class CurCurrenciesMapperExt {
//    public List<CurCurrenciesDTO> JsonArrayToDtoList(JSONArray jsonArray) {
//        ArrayList<CurCurrenciesDTO> curCurrenciesDTOArrayList = new ArrayList<>();
//        if (jsonArray.length() == 0) {
//            return null;
//        }
//        for (int i=0; i<jsonArray.length(); i++) {
//            curCurrenciesDTOArrayList.add(jsonToDto((JSONObject) jsonArray.get(i)));
//        }
//        return curCurrenciesDTOArrayList;
//    }
//
//    public CurCurrenciesDTO jsonToDto(JSONObject jsonObject) {
//        CurCurrenciesDTO curCurrenciesDTO = new CurCurrenciesDTO();
//        curCurrenciesDTO.setCurrencyCode(jsonObject.getString("CurrencyCode"));
//        curCurrenciesDTO.setCurrencyName(jsonObject.getString("Isim"));
//        curCurrenciesDTO.setCurrencySymbol("");
//
//        return curCurrenciesDTO;
//
//    }

}
