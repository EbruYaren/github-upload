//package com.app.derin.currency.ext.rates;
//
//import com.app.derin.currency.domain.CurCurrencies;
//import com.app.derin.currency.domain.CurCurrencyDate;
//import com.app.derin.currency.ext.curCurrencyDate.CurCurrencyDateBusRepository;
//import com.app.derin.currency.ext.mapper.CurCurrenciesBusMapper;
//import com.app.derin.currency.ext.mapper.CurCurrencyDateBusMapper;
//import com.app.derin.currency.repository.CurCurrenciesRepository;
//import com.app.derin.currency.service.CurCurrenciesService;
//import com.app.derin.currency.service.dto.CurCurrenciesDTO;
//import com.app.derin.currency.service.dto.CurCurrencyDateDTO;
//import com.app.derin.currency.service.mapper.CurCurrenciesMapper;
//import com.app.derin.currency.service.mapper.CurCurrencyDateMapper;
//import com.app.derin.currency.web.rest.DerincurrencyKafkaResource;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.json.XML;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.ProtocolException;
//import java.net.URL;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@Component
//@RestController
//@RequestMapping("/api")
//public class TcmbRates {
//    private final Logger log = LoggerFactory.getLogger(DerincurrencyKafkaResource.class);
//    private final CurCurrenciesRepository curCurrenciesBusRepository;
//
//    private final CurCurrencyDateMapper curCurrencyDateMapper;
//    private final CurCurrenciesMapper curCurrenciesMapper;
//    private final CurCurrencyDateBusRepository curCurrencyDateBusRepository;
//    private final CurCurrencyDateBusMapper curCurrencyDateBusMapper;
//    private final CurCurrenciesBusMapper curCurrenciesBusMapper;
//
//    private static SimpleDateFormat inSDF = new SimpleDateFormat("mm/dd/yyyy");
//    private static SimpleDateFormat outSDF = new SimpleDateFormat("yyyy-mm-dd");
//
//
//    private static HttpURLConnection connection;
//    @Value("${jhipster.clientApp.name}")
//    private String applicationName;
//    private static final String ENTITY_NAME = "derincurrencyCurCurrencies";
//
//    ArrayList<CurCurrenciesDTO> curCurrenciesDTOArrayList = new ArrayList<>();
//
//    public TcmbRates(CurCurrenciesBusMapper curCurrenciesBusMapper, CurCurrenciesMapper curCurrenciesMapper, CurCurrenciesService curCurrenciesService, CurCurrenciesRepository curCurrenciesRepository, CurCurrenciesRepository curCurrenciesBusRepository, CurCurrencyDateMapper curCurrencyDateMapper, CurCurrenciesMapper curCurrenciesMapper1, CurCurrencyDateBusRepository curCurrencyDateBusRepository, CurCurrencyDateBusMapper curCurrencyDateBusMapper, CurCurrenciesBusMapper curCurrenciesBusMapper1) {
//        this.curCurrenciesBusRepository = curCurrenciesBusRepository;
//        this.curCurrencyDateMapper = curCurrencyDateMapper;
//        this.curCurrenciesMapper = curCurrenciesMapper1;
//        this.curCurrencyDateBusRepository = curCurrencyDateBusRepository;
//        this.curCurrencyDateBusMapper = curCurrencyDateBusMapper;
//        this.curCurrenciesBusMapper = curCurrenciesBusMapper1;
//    }
//
//
//    @GetMapping("/ext/rates")
//    public ArrayList<CurCurrenciesDTO> XmlToJsonObject () {
//        BufferedReader reader;
//        String line;
//        StringBuffer responseContent = new StringBuffer();
//        String content = "";
//        JSONObject jsonData = new JSONObject();
//        ArrayList<CurCurrenciesDTO> curCurrenciesDTOArrayList = new ArrayList<>();
//        ArrayList<CurCurrencyDateDTO> curCurrencyDateDTOArrayList = new ArrayList<>();
//
//        try {
//            URL url = new URL("https://www.tcmb.gov.tr/kurlar/today.xml");
//            connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//            connection.setConnectTimeout(5000);
//            connection.setReadTimeout(5000);
//
//            int status = connection.getResponseCode();
//            if (connection.getResponseCode() != 200) {
//                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
//                while((line = reader.readLine()) != null) {
//                    responseContent.append(line);
//                }
//                reader.close();
//            }
//            else {
//                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                while ((line = reader.readLine()) != null) {
//                    responseContent.append(line);
//                }
//                jsonData = XML.toJSONObject(String.valueOf(responseContent));
//                JSONArray allRates = (JSONArray) ((JSONObject) jsonData.get("Tarih_Date")).get("Currency");
//
//                String inDate = (String) ((JSONObject) jsonData.get("Tarih_Date")).get("Date");
//                Date dtDob = new Date(inDate);
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                LocalDate currencyDate = LocalDate.parse(sdf.format(dtDob));
//
//                curCurrencyDateDTOArrayList = (ArrayList<CurCurrencyDateDTO>) curCurrencyDateBusMapper.JsonArrayToDtoList(allRates, currencyDate);
//                curCurrenciesDTOArrayList = (ArrayList<CurCurrenciesDTO>) curCurrenciesBusMapper.JsonArrayToDtoList(allRates);
//                List<CurCurrencies> curCurrencies = curCurrenciesBusRepository.findAll();
//                    for (int i = 0; i < curCurrencyDateDTOArrayList.size(); i++) {
//                        for (int j = 0; j < curCurrencies.size() ; j++) {
//                            if(curCurrenciesDTOArrayList.get(i).getCurrencyCode().trim() == curCurrencies.get(j).getCurrencyCode().trim())
//                                insertToCurCurrencyDate(curCurrencyDateDTOArrayList.get(i));
//                            break;
//                           }
//                    }
//                reader.close();
//            }
//            return curCurrenciesDTOArrayList;
//        }
//
//        catch (MalformedURLException e){
//            e.printStackTrace();
//        }
//        catch (ProtocolException e){
//            e.printStackTrace();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//        return curCurrenciesDTOArrayList;
//    }
//    public void insertToCurCurrencyDate(CurCurrencyDateDTO curCurrencyDateDTO) {
//         CurCurrencyDate curCurrencyDate = curCurrencyDateMapper.toEntity(curCurrencyDateDTO);
//         curCurrencyDateBusRepository.save(curCurrencyDate);
//    }
//    public void insertToCurCurrencies(CurCurrenciesDTO curCurrenciesDTO) {
//        CurCurrencies curCurrencies = curCurrenciesMapper.toEntity(curCurrenciesDTO);
//        curCurrenciesBusRepository.save(curCurrencies);
//
//    }
//
//}
