package com.app.derin.currency.ext.dto;

import com.app.derin.currency.ext.derinuaa.UaaDictionaryDTO;
import com.app.derin.currency.ext.derinuaa.UaaMessagesDTO;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class TranslationReturnDTO {
    List<UaaDictionaryDTO> uaaDictionaryDTOList;
    List<UaaMessagesDTO> uaaMessagesDTOS;

    @Override
    public String toString() {
        return "TranslationReturnDTO{" +
            "uaaDictionaryDTOList=" + uaaDictionaryDTOList +
            ", uaaMessagesDTOS=" + uaaMessagesDTOS +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TranslationReturnDTO)) return false;
        TranslationReturnDTO that = (TranslationReturnDTO) o;
        return Objects.equals(uaaDictionaryDTOList, that.uaaDictionaryDTOList) &&
            Objects.equals(uaaMessagesDTOS, that.uaaMessagesDTOS);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uaaDictionaryDTOList, uaaMessagesDTOS);
    }

    public List<UaaDictionaryDTO> getUaaDictionaryDTOList() {
        return uaaDictionaryDTOList;
    }

    public void setUaaDictionaryDTOList(List<UaaDictionaryDTO> uaaDictionaryDTOList) {
        this.uaaDictionaryDTOList = uaaDictionaryDTOList;
    }

    public List<UaaMessagesDTO> getUaaMessagesDTOS() {
        return uaaMessagesDTOS;
    }

    public void setUaaMessagesDTOS(List<UaaMessagesDTO> uaaMessagesDTOS) {
        this.uaaMessagesDTOS = uaaMessagesDTOS;
    }
}
