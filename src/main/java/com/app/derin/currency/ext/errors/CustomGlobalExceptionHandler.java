package com.app.derin.currency.ext.errors;

import com.app.derin.currency.ext.derinuaa.UaaDictionaryDTO;
import com.app.derin.currency.ext.derinuaa.UaaMessagesDTO;
import com.app.derin.currency.ext.derinuaa.UaaServiceClient;
import com.app.derin.currency.ext.dto.ResultDTO;
import com.app.derin.currency.security.SecurityUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

@ControllerAdvice(annotations = RestController.class)
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageTranslator errorTranslator;

    public CustomGlobalExceptionHandler(MessageTranslator errorTranslator) {
        this.errorTranslator = errorTranslator;
    }

    //ResponseEntityExceptionHandler'ın "handleMethodArgumentNotValid" method'unun override edilmesi ve gelen hatanın MessageTranslator'a gönderilmesi
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String entityName= new String();
        ResultDTO resultDTO = new ResultDTO(false, null, "");
        //ErrorTranslatorExt'e gönderilecek map oluşturuluyor
        HashMap<String, String> map = new HashMap<String, String>();
        //Hatanın objectName'ine göre StaticEntityMapper.entityMap'te tanımlı entity'nin değeri getiriliyor
        entityName = StaticEntityMapper.entityMap.get(ex.getBindingResult().getObjectName().trim());
        //Hatanın field hatası olup olmadığı kontrol ediliyor
        if (ex.getBindingResult().hasFieldErrors()) {
            //Hatanın object name'in StaticEntityMapper'daki keySet'lerde olup olmadığı kontrol ediliyor
            if(StaticEntityMapper.entityMap.keySet().contains(ex.getBindingResult().getObjectName().trim())) {
                //Var ise map'e ekleniyor
                map.put(entityName, entityName);
            }
            for(int i=0; i<ex.getBindingResult().getFieldErrors().size(); i++) {
                //FieldName alanı istenilen case tipine dönüştürülüyor.
                String fieldName = ex.getBindingResult()
                    .getFieldErrors()
                    .get(i)
                    .getField()
                    .replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2")
                    .replaceAll("([a-z])([A-Z])", "$1_$2")
                    .toLowerCase();
                //Hata kodu getiriliyor
                String errorCode = ex.getBindingResult().getFieldErrors().get(i).getCode();
                //Dictionary'de aranacak code alanı entityName.fieldName olacak şekilde fieldName set ediliyor. (uaa_users.user_name gibi..)
                fieldName = entityName + "_" + fieldName;
                //Oluşan yeni fieldName ve hata kodu map'e ekleniyor
                map.put(fieldName, errorCode);
            }
            //ErrorTranslator class'ından mesajın getirilmesi
            String messages = errorTranslator.getMessage(entityName, map);
            resultDTO.setMessage(messages);
        }

        return ResponseEntity.ok().body(resultDTO);

    }
}
