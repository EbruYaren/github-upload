package com.app.derin.currency.ext.schedule;

import com.app.derin.currency.ext.mapper.CurCurrencyDateMapperExt;
import com.app.derin.currency.ext.services.ConvertXmlToJsonObjectService;
import com.app.derin.currency.service.CurCurrencyDateService;
import com.app.derin.currency.service.impl.CurCurrencyDateServiceImpl;
import com.app.derin.currency.service.mapper.CurCurrencyDateMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.time.LocalDate;

public class WriteCurrencyRatesJob implements Job {
    @Autowired
    CurCurrencyDateService curCurrencyDateService;
    @Autowired
    ConvertXmlToJsonObjectService convertXmlToJsonObjectService;
    @Autowired
    CurCurrencyDateMapperExt curCurrencyDateMapperExt;

    private final Logger log = LoggerFactory.getLogger(CurCurrencyDateServiceImpl.class);


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            writeRates();
        }
        catch (IOException e){

        }
    }

    //CRON JOB ILE HTTPCLIENT OLUŞTURARAK TCMB KURLARININ  GETIRILMESI VE SERVİSLER İLE DATABASE'E EKLENMESİ
    public void writeRates() throws IOException {
        log.debug("Job started");
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet("https://www.tcmb.gov.tr/kurlar/today.xml");
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            int status = response.getStatusLine().getStatusCode();
            if(status == 200) {
                HttpEntity entity = response.getEntity();
                String responseContent = EntityUtils.toString(entity);
                //Merkez bankasından gelen Xml verisinin JsonObject'e dönüştürülmesi
                JSONObject jsonObject = convertXmlToJsonObjectService.convert(String.valueOf(responseContent));
                //JsonObject'in içinde yer alan kurlar listesinin alınıp JsonArray'e dönüşmesi
                JSONArray allRates = (JSONArray) ((JSONObject) jsonObject.get("Tarih_Date")).get("Currency");
                //[dd/MM/yyyy] tipinde gelen tarihin LocalDate'e dönüştürülmesi
                LocalDate newDate = curCurrencyDateMapperExt.convertToLocalDate((String) ((JSONObject) jsonObject.get("Tarih_Date")).get("Date"));
                //JsonArray'e dönüşen XML verisinin CurCurrencyDate'e eklenmesi
                curCurrencyDateService.addCronJobRatesToCurCurrencyDate(allRates,newDate);
            }

        }
    }
}
