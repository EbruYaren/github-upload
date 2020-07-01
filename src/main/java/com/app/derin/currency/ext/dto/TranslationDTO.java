package com.app.derin.currency.ext.dto;

import java.util.Arrays;
import java.util.Objects;

public class TranslationDTO {
    String[] fields;
    String[] codes;
    String userName;

    public TranslationDTO()
    {

    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return "TranslationDTO{" +
            "fields=" + Arrays.toString(fields) +
            ", codes=" + Arrays.toString(codes) +
            ", userName='" + userName + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TranslationDTO)) return false;
        TranslationDTO that = (TranslationDTO) o;
        return Arrays.equals(fields, that.fields) &&
            Arrays.equals(codes, that.codes) &&
            Objects.equals(userName, that.userName);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(userName);
        result = 31 * result + Arrays.hashCode(fields);
        result = 31 * result + Arrays.hashCode(codes);
        return result;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public TranslationDTO(String[] fields, String[] codes, String userName)
    {
        this.fields = fields;
        this.codes = codes;
        this.userName = userName;
    }

    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }

    public String[] getCodes() {
        return codes;
    }

    public void setCodes(String[] codes) {
        this.codes = codes;
    }
}
