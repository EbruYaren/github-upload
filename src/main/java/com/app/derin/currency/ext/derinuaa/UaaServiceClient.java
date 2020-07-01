package com.app.derin.currency.ext.derinuaa;

import com.app.derin.currency.client.AuthorizedFeignClient;
import com.app.derin.currency.ext.dto.ResultDTO;
import com.app.derin.currency.ext.dto.TranslationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
@Component
@AuthorizedFeignClient(name = "derinuaa", decode404 = true)
public interface UaaServiceClient {
//
//    @RequestMapping(value = "/api/get-user-by-user-name/{userName}", method = RequestMethod.GET)
//    ResponseEntity<ResultDTO> getUaaUserByUserName(@PathVariable("userName") String userName);
//
//    @RequestMapping(value = "/api/uaa-user/{id}", method = RequestMethod.GET)
//    ResponseEntity<ResultDTO> getUaaUser(@PathVariable("id") Long id);
//
//    @RequestMapping(value = "api/uaa-user-language-id/{userName}", method = RequestMethod.GET)
//    ResponseEntity<ResultDTO> getLangIdByActiveUser(@PathVariable("userName") String userName) ;
//
//    @GetMapping("api/uaa-dictionary/by-codes-and-langId/{codes}/{id}")
//    ResponseEntity<ResultDTO> getDictionaryFromCodesAndLangId(@PathVariable("codes") String[] codes, @PathVariable("id") Long id);
//
//    @GetMapping("api/uaa-messages/by-codes-and-langId/{codes}/{id}")
//    ResponseEntity<ResultDTO> getMessagesFromCodesAndLangId(@PathVariable("codes") String[] codes, @PathVariable("id") Long id);

    @RequestMapping(value = "/api/get-translation", method = RequestMethod.POST)
    ResponseEntity<ResultDTO> getTranslation(@RequestBody TranslationDTO translationDTO);
}
