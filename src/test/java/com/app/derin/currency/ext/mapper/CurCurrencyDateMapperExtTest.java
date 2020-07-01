package com.app.derin.currency.ext.mapper;

import com.app.derin.currency.domain.CurCurrencyDate;
import com.app.derin.currency.service.dto.CurCurrencyDateDTO;
import com.app.derin.currency.service.impl.CurCurrencyDateServiceImpl;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.shaded.com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

class CurCurrencyDateMapperExtTest {
    private final Logger log = LoggerFactory.getLogger(CurCurrencyDateServiceImpl.class);

    @Mock
    CurCurrencyDateDTO curCurrencyDateDTO;

    @Mock
    @JsonProperty("date")
    public String date;

    @Mock
    JSONArray jsonArray1 = new JSONArray();
    @Mock
    JSONArray jsonArray2 = new JSONArray();
    @Mock
    JSONArray newArray = new JSONArray();

    @Test
    void jsonToDto() {
        LocalDate date = LocalDate.now();
        JSONObject obj1 = new JSONObject();
        obj1.put("CurrencyCode", "USD");
        obj1.put("Kod", "USD");
        obj1.put("BanknoteSelling", 6.1022);
        obj1.put("BanknoteBuying", 6.0125);
        obj1.put("ForexSelling", 6.093);
        obj1.put("ForexBuying", 6.114);
        obj1.put("CrossOrder", 0);
        obj1.put("Unit", 1);
        obj1.put("Isim", "ABD DOLARI");
        obj1.put("CrossRateOther", "");
        obj1.put("CurrencyName","US DOLLAR");
        obj1.put("CrossRateUSD", "");

        curCurrencyDateDTO = new CurCurrencyDateDTO();
        curCurrencyDateDTO.setCurrencyDate(date);
        curCurrencyDateDTO.setBuyingRate(obj1.getDouble("ForexBuying"));
        curCurrencyDateDTO.setSellingRate(obj1.getDouble("ForexSelling"));
        curCurrencyDateDTO.setEffectiveSellingRate(obj1.getDouble("BanknoteSelling"));
        curCurrencyDateDTO.setEffectiveBuyingRate(obj1.getDouble("BanknoteBuying"));
        curCurrencyDateDTO.setIsService(true);
        curCurrencyDateDTO.setSourceCurrencyId(1L);
        curCurrencyDateDTO.setResultCurrencyId(23L);

        try {
            assertThat(curCurrencyDateDTO.getBuyingRate()).isEqualTo(6.114);
            assertThat(curCurrencyDateDTO.getSellingRate()).isEqualTo(6.093);
            assertThat(curCurrencyDateDTO.getCurrencyDate()).isEqualTo(date);
            assertThat(curCurrencyDateDTO.getEffectiveBuyingRate()).isEqualTo(6.0125);
            assertThat(curCurrencyDateDTO.getEffectiveSellingRate()).isEqualTo(6.1022);
            assertThat(curCurrencyDateDTO.getSourceCurrencyId()).isEqualTo(1L);
            assertThat(curCurrencyDateDTO.getResultCurrencyId()).isEqualTo(23L);

        }
        catch(Exception e) {
            System.out.println("Null Pointer Exception!");
        }
    }

    @Test
    void twoJSONArraysMergeMapper() {
        JSONObject obj11 = new JSONObject();
        obj11.put("TP_DK_BGN_A_YTL", 3.456);
        obj11.put("Tarih", "05-03-2020");
        JSONObject obj = new JSONObject();
        obj.put("numberLong", 1583362800);
        obj11.put("UNIXTIME", obj);
        log.debug("{}", obj11);
        jsonArray1.put(obj11);

        JSONObject obj12 = new JSONObject();
        obj12.put("TP_DK_BGN_A_YTL", 3.4462);
        obj12.put("Tarih", "06-03-2020");
        JSONObject objj = new JSONObject();
        objj.put("numberLong", 1583449200);
        obj12.put("UNIXTIME", objj);
        jsonArray1.put(obj12);

        JSONObject obj21 = new JSONObject();
        obj21.put("TP_DK_BGN_S_YTL", 3.4988);
        obj21.put("Tarih", "05-03-2020");
        JSONObject ob = new JSONObject();
        ob.put("numberLong", 1583362800);
        obj21.put("UNIXTIME", ob);
        jsonArray2.put(obj21);

        JSONObject obj22 = new JSONObject();
        obj22.put("TP_DK_BGN_S_YTL", 3.4912);
        obj22.put("Tarih", "06-03-2020");
        JSONObject o = new JSONObject();
        o.put("numberLong", 1583449200);
        obj22.put("UNIXTIME", o);
        jsonArray2.put(obj22);

        for(Object object : jsonArray1) {
            for(Object o1 : jsonArray2) {
                if (((JSONObject) ((JSONObject) object).get("UNIXTIME")).get("numberLong").equals(((JSONObject) ((JSONObject) o1).get("UNIXTIME")).get("numberLong"))) {
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("date", ((JSONObject) object).getString("Tarih"));
                    jsonObject2.put("buyingRate", ((JSONObject) object).get("TP_DK_BGN_A_YTL"));
                    jsonObject2.put("sellingRate", ((JSONObject) o1).get("TP_DK_BGN_S_YTL"));
                    newArray.put(jsonObject2);
                }
            }
        }
        try {
            assertThat(newArray).hasSize(jsonArray1.length());
            assertThat(((JSONObject) newArray.get(0)).get("date")).isEqualTo("05-03-2020");
            assertThat(((JSONObject) newArray.get(0)).get("buyingRate")).isEqualTo(3.456);
            assertThat(((JSONObject) newArray.get(0)).get("sellingRate")).isEqualTo(3.4988);
            assertThat(((JSONObject) newArray.get(1)).get("date")).isEqualTo("06-03-2020");
            assertThat(((JSONObject) newArray.get(1)).get("buyingRate")).isEqualTo(3.4462);
            assertThat(((JSONObject) newArray.get(1)).get("sellingRate")).isEqualTo(3.4912);
        }
        catch(Exception e) {
            System.out.println("Null Pointer Exception!");
        }
    }
}
