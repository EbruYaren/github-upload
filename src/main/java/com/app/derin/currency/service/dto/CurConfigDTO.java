package com.app.derin.currency.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.app.derin.currency.domain.CurConfig} entity.
 */
public class CurConfigDTO implements Serializable {

    private Long id;

    private String currencyImportUrl;

    private String currencyImportTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrencyImportUrl() {
        return currencyImportUrl;
    }

    public void setCurrencyImportUrl(String currencyImportUrl) {
        this.currencyImportUrl = currencyImportUrl;
    }

    public String getCurrencyImportTime() {
        return currencyImportTime;
    }

    public void setCurrencyImportTime(String currencyImportTime) {
        this.currencyImportTime = currencyImportTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CurConfigDTO curConfigDTO = (CurConfigDTO) o;
        if (curConfigDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), curConfigDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CurConfigDTO{" +
            "id=" + getId() +
            ", currencyImportUrl='" + getCurrencyImportUrl() + "'" +
            ", currencyImportTime='" + getCurrencyImportTime() + "'" +
            "}";
    }
}
