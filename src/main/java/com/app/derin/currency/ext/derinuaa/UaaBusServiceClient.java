package com.app.derin.currency.ext.derinuaa;

import com.app.derin.currency.ext.dto.ResultDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "derinuaa", decode404 = true)
public interface UaaBusServiceClient {
    @RequestMapping(value = "/uaa-dictionary/by-codes-and-langId/{codes}/{id}", method = RequestMethod.GET)
    ResponseEntity<ResultDTO> getDictionaryFromCodesAndLangId(@PathVariable("codes") String[] codes, @PathVariable("id") Long id);

    @RequestMapping(value = "/uaa-messages/by-codes-and-langId/{codes}/{id}", method = RequestMethod.GET)
    ResponseEntity<ResultDTO> getMessagesFromCodesAndLangId(@PathVariable("codes") String[] codes, @PathVariable("id") Long id);

    @RequestMapping(value = "/api/account", method = RequestMethod.GET)
    ResultDTO getAccount();

    @RequestMapping(value = "/get-user-by-user-name/{userName}", method = RequestMethod.GET)
    ResponseEntity<ResultDTO> getUaaUserByUserName(@PathVariable("userName") String userName) ;

}
