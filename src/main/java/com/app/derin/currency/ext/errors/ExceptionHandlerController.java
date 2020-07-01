package com.app.derin.currency.ext.errors;

import com.app.derin.currency.ext.dto.ResultDTO;
import org.apache.commons.lang.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerController {

    private final MessageTranslator messageTranslator;

    public ExceptionHandlerController(MessageTranslator messageTranslator) {
        this.messageTranslator = messageTranslator;
    }

    //CustomException'dan gelen hata kodunun Messages'tan kullanıcının dil seçimine göre getirilmesi
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ResultDTO> handleCustomException(CustomException ex) {
        //Success'i false, data'sı null ve mesajı Messages'tan gelen tranlation olan ResultDTO
        ResultDTO resultDTO = messageTranslator.getResult("uaa_users_id", ex.getErrorCode(), "uaa_users", false, null);
        return ResponseEntity.ok().body(resultDTO);
    }

    //Constraint violation exception'a düşen hataların düzeltilmesi
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ResultDTO> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request){
        //Exception'da dönen mesaj getiriliyor
        String message = ex.getSQLException().getMessage();
        //Exception'da dönen constraint getiriliyor
        String constraintName = ex.getConstraintName();
        //Constraint'in foreign key constraint olma durumu kontrol ediliyor
        if(message.contains("violates foreign key constraint")) {
            //Hata foreign key constraint hatası ise ForeignKeyConstraint olarak tanımlı mesajın dönmesi
            return messageTranslator.getResponseEntity("uaa_users_id", "ForeignKeyConstraint", "uaa_users", false, null);
        }
        //Constraint'in unique constraint olma durumu kontrol ediliyor
        if(message.contains("unique constraint")) {
            //ConstraintViolationStaticMapper'da tanımlı unique alanların veritabanındaki alan karşılıklarının getirilmesi
            Map<String, String> map = ConstraintViolationStaticMapper.map;
            //Hata kodu olarak "UniqueConstraint" code'a yazılıyor
            String code = "UniqueConstraint";
            //map'ten unique olarak tanımlı alan getiriliyor
            String fieldName = map.get(constraintName);
            //Mesajdan kullanıcının girmek istediği değer (value) getiriliyor
            String value = StringUtils.substringBetween(message, "=(", ")");
            //fieldName, value ve code MessageTranslator'daki getConstraintViolationMessage() fonksiyonuna gidilerek orada oluşan mesaj getiriliyor
            String constraintViolationMessage = messageTranslator.getConstraintViolationMessage(fieldName, value, code);
            //Dönecek ResultDTO'nun mesajı constraintViolationMessage olarak set ediliyor
            ResultDTO resultDTO = new ResultDTO(false, null, constraintViolationMessage);
            return ResponseEntity.ok().body(resultDTO);
        }
        ResultDTO resultDTO = new ResultDTO();
        return ResponseEntity.ok().body(resultDTO);
    }
}
