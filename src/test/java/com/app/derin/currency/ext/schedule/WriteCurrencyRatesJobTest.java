package com.app.derin.currency.ext.schedule;

import com.app.derin.currency.ext.mapper.CurCurrencyDateMapperExt;
import com.app.derin.currency.ext.services.ConvertXmlToJsonObjectService;
import com.app.derin.currency.service.CurCurrencyDateService;
import com.app.derin.currency.service.mapper.CurCurrencyDateMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

class WriteCurrencyRatesJobTest {
    @Mock
    CloseableHttpClient httpClient;
    @Mock
    HttpGet request;
    @Mock
    CloseableHttpResponse response;
    @Mock
    HttpEntity entity;
    @Mock
    String responseContent;
    @Mock
    JSONObject jsonObject;
    @Mock
    CurCurrencyDateMapperExt curCurrencyDateMapperExt;
    @Mock
    ConvertXmlToJsonObjectService convertXmlToJsonObjectService;
    @Mock
    JSONArray allRates;
    @Mock
    LocalDate newDate;
    @Mock
    CurCurrencyDateService curCurrencyDateService;

    @Test
    void execute() {
    }

    @Test
    void writeRates() {
        httpClient = HttpClients.createDefault();
        request = new HttpGet("https://www.tcmb.gov.tr/kurlar/today.xml");
        try {
            if(response.getStatusLine().getStatusCode() == 200) {
                assertThat(entity).isEqualTo(response.getEntity());
                assertThat(responseContent).isEqualTo(EntityUtils.toString(entity));
                assertThat(jsonObject).isEqualTo(convertXmlToJsonObjectService.convert(String.valueOf(responseContent)));
                assertThat(allRates).hasSize(((JSONArray) ((JSONObject) jsonObject.get("Tarih_Date")).get("Currency")).length()).
                    isEqualTo((JSONArray) ((JSONObject) jsonObject.get("Tarih_Date")).get("Currency"));
                assertThat(newDate).isEqualTo(curCurrencyDateMapperExt.convertToLocalDate((String) ((JSONObject) jsonObject.get("Tarih_Date")).get("Date")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
