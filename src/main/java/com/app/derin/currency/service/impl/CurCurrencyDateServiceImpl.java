package com.app.derin.currency.service.impl;

import com.app.derin.currency.domain.CurCurrencies;
import com.app.derin.currency.ext.derinfw.McfClientCompanyBusServiceClient;
import com.app.derin.currency.ext.derinfw.dto.McfClientCompaniesDTO;
import com.app.derin.currency.ext.dto.ResultDTO;
import com.app.derin.currency.ext.mapper.CurCurrencyDateMapperExt;
import com.app.derin.currency.ext.services.ConvertXmlToJsonObjectService;
import com.app.derin.currency.repository.CurCurrenciesRepository;
import com.app.derin.currency.service.CurCurrencyDateService;
import com.app.derin.currency.domain.CurCurrencyDate;
import com.app.derin.currency.repository.CurCurrencyDateRepository;
import com.app.derin.currency.service.CurCurrencyMatrixService;
import com.app.derin.currency.service.dto.CurCurrencyDateDTO;
import com.app.derin.currency.service.dto.CurCurrencyMatrixDTO;
import com.app.derin.currency.service.mapper.CurCurrencyDateMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link CurCurrencyDate}.
 */
@Service
@Transactional
@Component
public class CurCurrencyDateServiceImpl implements CurCurrencyDateService {

    private final Logger log = LoggerFactory.getLogger(CurCurrencyDateServiceImpl.class);

    private final CurCurrencyDateRepository curCurrencyDateRepository;

    private final CurCurrencyDateMapper curCurrencyDateMapper;

    private final CurCurrenciesRepository curCurrenciesRepository;

    private final CurCurrencyDateMapperExt curCurrencyDateBusMapper;

    private final CurCurrencyMatrixService curCurrencyMatrixService;

    private final ConvertXmlToJsonObjectService convertXmlToJsonObjectService;

    private final McfClientCompanyBusServiceClient mcfClientCompanyBusServiceClient;

    public CurCurrencyDateServiceImpl(CurCurrencyDateRepository curCurrencyDateRepository, CurCurrencyDateMapper curCurrencyDateMapper, CurCurrenciesRepository curCurrenciesRepository, CurCurrencyDateMapperExt curCurrencyDateBusMapper, CurCurrencyMatrixService curCurrencyMatrixService, ConvertXmlToJsonObjectService convertXmlToJsonObjectService, McfClientCompanyBusServiceClient mcfClientCompanyBusServiceClient) {
        this.curCurrencyDateRepository = curCurrencyDateRepository;
        this.curCurrencyDateMapper = curCurrencyDateMapper;
        this.curCurrenciesRepository = curCurrenciesRepository;
        this.curCurrencyDateBusMapper = curCurrencyDateBusMapper;
        this.curCurrencyMatrixService = curCurrencyMatrixService;
        this.convertXmlToJsonObjectService = convertXmlToJsonObjectService;

        this.mcfClientCompanyBusServiceClient = mcfClientCompanyBusServiceClient;
    }
    /**
     * Cron ile gelen Merkez Bankası verilerinin CurCurrencyDate tablosuna eklenmesi
     *
     * @param jsonArray the jsonArray information.
     * @param date the date information.
     *
     */
    //CRON JOB ile gelen Merkez Bankası verilerinin kur kodlarının CurCurrencies tablosundaki kur kodlarıyla eşleşmesi durumunda CurCurrencyDate'e eklenmesi
    @Override
    public void addCronJobRatesToCurCurrencyDate(JSONArray jsonArray, LocalDate date) {
        //CurCurrencies tablosundaki tanımlı kurlar getiriliyor.
        List<CurCurrencies> curCurrencyList = curCurrenciesRepository.findAll();
        //Feign client'tan firmaya ait currency_id'nin getirilmesi
        ObjectMapper mapper = new ObjectMapper();
        ResultDTO result = mcfClientCompanyBusServiceClient.getMcfClientCompany(6102L).getBody();
        McfClientCompaniesDTO mcfClientCompaniesDTO = mapper.convertValue(result.data, McfClientCompaniesDTO.class);
        Long currencyId =  mcfClientCompaniesDTO.getCurCurrencyId();
        //Merkez bankasından gelen verilerin for döngüsü :
        for (int i = 0; i < jsonArray.length(); i++) {
            //CurCurrencies'te ihtiyacımız olan tanımlı kurların for döngüsü:
            for (int j = 0; j < curCurrencyList.size(); j++) {
                //Merkez bankasıdan gelen kurun CurrencyCode'u:
                String code = ((JSONObject) jsonArray.get(i)).getString("CurrencyCode").trim();
                //CurCurrencies'te tanımlı kurun CurrencyCode'u:
                String curCode = curCurrencyList.get(j).getCurrencyCode();
                //Merkez bankasından gelen kurlardan bizim CurCurrencies tablomuzda olan kurlarla (ihtiyacımız olan) karşılaştırılıyor
                if (code.equals(curCode)) {
                    //Buna göre ihtiyacımız olanları alıyoruz
                    JSONObject obj = (JSONObject) jsonArray.get(i);
                    //CurCurrencyDateBusMapper ile Json objesi CurCurrencyDateDTO'ya dönüştürülüyor.
                    CurCurrencyDateDTO curDateDTO = curCurrencyDateBusMapper.jsonToDto(obj, date, curCurrencyList.get(j).getId(), currencyId);
                    //CurCurrencyDateMapper ile CurCurrencyDTO'nun CurCurrency entity'sine dönüştürülmesi
                    CurCurrencyDate curCurrencyDate = curCurrencyDateMapper.toEntity(curDateDTO);
                    //Oluşan CurCurrencyDate'in save edilmesi
                    curCurrencyDateRepository.save(curCurrencyDate);
                    //CurCurrencyMatrix tablosunun CurCurrencyDateDTO gönderilerek update edilmesi
                    curCurrencyMatrixService.updateTable(curDateDTO);
                    break;
                }
            }
        }
    }

    /**
     * Kullanıcı tarafından yeni kur tanımlamasında seçilen tarih itibari ile gelen ALIŞ kur verileri
     * Gelen XML tipindeki veri JSONArray tipinde dönüyor
     * @param currencyCode kullanıcı tarafından tanımlanan kur kodu.
     * @param startDate kullanıcının seçtiği başlangıç tarihi
     * @param startDate kullanıcının seçtiği bitiş tarihi
     *
     */
    //Kullanıcının eklediği kur tipine ve seçtiği tarihe göre Http Request ile ALIŞ verilerinin getirilmesi
    private JSONArray getJsonBuyingRates(String currencyCode, LocalDate startDate, LocalDate endDate) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //Kullanıcının LocalDate olarak eklediği tarih merkez bankası api'sinde yazılacak tipe dönüşüyor. (String dd-MM-yyyy)
        String date1 = startDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        //Api'ye yazılacak bitiş tarih değeri default olarak yine dd-MM-yyyy tipinde current_date yazılıyor.
        String date2 = endDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        //Değişkenler yerine koyularak api adresi çağrılıyor
        HttpGet request = new HttpGet("https://evds2.tcmb.gov.tr/service/evds/series=TP.DK." + currencyCode + ".A.YTL&startDate=" + date1 + "&endDate=" + date2 + "&type=xml&key=Fgj8EIpJwh");
        JSONArray allRates = new JSONArray();
        try {
            CloseableHttpResponse response = httpClient.execute(request);
            //Response'ın başarılı olması durumunda
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String responseContent = EntityUtils.toString(entity);
                JSONObject jsonObject = convertXmlToJsonObjectService.convert(String.valueOf(responseContent));
                Object o = ((JSONObject) jsonObject.get("document")).get("items");
                if(o.getClass().toString().trim().equals("class org.json.JSONArray")){
                    allRates = (JSONArray) o;
                }
                else {
                    allRates.put(o);
                }
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allRates;
    }

    /**
     * Kullanıcı tarafından yeni kur tanımlamasında seçilen tarih itibari ile gelen SATIŞ kur verileri
     * Gelen XML tipindeki veri JSONArray tipinde dönüyor
     * @param currencyCode kullanıcı tarafından tanımlanan kur kodu.
     * @param startDate kullanıcının seçtiği başlangıç tarihi
     * @param startDate kullanıcının seçtiği bitiş tarihi
     *
     */
    //[getJsonBuyingRates]'de yapılan işlemlerin aynısı Satış için yapılıyor.
    public JSONArray getJsonSellingRates(String currencyCode, LocalDate startDate, LocalDate endDate) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String date1 = startDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String date2 = endDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        HttpGet request = new HttpGet("https://evds2.tcmb.gov.tr/service/evds/series=TP.DK." + currencyCode + ".S.YTL&startDate=" + date1 + "&endDate=" + date2 + "&type=xml&key=Fgj8EIpJwh");
        JSONArray allRates = new JSONArray();
        try {
            CloseableHttpResponse response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String responseContent = EntityUtils.toString(entity);
                JSONObject jsonObject = convertXmlToJsonObjectService.convert(String.valueOf(responseContent));
                Object o = ((JSONObject) jsonObject.get("document")).get("items");
                if(o.getClass().toString().trim().equals("class org.json.JSONArray")){
                    allRates = (JSONArray) o;
                }
                else {
                    allRates.put(o);
                }
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allRates;
    }

    /**
     * Kullanıcı tarafından yeni kur tanımlamasında seçilen tarih itibari ile gelen kurların CurCurrencyDate tablosuna eklenmesi
     * Ve CurCurrencyMatrix'e son günün verisinin eklenmesi
     * @param currencyCode kullanıcı tarafından tanımlanan kur kodu.
     * @param startDate kullanıcının seçtiği tarihten itibaren olan tarihler
     * @param endDate kullanıcının seçtiği tarihten itibaren olan tarihler
     */
    //Kullanıcı tarafından başlangıç ve bitiş tarihleri seçilerek yeni bir kur tipi (currencyCode) eklenmesiyle
    //Alış ve satış JsonArray'lerinin tek bir jsonArray'e dönüştürülerek
    //Veri olmaması durumunda önceki gün verisinin getirilmesi
    public void setJsonArrayAndSave(String currencyCode, LocalDate startDate, LocalDate endDate) throws IOException, ParseException {
        //JsonArray1 seçilen iki tarih arası eklenen currencyCode'a bağlı gelen ALIŞ oranları
        JSONArray jsonArray1 = getJsonBuyingRates(currencyCode, startDate, endDate);
        //JsonArray2 seçilen iki tarih arası eklenen currencyCode'a bağlı gelen SATIŞ oranları
        JSONArray jsonArray2 = getJsonSellingRates(currencyCode, startDate, endDate);
        //Seçilen currencyCode'un CurCurrencies tablosundaki id'si getiriliyor ve CurCurrencyDate tablosuna verinin resultCurrencyId'si olarak eklenmesi
        Long resultCurrencyId = curCurrenciesRepository.findByCurrencyCode(currencyCode);

        List<CurCurrencyMatrixDTO> curCurrencyMatrixDTOS = curCurrencyMatrixService.findAll();
        LocalDate lastMatrixDate = curCurrencyMatrixDTOS.get(0).getLastCurrencyDate();

        //Eklenecek alış ve satış oranları alınan oranlara göre değişiklik göstereceğinden değişkenlere atandı.
        Double buyingRate = 0.0;
        Double sellingRate = 0.0;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        //Oluşan alış ve satış JSONArray'lerinin ilk verilerinin boş gelmesi durumunda kullanıcının seçtiği tarihin veri boş gelmeyene dek öncesine gidilerek
        // JsonArray'ler değişir
        while (((JSONObject) jsonArray1.get(0)).get("TP_DK_" + currencyCode + "_A_YTL") == "" || ((JSONObject) jsonArray1.get(0)).get("TP_DK_" + currencyCode + "_A_YTL") == null)
        {
            startDate = startDate.minusDays(1);
            jsonArray1 = getJsonBuyingRates(currencyCode, startDate, endDate);
            jsonArray2 = getJsonSellingRates(currencyCode, startDate, endDate);
        }

        //Alış ve satış JSONArray'leri CurCurrencyDateBusMapper ile tek bir JsonArray'de birleştirilir.
        JSONArray resultJsonArray = curCurrencyDateBusMapper.twoJSONArraysMergeMapper(jsonArray1, jsonArray2, currencyCode);
        for (int i=0; i<resultJsonArray.length(); i++) {
            //Oluşan son JsonArray'deki elemanın rate oranının boş olması durumunda önceki ilk dolu gelen rate oranı boş olana yazılır.
            if(((JSONObject) resultJsonArray.get(i)).get("sellingRate") == null || ((JSONObject) resultJsonArray.get(i)).get("sellingRate") == "" ||
                ((JSONObject) resultJsonArray.get(i)).get("buyingRate") == null || ((JSONObject) resultJsonArray.get(i)).get("buyingRate") == "") {
                while (((JSONObject) resultJsonArray.get(i)).get("sellingRate") != null && ((JSONObject) resultJsonArray.get(i)).get("sellingRate") != "" &&
                    ((JSONObject) resultJsonArray.get(i)).get("buyingRate") != null && ((JSONObject) resultJsonArray.get(i)).get("buyingRate") != "") {
                    //Gelen dizide boş alış ya da satış kuru olması durumunda önceki günlere ait ilk dolu kur ile gösterim
                    sellingRate = (Double) ((JSONObject) resultJsonArray.get(i - 1)).get("sellingRate");
                    buyingRate = (Double) ((JSONObject) resultJsonArray.get(i - 1)).get("buyingRate");
                }
            }
            else {
                sellingRate = (Double) ((JSONObject) resultJsonArray.get(i)).get("sellingRate");
                buyingRate = (Double) ((JSONObject) resultJsonArray.get(i)).get("buyingRate");
            }
            //Alınan sellingRate ve buyingRate'ler ile resultId ve sourceId'ler ile her bir elemanın [insertRatesToCurCurrencyDate] ile CurCurrencyDate'e eklenmesi
            //Son gün verisi ile CurCurrencyMatrix'in update edilmesi

            //CurCurrencyMatrix'teki last_currency_date'e göre
            LocalDate inCurDate = LocalDate.parse((String) ((JSONObject) resultJsonArray.get(i)).get("date"), formatter);
            if(inCurDate.compareTo(lastMatrixDate)<1) {
                if (inCurDate.equals(lastMatrixDate)) {
                    curCurrencyMatrixService.updateTable(insertRatesToCurCurrencyDate((JSONObject) resultJsonArray.get(i), resultCurrencyId, buyingRate, sellingRate));
                }
                else {
                    insertRatesToCurCurrencyDate((JSONObject) resultJsonArray.get(i), resultCurrencyId, buyingRate, sellingRate);
                }
            }
        }
    }

    /**
     * Kullanıcı tarafından yeni kur tanımlamasında seçilen tarih itibari ile gelen SATIŞ kur verileri
     * Gelen XML tipindeki veri JSONArray tipinde dönüyor
     * @param jsonObject kullanıcı tarafından tanımlanan kur kodu.
     * @param resultCurrencyId kullanıcının seçtiği tarihten itibaren olan tarihler
     * @param buyingRate kullanıcının seçtiği tarihten itibaren olan tarihler
     * @param sellingRate kullanıcının seçtiği tarihten itibaren olan tarihler
     *
     */
    //Kullanıcının eklediği döviz tipine göre seçtiği tarihten itibaren gelen verilerin CurCurrencyDate'eklenmesi
    public CurCurrencyDateDTO insertRatesToCurCurrencyDate(JSONObject jsonObject, Long resultCurrencyId, Double buyingRate, Double sellingRate){
        //Firmaya ait tanımlı local curCurrencyId'nin getirilmesi
        ObjectMapper mapper = new ObjectMapper();
        ResultDTO result = mcfClientCompanyBusServiceClient.getMcfClientCompany(6102L).getBody();
        McfClientCompaniesDTO mcfClientCompaniesDTO = mapper.convertValue(result.data, McfClientCompaniesDTO.class);
        Long currencyId =  mcfClientCompaniesDTO.getCurCurrencyId();
        //Her bir verinin "Tarih" alanı getiriliyor ve LocalDate'e dönüştürülüyor
        String date = jsonObject.getString("date");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(date, formatter);
        return save(curCurrencyDateBusMapper.convertToCurCurrencyDateDTO(localDate,buyingRate,sellingRate,resultCurrencyId,currencyId));
    }

    /**
     * Save a curCurrencyDate.
     *
     * @param curCurrencyDateDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CurCurrencyDateDTO save(CurCurrencyDateDTO curCurrencyDateDTO) {
        log.debug("Request to save CurCurrencyDate : {}", curCurrencyDateDTO);
        CurCurrencyDate curCurrencyDate = curCurrencyDateMapper.toEntity(curCurrencyDateDTO);
        curCurrencyDate = curCurrencyDateRepository.save(curCurrencyDate);
        return curCurrencyDateMapper.toDto(curCurrencyDate);
    }

    /**
     * Get all the curCurrencyDates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CurCurrencyDateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CurCurrencyDates");
        return curCurrencyDateRepository.findAll(pageable)
            .map(curCurrencyDateMapper::toDto);
    }

    /**
     * Get one curCurrencyDate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CurCurrencyDateDTO> findOne(Long id) {
        log.debug("Request to get CurCurrencyDate : {}", id);
        return curCurrencyDateRepository.findById(id)
            .map(curCurrencyDateMapper::toDto);
    }

    /**
     * Delete the curCurrencyDate by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CurCurrencyDate : {}", id);
        curCurrencyDateRepository.deleteById(id);
    }

}
