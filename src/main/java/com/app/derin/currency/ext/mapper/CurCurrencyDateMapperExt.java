package com.app.derin.currency.ext.mapper;

import com.app.derin.currency.service.dto.CurCurrencyDateDTO;
import com.app.derin.currency.service.impl.CurCurrencyDateServiceImpl;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mapstruct.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Mapper
@Component
public class CurCurrencyDateMapperExt {
    private final Logger log = LoggerFactory.getLogger(CurCurrencyDateMapperExt.class);
    //JSON tipindeki merkez bankası verilerinin DTO'ya dönüştürülmesi
    public CurCurrencyDateDTO jsonToDto(JSONObject jsonObject, LocalDate date, Long resultCurrencyId, Long localCurrencyId) {
        CurCurrencyDateDTO curCurrencyDateDTO = new CurCurrencyDateDTO();
        curCurrencyDateDTO.setCurrencyDate(date);
        curCurrencyDateDTO.setBuyingRate((jsonObject.get("ForexBuying") == null || jsonObject.get("ForexBuying") == "") ? 0 : jsonObject.getDouble("ForexBuying"));
        curCurrencyDateDTO.setSellingRate((jsonObject.get("ForexSelling") == null || jsonObject.get("ForexSelling") == "") ? 0 : jsonObject.getDouble("ForexSelling"));
        curCurrencyDateDTO.setIsService(true);
        curCurrencyDateDTO.setEffectiveBuyingRate((jsonObject.get("BanknoteBuying") == null || jsonObject.get("BanknoteBuying") == "") ? 0 : jsonObject.getDouble("BanknoteBuying"));
        curCurrencyDateDTO.setEffectiveSellingRate((jsonObject.get("BanknoteSelling") == null || jsonObject.get("BanknoteSelling") == "") ? 0 : jsonObject.getDouble("BanknoteSelling"));
        curCurrencyDateDTO.setResultUnitValue((jsonObject.get("Unit") == null) ? 0 : (int) jsonObject.getLong("Unit"));
        curCurrencyDateDTO.setSourceUnitValue((jsonObject.get("Unit") == null) ? 0 : (int) jsonObject.getLong("Unit"));
        //SourceId yapılacak
        curCurrencyDateDTO.setSourceCurrencyId(localCurrencyId);
        curCurrencyDateDTO.setResultCurrencyId(resultCurrencyId);

        return curCurrencyDateDTO;

    }

    //Kullanıcı tarafından eklenen kur tipine göre alış ve satış JSONArray'lerinin tek bir JsonArray'de birleşmesi
    public JSONArray twoJSONArraysMergeMapper (JSONArray jsonArray, JSONArray jsonArray1, String currencyCode) {
        log.debug("{}", jsonArray.get(0));
        log.debug("{}", jsonArray1.get(0));
        JSONArray newJSONArray = new JSONArray();
        for(Object jsonObject : jsonArray) {
            for(Object jsonObject1 : jsonArray1) {
                if(((JSONObject)((JSONObject) jsonObject).get("UNIXTIME")).get("numberLong").equals(((JSONObject)((JSONObject) jsonObject1).get("UNIXTIME")).get("numberLong"))) {
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("date", ((JSONObject) jsonObject).getString("Tarih"));
                    jsonObject2.put("buyingRate", ((JSONObject) jsonObject).get("TP_DK_"+currencyCode+"_A_YTL"));
                    jsonObject2.put("sellingRate", ((JSONObject) jsonObject1).get("TP_DK_"+currencyCode+"_S_YTL"));
                    newJSONArray.put(jsonObject2);
                }
            }
        }
        return newJSONArray;
    }

    /**
     * String'i LocalDate'e dönüştüren mapper
     * @param s string tipindeki date
     *
     */
    //[dd/MM/yyyy] tipindeki tarihin LocalDate'e (yyyy-MM-dd) dönüştürülmesi
    public LocalDate convertToLocalDate(String s) {
        Date date = new Date(s);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        LocalDate newDate = LocalDate.parse(sdf.format(date));
        return newDate;
    }

    /**
     * Kullanıcı tarafından yapılan eklemelerde oluşan parametrelerle CurCurrencyDateDTO'ya dönüştürme
     * @param date LocalDate
     * @param buyingRate
     * @param sellingRate
     * @param resultCurrencyId result currency id
     * @param sourceCurrencyId source currency id
     */
    //CurCurrencyDateDTO'nun serviste oluşan gerekli değişkenlerle save edilmesi
    public CurCurrencyDateDTO convertToCurCurrencyDateDTO(LocalDate date, Double buyingRate, Double sellingRate, Long resultCurrencyId, Long sourceCurrencyId) {
        CurCurrencyDateDTO curCurrencyDateDTO = new CurCurrencyDateDTO();
        curCurrencyDateDTO.setBuyingRate(buyingRate);
        curCurrencyDateDTO.setSellingRate(sellingRate);
        curCurrencyDateDTO.setIsService(true);
        curCurrencyDateDTO.setCurrencyDate(date);
        curCurrencyDateDTO.setSourceCurrencyId(sourceCurrencyId);
        curCurrencyDateDTO.setResultCurrencyId(resultCurrencyId);
        return curCurrencyDateDTO;
    }


}
