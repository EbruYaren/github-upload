package com.app.derin.currency.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link com.app.derin.currency.domain.CurCurrencyDate} entity. This class is used
 * in {@link com.app.derin.currency.web.rest.CurCurrencyDateResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cur-currency-dates?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CurCurrencyDateCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter currencyDate;

    private IntegerFilter sourceUnitValue;

    private IntegerFilter resultUnitValue;

    private DoubleFilter buyingRate;

    private DoubleFilter sellingRate;

    private DoubleFilter effectiveBuyingRate;

    private DoubleFilter effectiveSellingRate;

    private BooleanFilter isService;

    private LongFilter sourceCurrencyId;

    private LongFilter resultCurrencyId;

    public CurCurrencyDateCriteria() {
    }

    public CurCurrencyDateCriteria(CurCurrencyDateCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.currencyDate = other.currencyDate == null ? null : other.currencyDate.copy();
        this.sourceUnitValue = other.sourceUnitValue == null ? null : other.sourceUnitValue.copy();
        this.resultUnitValue = other.resultUnitValue == null ? null : other.resultUnitValue.copy();
        this.buyingRate = other.buyingRate == null ? null : other.buyingRate.copy();
        this.sellingRate = other.sellingRate == null ? null : other.sellingRate.copy();
        this.effectiveBuyingRate = other.effectiveBuyingRate == null ? null : other.effectiveBuyingRate.copy();
        this.effectiveSellingRate = other.effectiveSellingRate == null ? null : other.effectiveSellingRate.copy();
        this.isService = other.isService == null ? null : other.isService.copy();
        this.sourceCurrencyId = other.sourceCurrencyId == null ? null : other.sourceCurrencyId.copy();
        this.resultCurrencyId = other.resultCurrencyId == null ? null : other.resultCurrencyId.copy();
    }

    @Override
    public CurCurrencyDateCriteria copy() {
        return new CurCurrencyDateCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getCurrencyDate() {
        return currencyDate;
    }

    public void setCurrencyDate(LocalDateFilter currencyDate) {
        this.currencyDate = currencyDate;
    }

    public IntegerFilter getSourceUnitValue() {
        return sourceUnitValue;
    }

    public void setSourceUnitValue(IntegerFilter sourceUnitValue) {
        this.sourceUnitValue = sourceUnitValue;
    }

    public IntegerFilter getResultUnitValue() {
        return resultUnitValue;
    }

    public void setResultUnitValue(IntegerFilter resultUnitValue) {
        this.resultUnitValue = resultUnitValue;
    }

    public DoubleFilter getBuyingRate() {
        return buyingRate;
    }

    public void setBuyingRate(DoubleFilter buyingRate) {
        this.buyingRate = buyingRate;
    }

    public DoubleFilter getSellingRate() {
        return sellingRate;
    }

    public void setSellingRate(DoubleFilter sellingRate) {
        this.sellingRate = sellingRate;
    }

    public DoubleFilter getEffectiveBuyingRate() {
        return effectiveBuyingRate;
    }

    public void setEffectiveBuyingRate(DoubleFilter effectiveBuyingRate) {
        this.effectiveBuyingRate = effectiveBuyingRate;
    }

    public DoubleFilter getEffectiveSellingRate() {
        return effectiveSellingRate;
    }

    public void setEffectiveSellingRate(DoubleFilter effectiveSellingRate) {
        this.effectiveSellingRate = effectiveSellingRate;
    }

    public BooleanFilter getIsService() {
        return isService;
    }

    public void setIsService(BooleanFilter isService) {
        this.isService = isService;
    }

    public LongFilter getSourceCurrencyId() {
        return sourceCurrencyId;
    }

    public void setSourceCurrencyId(LongFilter sourceCurrencyId) {
        this.sourceCurrencyId = sourceCurrencyId;
    }

    public LongFilter getResultCurrencyId() {
        return resultCurrencyId;
    }

    public void setResultCurrencyId(LongFilter resultCurrencyId) {
        this.resultCurrencyId = resultCurrencyId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CurCurrencyDateCriteria that = (CurCurrencyDateCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(currencyDate, that.currencyDate) &&
            Objects.equals(sourceUnitValue, that.sourceUnitValue) &&
            Objects.equals(resultUnitValue, that.resultUnitValue) &&
            Objects.equals(buyingRate, that.buyingRate) &&
            Objects.equals(sellingRate, that.sellingRate) &&
            Objects.equals(effectiveBuyingRate, that.effectiveBuyingRate) &&
            Objects.equals(effectiveSellingRate, that.effectiveSellingRate) &&
            Objects.equals(isService, that.isService) &&
            Objects.equals(sourceCurrencyId, that.sourceCurrencyId) &&
            Objects.equals(resultCurrencyId, that.resultCurrencyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        currencyDate,
        sourceUnitValue,
        resultUnitValue,
        buyingRate,
        sellingRate,
        effectiveBuyingRate,
        effectiveSellingRate,
        isService,
        sourceCurrencyId,
        resultCurrencyId
        );
    }

    @Override
    public String toString() {
        return "CurCurrencyDateCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (currencyDate != null ? "currencyDate=" + currencyDate + ", " : "") +
                (sourceUnitValue != null ? "sourceUnitValue=" + sourceUnitValue + ", " : "") +
                (resultUnitValue != null ? "resultUnitValue=" + resultUnitValue + ", " : "") +
                (buyingRate != null ? "buyingRate=" + buyingRate + ", " : "") +
                (sellingRate != null ? "sellingRate=" + sellingRate + ", " : "") +
                (effectiveBuyingRate != null ? "effectiveBuyingRate=" + effectiveBuyingRate + ", " : "") +
                (effectiveSellingRate != null ? "effectiveSellingRate=" + effectiveSellingRate + ", " : "") +
                (isService != null ? "isService=" + isService + ", " : "") +
                (sourceCurrencyId != null ? "sourceCurrencyId=" + sourceCurrencyId + ", " : "") +
                (resultCurrencyId != null ? "resultCurrencyId=" + resultCurrencyId + ", " : "") +
            "}";
    }

}
