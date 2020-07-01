package com.app.derin.currency.ext.derinfw.dto;

import java.io.Serializable;
import java.util.Objects;


public class McfClientCompaniesDTO implements Serializable {

    private Long id;

    private String code;

    private String name;

    private String currencyImportUrl;

    private String currencyImportTime;

    private Long curCurrencyId;


    private Long clientId;

    private Long companyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Long getCurCurrencyId() {
        return curCurrencyId;
    }

    public void setCurCurrencyId(Long curCurrencyId) {
        this.curCurrencyId = curCurrencyId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long mcfClientsId) {
        this.clientId = mcfClientsId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long mcfCompaniesId) {
        this.companyId = mcfCompaniesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        McfClientCompaniesDTO mcfClientCompaniesDTO = (McfClientCompaniesDTO) o;
        if (mcfClientCompaniesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mcfClientCompaniesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "McfClientCompaniesDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", currencyImportUrl='" + getCurrencyImportUrl() + "'" +
            ", currencyImportTime='" + getCurrencyImportTime() + "'" +
            ", curCurrencyId=" + getCurCurrencyId() +
            ", clientId=" + getClientId() +
            ", companyId=" + getCompanyId() +
            "}";
    }
}
