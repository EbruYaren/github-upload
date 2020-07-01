package com.app.derin.currency.ext.errors;

import com.app.derin.currency.ext.derinuaa.UaaDictionaryDTO;
import com.app.derin.currency.ext.derinuaa.UaaMessagesDTO;
import com.app.derin.currency.ext.derinuaa.UaaServiceClient;
import com.app.derin.currency.ext.dto.ResultDTO;
import com.app.derin.currency.ext.dto.TranslationDTO;
import com.app.derin.currency.ext.dto.TranslationReturnDTO;
import com.app.derin.currency.security.SecurityUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MessageTranslator {
    private final Logger log = LoggerFactory.getLogger(MessageTranslator.class);

    private final UaaServiceClient uaaUsersBusServiceClient;

    public MessageTranslator(UaaServiceClient uaaUsersBusServiceClient) {
        this.uaaUsersBusServiceClient = uaaUsersBusServiceClient;
    }

    /**
     * {@code getMessage} GET the message from UaaMessagess
     * With fields and entityName from UaaDictionary
     * @param entity the entity of the message
     * @param map the map of that contains field as key and code as value
     * @return the {@link String} with status {@code 204 (NO_CONTENT)}.
     */
    public String getMessage(String entity, Map<String, String> map) {
        //Gelen map'in keySet'i fields'a, values'u codes'a ekleniyor.
        String[] fields =  map.keySet().toArray(new String[map.size()]);
        String[] codes = map.values().toArray(new String[map.size()]);

        //Fields ve codes'un Dictionary ve Messages'taki translation'larının TranslationReturnDTO tipinde getirilmesi
        TranslationReturnDTO translationReturnDTO = getTranslationReturnDTO(fields, codes);
        //translationReturnDTO'nun içerisinden fields'ların translation'larını içeren List<UaaDictionaryDTO> verilerinin alınması
        List<UaaDictionaryDTO> dictionaries = translationReturnDTO.getUaaDictionaryDTOList();
        //translationReturnDTO'nun içerisinden codes'ların translation'larını içeren List<UaaMessagesDTO> verilerinin alınması
        List<UaaMessagesDTO> messages = translationReturnDTO.getUaaMessagesDTOS();

        //Gelen kod için tanımlı mesaj kaydı bulunmaması durumunda dönecek olan mesaj:
        if(messages.size() == 0) {
            //Mesaj kodu getiriliyor
            Map.Entry<String,String> entry = map.entrySet().iterator().next();
            String code = entry.getValue();
            return "İlgili dil için "+code+" ile ilgili bir tanım bulunamamıştır";
        }


        //entityName'in Dictionary'den karşılığının (translation) getirilmesi
        for(UaaDictionaryDTO dic : dictionaries) {
            if(dic.getCode().trim().equals(entity.trim())) {
                entity = dic.getTranslation();
            }
        }

        String fieldName = "";
        StringBuilder newMessage = new StringBuilder();
        //Map'ten gelen ${fieldName}'in dictionaries listesine göre translation'unun getirilmesi
        for(Map.Entry<String, String> entry : map.entrySet()) {
            if (!(entry.getValue().trim().equals(entity))) {
                //DictionaryList'te dönülerek fieldName'in translation'unun getirilmesi
                for (UaaDictionaryDTO dictionaryDTO : dictionaries) {
                    if (entry.getKey().trim().equals(dictionaryDTO.getCode().trim())) {
                        fieldName = dictionaryDTO.getTranslation();
                    }
                }
                //Map'ten gelen errorCode'a göre Mesajlar'dan koda ait mesajın getirilmesi ve ${fieldName} ile ${entityName} alanlarının set edilmesi
                for (UaaMessagesDTO message : messages) {
                    String errorMessage = message.getTranslation();
                    if (entry.getValue().trim().equals(message.getCode().trim())) {
                        if(entry.getKey().trim() != entity) {
                            //Mesaj içerisindeki ${entityName}'nun entity olarak güncellenmesi
                            String s = errorMessage.replace("${entityName}", entity);
                            //Mesaj içerisindeki ${fieldName}'in fieldName olarak güncellenmesi
                            newMessage.append(s.replace("${fieldName}", fieldName));
                            newMessage.append(System.getProperty("line.separator"));
                        }
                    }
                }
            }
        }
        return newMessage.toString();
    }
    public  String getConstraintViolationMessage(String field, String value, String code) {
        //Exception handler'den gelen field ve code String[] olarak tanımlı değişkenlere atılıyor
        String[] fields = new String[]{field};
        String[] codes = new String[]{code};

        //Oluşan [fields] ve [codes] getTranslationReturnDTO methoduna gönderiliyor
        // TranslationReturnDTO tipindeki veri dönüyor
        TranslationReturnDTO translationReturnDTO = getTranslationReturnDTO(fields, codes);
        //translationReturnDTO'nun içerisinden fields'ların translation'larını içeren List<UaaDictionaryDTO> verilerinin alınması
        List<UaaDictionaryDTO> dictionaries = translationReturnDTO.getUaaDictionaryDTOList();
        //translationReturnDTO'nun içerisinden codes'ların translation'larını içeren List<UaaMessagesDTO> verilerinin alınması
        List<UaaMessagesDTO> messages = translationReturnDTO.getUaaMessagesDTOS();

        //Hata kodu için tanımlı mesaj verisi olmaması durumunda
        if(messages.size() == 0) {
            return "İlgili dil için "+code+" ile ilgili bir tanım bulunamamıştır";
        }
        //Field için tanımlı dictionary verisi olmaması durumunda
        if(dictionaries.size() == 0) {
            return "İlgili dil için "+code+" ile ilgili bir tanım bulunamamıştır";
        }

        String fieldName = "";
        //Dictionary'de kayıtlı veri varsa field'ın translation'u getiriliyor.
        if(dictionaries.size() > 0) {
            fieldName = dictionaries.get(0).getTranslation();
        }

        //Hata kodunun bulunduğu mesaj verisinden mesajın translation'unun getirilmesi
        String message = messages.get(0).getTranslation();
        //Mesaj içerisindeki ${fieldName}'in fieldName olarak güncellenmesi
        message = message.replace("${fieldName}", fieldName);
        //Mesaj içerisindeki ${value}'nun value olarak güncellenmesi
        message = message.replace("${value}", value);

        return message;

    }
    //Feign ile uaa'e bağlanarak TranslationReturnDTO'nun getirilmesi
    private TranslationReturnDTO getTranslationReturnDTO(String[] fields, String[] codes){
        //Aktif kullanıcı'nın userName'i:
        String activeUserName = SecurityUtils.getCurrentUserLogin().get();
        //Aktif kullanıcını userName'i ile birlikte fields ve codes ile yeni bir TranslationDTO oluşması
        TranslationDTO translationDTO = new TranslationDTO(fields, codes, activeUserName);
        //Feign ile Uaa'e bağlanılarak fields ve codes'un aktif kullanıcının dil seçimine göre translation'larının getirilmesi
        //Gelen ResponseEntity tipindeki verinin body'si alınarak ResultDTO oluşuyor
        ResultDTO resultDTO = uaaUsersBusServiceClient.getTranslation(translationDTO).getBody();
        //Yeni bir ObjectMapper oluşuyor
        ObjectMapper objectMapper = new ObjectMapper();
        //objectMapper ile oluşan resultDTO'nun data'sı alınarak TranslationReturnDTO tipindeki veri oluşuyor
        TranslationReturnDTO translationReturnDTO = objectMapper.convertValue(resultDTO.data, TranslationReturnDTO.class);

        return translationReturnDTO;
    }
    /**
     * {@code getResponseEntity} GET the ResponseEntity<ResultDTO> of message.
     *
     * @param key the key of the message
     * @param value the value of the message
     * @param entity the entity of the message
     * @param success the success of the ResultDTO
     * @param data the data of the ResultDTO
     * @return the {@link ResponseEntity < ResultDTO >} with status {@code 204 (NO_CONTENT)}.
     */
    public ResponseEntity<ResultDTO> getResponseEntity(String key, String value, String entity, Boolean success, Object data) {
        ResultDTO resultDTO = getResult(key, value,entity,success,data);
        return ResponseEntity.ok().body(resultDTO);
    }
    /**
     * {@code getResult} GET the ResultDTO of message.
     *
     * @param key the key of the message
     * @param value the value of the message
     * @param entity the entity of the message
     * @param success the success of the ResultDTO
     * @param data the data of the ResultDTO
     * @return the {ResultDTO}
     */
    public ResultDTO getResult(String key, String value, String entity, Boolean success, Object data) {
        //Key ve entity'nin istenilen case'e dönüştürülmesi
        key = key
            .replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2")
            .replaceAll("([a-z])([A-Z])", "$1_$2")
            .toLowerCase();
        entity = entity
            .replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2")
            .replaceAll("([a-z])([A-Z])", "$1_$2")
            .toLowerCase();
        //getMessage'a gidecek olan map oluşturuluyor
        Map<String, String> map = new HashMap<String, String>();
        map.put(key, value);
        map.put(entity, entity);
        //getMessage'da oluşan mesaj
        String message = getMessage(entity, map);
        ResultDTO resultDTO = new ResultDTO(success, data, message);
        return resultDTO;
    }
}
