package com.app.derin.currency.service.impl;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.XML;
import org.junit.jupiter.api.Test;

import com.app.derin.currency.domain.CurCurrencies;
import com.app.derin.currency.domain.CurCurrencyDate;
import com.app.derin.currency.ext.derinfw.DerinfwBusService;
import com.app.derin.currency.ext.mapper.CurCurrencyDateMapperExt;
import com.app.derin.currency.repository.CurCurrenciesRepository;
import com.app.derin.currency.repository.CurCurrencyDateRepository;
import com.app.derin.currency.service.CurCurrencyDateService;
import com.app.derin.currency.service.CurCurrencyMatrixService;
import com.app.derin.currency.service.dto.CurCurrencyDateDTO;
import com.app.derin.currency.service.mapper.CurCurrencyDateMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurCurrencyDateServiceImplTest {
    private final Logger log = LoggerFactory.getLogger(CurCurrencyDateServiceImplTest.class);

    @Mock
    DerinfwBusService derinfwBusService;
    @Mock
    CurCurrencyDateDTO curCurrencyDateDTO = new CurCurrencyDateDTO();
    @Mock
    CurCurrencyDateDTO curCurrencyDateDTO2 = new CurCurrencyDateDTO();
    @Mock
    CurCurrencyDate curCurrencyDate = new CurCurrencyDate();

    @Mock
    CurCurrencyDateRepository curCurrencyDateRepository;;

    @Mock
    CurCurrencyDateMapper curCurrencyDateMapper;

    @Mock
    CurCurrencyDateMapperExt curCurrencyDateMapperExt;

    @Mock
    Date date;

    @Mock
    JSONArray allRatesJsonArray = new JSONArray();

    @Mock
    CurCurrencyDateService curCurrencyDateService;

    @Mock
    CurCurrenciesRepository curCurrenciesRepository;

    @Mock
    JSONObject jsonObject;

    @Mock
    CloseableHttpClient httpClient;

    @Mock
    HttpGet request;

    @Mock
    CurCurrencyMatrixService curCurrencyMatrixService;

    private List<CurCurrencies> curCurrencyList = new ArrayList<CurCurrencies>();

    private JSONArray allRates = new JSONArray();

    private JSONArray jsonArray1 = new JSONArray();
    private JSONArray jsonArray2 = new JSONArray();
    private JSONArray jsonArray3 = new JSONArray();

    private JSONArray newJsonArray1 = new JSONArray();
    private JSONArray newJsonArray2 = new JSONArray();
    private JSONArray newJsonArray3 = new JSONArray();

    private JSONArray lastJsonArray3 = new JSONArray();


    @Before
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void getLocalCurrencyId() throws Exception{
//        try {
//           // when(mcfClientCompanyBusServiceClient.getMcfClientCompanies(58559L).getStatusCodeValue()).thenReturn(200);
//            // when(mcfClientCompanyBusServiceClient.getMcfClientCompanies(58559L).getBody().getCurCurrencyId()).thenReturn(1205L);
//        }
//        catch (Exception e){
//            System.out.println("Null Pointer Exception!");
//        }
    }

    @Test
    void save() {
    }

    @Test
    void saveRates() throws Exception{
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
        allRates.put(obj1);

        JSONObject obj2 = new JSONObject();
        obj2.put("CurrencyCode", "EUR");
        obj2.put("Kod", "EUR");
        obj2.put("BanknoteSelling", 6.842);
        obj2.put("BanknoteBuying", 6.8125);
        obj2.put("ForexSelling", 6.793);
        obj2.put("ForexBuying", 6.784);
        obj2.put("CrossOrder", 9);
        obj2.put("Unit", 1);
        obj2.put("Isim", "EURO");
        obj2.put("CrossRateOther",1.128);
        obj2.put("CurrencyName","EURO");
        obj2.put("CrossRateUSD", "");
        allRates.put(obj2);

        JSONObject obj3 = new JSONObject();
        obj3.put("CurrencyCode", "EUR");
        obj3.put("Kod", "EUR");
        obj3.put("BanknoteSelling", 6.842);
        obj3.put("BanknoteBuying", 6.8125);
        obj3.put("ForexSelling", 6.793);
        obj3.put("ForexBuying", 6.784);
        obj3.put("CrossOrder", 9);
        obj3.put("Unit", 1);
        obj3.put("Isim", "EURO");
        obj3.put("CrossRateOther",1.128);
        obj3.put("CurrencyName","EURO");
        obj3.put("CrossRateUSD", "");
        allRates.put(obj3);

        CurCurrencies curCurrency = new CurCurrencies();
        curCurrency.setId(1L);
        curCurrency.setCurrencyCode("USD");
        curCurrency.setCurrencyName("ABD DOLARI");
        curCurrency.setCurrencySymbol("$");
        curCurrencyList.add(curCurrency);
        CurCurrencies curCurrency1 = new CurCurrencies();
        curCurrency1.setId(2L);
        curCurrency1.setCurrencyCode("EUR");
        curCurrency1.setCurrencyName("EURO");
        curCurrency1.setCurrencySymbol("€");
        curCurrencyList.add(curCurrency1);

        for(int i=0; i<allRates.length(); i++) {
            for (int j = 0; j < curCurrencyList.size(); j++) {
                String code = ((JSONObject) allRates.get(i)).getString("CurrencyCode").trim();
                String curCode = curCurrencyList.get(j).getCurrencyCode();
                if (code.equals(curCode)) {
                    JSONObject jsonObject = (JSONObject) allRates.get(i);
                    try {
                        when(curCurrencyDateMapperExt.jsonToDto(jsonObject, LocalDate.now(), 1L, 2L)).thenReturn(curCurrencyDateDTO);
                        assertThat(curCurrencyDateDTO).isNotNull().isInstanceOf(CurCurrencyDateDTO.class);
                        when(curCurrencyDateMapper.toEntity(curCurrencyDateDTO)).thenReturn(curCurrencyDate);
                        when(curCurrencyDateRepository.save(curCurrencyDate)).thenReturn(curCurrencyDate);
                        doThrow(NullPointerException.class).when(curCurrencyMatrixService).updateTable(curCurrencyDateDTO);
                    }
                    catch(Exception e) {
                        System.out.println("Null Pointer Exception!");
                    }
                }
            }
        }
    }

    @Test
    void convertToLocalDate() {
        Date date = new Date();
        LocalDate newDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(date));
        try {
            Assert.assertEquals(newDate, LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(date)));
        }
        catch(Exception e) {
            System.out.println("Null Pointer Exception!");
        }
    }

    @Test
    void findAll() {
    }

    @Test
    void findOne() {
    }

    @Test
    void delete() {
    }

    @Test
    void insertToCurCurrencyDate() {
        JSONObject obj1 = new JSONObject();
        obj1.put("TP_DK_BGN_A_YTL", "");
        obj1.put("Tarih", "05-03-2020");
        JSONObject obj = new JSONObject();
        obj.put("numberLong", 1583362800);
        obj1.put("UNIXTIME", obj);
        jsonArray1.put(obj1);

        JSONObject obj12 = new JSONObject();
        obj12.put("TP_DK_BGN_A_YTL", 2.15);
        obj12.put("Tarih", "06-03-2020");
        JSONObject o12 = new JSONObject();
        o12.put("numberLong", 1583362200);
        obj12.put("UNIXTIME", o12);
        jsonArray1.put(obj12);

        JSONObject obj2 = new JSONObject();
        obj2.put("TP_DK_BGN_S_YTL", "");
        obj2.put("Tarih", "05-03-2020");
        JSONObject ob = new JSONObject();
        ob.put("numberLong", 1583362800);
        obj2.put("UNIXTIME", ob);
        jsonArray2.put(obj2);

        JSONObject obj22 = new JSONObject();
        obj22.put("TP_DK_BGN_S_YTL", 0.15);
        obj22.put("Tarih", "06-03-2020");
        JSONObject obb = new JSONObject();
        obb.put("numberLong", 1583362200);
        obj22.put("UNIXTIME", obb);
        jsonArray2.put(obj22);

        JSONObject object = new JSONObject();
        object.put("buyingRate", "");
        object.put("sellingRate", "");
        object.put("date", "05-03-2020");
        jsonArray3.put(object);

        JSONObject object2 = new JSONObject();
        object2.put("buyingRate", 2.15);
        object2.put("sellingRate", 0.15);
        object2.put("date", "06-03-2020");
        jsonArray3.put(object2);

        JSONObject object3 = new JSONObject();
        object3.put("buyingRate", 3.456);
        object3.put("sellingRate", 1.85);
        object3.put("date", "04-03-2020");
        newJsonArray3.put(object3);
        newJsonArray3.put(object);
        newJsonArray3.put(object2);

        JSONObject objNew = new JSONObject();
        objNew.put("buyingRate", 3.456);
        objNew.put("sellingRate", 1.85);
        objNew.put("date", "05-03-2020");

        JSONObject newObject = new JSONObject();
        newObject.put("TP_DK_BGN_A_YTL", 3.456);
        newObject.put("Tarih", "04-03-2020");
        JSONObject obbj = new JSONObject();
        obbj.put("numberLong", 1583362100);
        newObject.put("UNIXTIME", obbj);
        newJsonArray1.put(newObject);
        newJsonArray1.put(obj1);
        newJsonArray1.put(obj12);

        JSONObject newObject2 = new JSONObject();
        newObject2.put("TP_DK_BGN_A_YTL", 1.85);
        newObject2.put("Tarih", "04-03-2020");
        JSONObject oj = new JSONObject();
        oj.put("numberLong", 1583362100);
        newObject2.put("UNIXTIME", oj);
        newJsonArray2.put(newObject2);
        newJsonArray2.put(obj2);
        newJsonArray2.put(obj22);

        String curCode = "USD";
        LocalDate date = LocalDate.now();

        try {
//            when(curCurrencyDateService.getJsonBuyingRates(curCode, date, date)).thenReturn(jsonArray1);
//            when(curCurrencyDateService.getJsonSellingRates(curCode,  date, date)).thenReturn(jsonArray2);
            when(curCurrenciesRepository.findByCurrencyCode(curCode)).thenReturn(1205L);
            when(curCurrencyDateMapperExt.twoJSONArraysMergeMapper(jsonArray1, jsonArray2, curCode)).thenReturn(jsonArray3);

            //İlk eleman kontrolü
            if(((JSONObject) jsonArray1.get(0)).getString("TP_DK_BGN_A_YTL") == null || ((JSONObject) jsonArray1.get(0)).getString("TP_DK_BGN_A_YTL") == "") {
                date = date.minusDays(1);
//                when(curCurrencyDateService.getJsonBuyingRates(curCode,  date, date)).thenReturn(newJsonArray1);
//                when(curCurrencyDateService.getJsonSellingRates(curCode,  date, date)).thenReturn(newJsonArray2);
            }

            when(curCurrencyDateMapperExt.twoJSONArraysMergeMapper(jsonArray1, jsonArray2, curCode)).thenReturn(newJsonArray3);

            if(((JSONObject) newJsonArray3.get(1)).get("buyingRate") == null || ((JSONObject) newJsonArray3.get(1)).get("buyingRate") == "") {
                lastJsonArray3.put(object3);
                lastJsonArray3.put(objNew);
                lastJsonArray3.put(object2);
            }

            for(int i=0; i<lastJsonArray3.length(); i++) {
                assertNotNull(((JSONObject) lastJsonArray3.get(i)).get("date"));
                assertNotNull(((JSONObject) lastJsonArray3.get(i)).get("buyingRate"));
                assertNotNull(((JSONObject) lastJsonArray3.get(i)).get("sellingRate"));
                when(curCurrencyDateService.insertRatesToCurCurrencyDate(((JSONObject) lastJsonArray3.get(i)), 1L, ((JSONObject) lastJsonArray3.get(i)).getDouble("buyingRate"), ((JSONObject) lastJsonArray3.get(i)).getDouble("sellingRate"))).thenReturn(curCurrencyDateDTO);
                if(i == (lastJsonArray3.length()-1)) {
                    doThrow(NullPointerException.class).when(curCurrencyMatrixService).updateTable(curCurrencyDateDTO2);
                }
            }
        } catch (Exception e) {
            System.out.println("Null Pointer Exception");
        }
    }

    @Test
    void insert() throws Exception {
        Long resultId = 1L;
        Double buyingRate = 45.152;
        Double sellingRate = 0.154;

        jsonObject = new JSONObject();
        jsonObject.put("date", "10-03-2020");
        curCurrencyDateDTO = new CurCurrencyDateDTO();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate tarih = LocalDate.parse(jsonObject.getString("date"), formatter);
        curCurrencyDateDTO.setCurrencyDate(LocalDate.parse(jsonObject.getString("date"), formatter));
        curCurrencyDateDTO.setResultCurrencyId(resultId);
        curCurrencyDateDTO.setBuyingRate(buyingRate);
        curCurrencyDateDTO.setSellingRate(sellingRate);
        //when(curCurrencyDateService.getLocalCurrencyId()).thenReturn(45L);
        //curCurrencyDateDTO.setSourceCurrencyId(curCurrencyDateService.getLocalCurrencyId());
        try {
            assertThat(curCurrencyDateDTO.getCurrencyDate()).isEqualTo(LocalDate.parse(jsonObject.getString("date"), formatter));
        }
        catch (Exception e){
            System.out.println("Null Pointer Exception!");
        }
    }

    @Test
    void getJsonBuyingRates() throws IOException {
        httpClient = HttpClients.createDefault();
        String curCode = "USD";
        String date1 = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String date2 = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        try {
            assertThat(date1).isNotEqualTo(date2);
            request = new HttpGet("https://evds2.tcmb.gov.tr/service/evds/series=TP.DK." + curCode + ".A.YTL&startDate=" + date2 + "&endDate=" + date1 + "&type=xml&key=Fgj8EIpJwh");
            CloseableHttpResponse response = httpClient.execute(request);
            assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
            assertThat(response.getEntity()).isInstanceOf(HttpEntity.class);
            HttpEntity entity = response.getEntity();
            String responseContent = EntityUtils.toString(entity);
            assertThat(XML.toJSONObject(String.valueOf(responseContent))).isInstanceOf(JSONObject.class);
            JSONObject jsonObject = XML.toJSONObject(String.valueOf(responseContent));
            assertThat(jsonObject.get("document")).isInstanceOf(JSONObject.class);
            assertThat(((JSONObject) jsonObject.get("document")).get("items")).isInstanceOf(JSONArray.class);
            allRatesJsonArray = (JSONArray) ((JSONObject) jsonObject.get("document")).get("items");
            assertThat(allRatesJsonArray).hasSize(((JSONArray) ((JSONObject) jsonObject.get("document")).get("items")).length());
        }
        catch (Exception e) {
            System.out.println("Null Pointer Exception!");
        }

    }

    @Test
    void getJsonSellingRates() {
        httpClient = HttpClients.createDefault();
        String curCode = "USD";
        String date1 = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String date2 = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        try {
            assertThat(date1).isNotEqualTo(date2);
            request = new HttpGet("https://evds2.tcmb.gov.tr/service/evds/series=TP.DK." + curCode + ".S.YTL&startDate=" + date2 + "&endDate=" + date1 + "&type=xml&key=Fgj8EIpJwh");
            CloseableHttpResponse response = httpClient.execute(request);
            assertThat(response.getStatusLine().getStatusCode()).isEqualTo(200);
            assertThat(response.getEntity()).isInstanceOf(HttpEntity.class);
            HttpEntity entity = response.getEntity();
            String responseContent = EntityUtils.toString(entity);
            assertThat(XML.toJSONObject(String.valueOf(responseContent))).isInstanceOf(JSONObject.class);
            JSONObject jsonObject = XML.toJSONObject(String.valueOf(responseContent));
            assertThat(jsonObject.get("document")).isInstanceOf(JSONObject.class);
            assertThat(((JSONObject) jsonObject.get("document")).get("items")).isInstanceOf(JSONArray.class);
            allRatesJsonArray = (JSONArray) ((JSONObject) jsonObject.get("document")).get("items");
            assertThat(allRatesJsonArray).hasSize(((JSONArray) ((JSONObject) jsonObject.get("document")).get("items")).length());
        }
        catch (Exception e) {
            System.out.println("Null Pointer Exception!");
        }

    }
}
