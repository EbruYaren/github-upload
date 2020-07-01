package com.app.derin.currency.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.app.derin.currency.domain.CurCurrencies} entity.
 */
public class CurCurrenciesDTO implements Serializable {

    private Long id;

    private String currencyCode;

    private String currencyName;

    private String currencySymbol;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CurCurrenciesDTO curCurrenciesDTO = (CurCurrenciesDTO) o;
        if (curCurrenciesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), curCurrenciesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CurCurrenciesDTO{" +
            "id=" + getId() +
            ", currencyCode='" + getCurrencyCode() + "'" +
            ", currencyName='" + getCurrencyName() + "'" +
            ", currencySymbol='" + getCurrencySymbol() + "'" +
            "}";
    }
}
