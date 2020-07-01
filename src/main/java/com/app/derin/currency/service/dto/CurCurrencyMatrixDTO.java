package com.app.derin.currency.service.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.app.derin.currency.domain.CurCurrencyMatrix} entity.
 */
public class CurCurrencyMatrixDTO implements Serializable {

    private Long id;

    private Integer sourceUnitValue;

    private Integer resultUnitValue;

    private Double buyingRate;

    private Double sellingRate;

    private Double effectiveBuyingRate;

    private Double effectiveSellingRate;

    private LocalDate lastCurrencyDate;


    private Long sourceCurrencyId;

    private Long resultCurrencyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSourceUnitValue() {
        return sourceUnitValue;
    }

    public void setSourceUnitValue(Integer sourceUnitValue) {
        this.sourceUnitValue = sourceUnitValue;
    }

    public Integer getResultUnitValue() {
        return resultUnitValue;
    }

    public void setResultUnitValue(Integer resultUnitValue) {
        this.resultUnitValue = resultUnitValue;
    }

    public Double getBuyingRate() {
        return buyingRate;
    }

    public void setBuyingRate(Double buyingRate) {
        this.buyingRate = buyingRate;
    }

    public Double getSellingRate() {
        return sellingRate;
    }

    public void setSellingRate(Double sellingRate) {
        this.sellingRate = sellingRate;
    }

    public Double getEffectiveBuyingRate() {
        return effectiveBuyingRate;
    }

    public void setEffectiveBuyingRate(Double effectiveBuyingRate) {
        this.effectiveBuyingRate = effectiveBuyingRate;
    }

    public Double getEffectiveSellingRate() {
        return effectiveSellingRate;
    }

    public void setEffectiveSellingRate(Double effectiveSellingRate) {
        this.effectiveSellingRate = effectiveSellingRate;
    }

    public LocalDate getLastCurrencyDate() {
        return lastCurrencyDate;
    }

    public void setLastCurrencyDate(LocalDate lastCurrencyDate) {
        this.lastCurrencyDate = lastCurrencyDate;
    }

    public Long getSourceCurrencyId() {
        return sourceCurrencyId;
    }

    public void setSourceCurrencyId(Long curCurrenciesId) {
        this.sourceCurrencyId = curCurrenciesId;
    }

    public Long getResultCurrencyId() {
        return resultCurrencyId;
    }

    public void setResultCurrencyId(Long curCurrenciesId) {
        this.resultCurrencyId = curCurrenciesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CurCurrencyMatrixDTO curCurrencyMatrixDTO = (CurCurrencyMatrixDTO) o;
        if (curCurrencyMatrixDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), curCurrencyMatrixDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CurCurrencyMatrixDTO{" +
            "id=" + getId() +
            ", sourceUnitValue=" + getSourceUnitValue() +
            ", resultUnitValue=" + getResultUnitValue() +
            ", buyingRate=" + getBuyingRate() +
            ", sellingRate=" + getSellingRate() +
            ", effectiveBuyingRate=" + getEffectiveBuyingRate() +
            ", effectiveSellingRate=" + getEffectiveSellingRate() +
            ", lastCurrencyDate='" + getLastCurrencyDate() + "'" +
            ", sourceCurrencyId=" + getSourceCurrencyId() +
            ", resultCurrencyId=" + getResultCurrencyId() +
            "}";
    }
}
