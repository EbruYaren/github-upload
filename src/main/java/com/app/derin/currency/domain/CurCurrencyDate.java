package com.app.derin.currency.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A CurCurrencyDate.
 */
@Entity
@Table(name = "cur_currency_date")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CurCurrencyDate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "currency_date")
    private LocalDate currencyDate;

    @Column(name = "source_unit_value")
    private Integer sourceUnitValue;

    @Column(name = "result_unit_value")
    private Integer resultUnitValue;

    @Column(name = "buying_rate")
    private Double buyingRate;

    @Column(name = "selling_rate")
    private Double sellingRate;

    @Column(name = "effective_buying_rate")
    private Double effectiveBuyingRate;

    @Column(name = "effective_selling_rate")
    private Double effectiveSellingRate;

    @Column(name = "is_service")
    @ColumnDefault("false")
    private Boolean isService;

    @ManyToOne
    @JsonIgnoreProperties("curCurrencyDates")
    private CurCurrencies sourceCurrency;

    @ManyToOne
    @JsonIgnoreProperties("curCurrencyDates")
    private CurCurrencies resultCurrency;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCurrencyDate() {
        return currencyDate;
    }

    public CurCurrencyDate currencyDate(LocalDate currencyDate) {
        this.currencyDate = currencyDate;
        return this;
    }

    public void setCurrencyDate(LocalDate currencyDate) {
        this.currencyDate = currencyDate;
    }

    public Integer getSourceUnitValue() {
        return sourceUnitValue;
    }

    public CurCurrencyDate sourceUnitValue(Integer sourceUnitValue) {
        this.sourceUnitValue = sourceUnitValue;
        return this;
    }

    public void setSourceUnitValue(Integer sourceUnitValue) {
        this.sourceUnitValue = sourceUnitValue;
    }

    public Integer getResultUnitValue() {
        return resultUnitValue;
    }

    public CurCurrencyDate resultUnitValue(Integer resultUnitValue) {
        this.resultUnitValue = resultUnitValue;
        return this;
    }

    public void setResultUnitValue(Integer resultUnitValue) {
        this.resultUnitValue = resultUnitValue;
    }

    public Double getBuyingRate() {
        return buyingRate;
    }

    public CurCurrencyDate buyingRate(Double buyingRate) {
        this.buyingRate = buyingRate;
        return this;
    }

    public void setBuyingRate(Double buyingRate) {
        this.buyingRate = buyingRate;
    }

    public Double getSellingRate() {
        return sellingRate;
    }

    public CurCurrencyDate sellingRate(Double sellingRate) {
        this.sellingRate = sellingRate;
        return this;
    }

    public void setSellingRate(Double sellingRate) {
        this.sellingRate = sellingRate;
    }

    public Double getEffectiveBuyingRate() {
        return effectiveBuyingRate;
    }

    public CurCurrencyDate effectiveBuyingRate(Double effectiveBuyingRate) {
        this.effectiveBuyingRate = effectiveBuyingRate;
        return this;
    }

    public void setEffectiveBuyingRate(Double effectiveBuyingRate) {
        this.effectiveBuyingRate = effectiveBuyingRate;
    }

    public Double getEffectiveSellingRate() {
        return effectiveSellingRate;
    }

    public CurCurrencyDate effectiveSellingRate(Double effectiveSellingRate) {
        this.effectiveSellingRate = effectiveSellingRate;
        return this;
    }

    public void setEffectiveSellingRate(Double effectiveSellingRate) {
        this.effectiveSellingRate = effectiveSellingRate;
    }

    public Boolean isIsService() {
        return isService;
    }

    public CurCurrencyDate isService(Boolean isService) {
        this.isService = isService;
        return this;
    }

    public void setIsService(Boolean isService) {
        this.isService = isService;
    }

    public CurCurrencies getSourceCurrency() {
        return sourceCurrency;
    }

    public CurCurrencyDate sourceCurrency(CurCurrencies curCurrencies) {
        this.sourceCurrency = curCurrencies;
        return this;
    }

    public void setSourceCurrency(CurCurrencies curCurrencies) {
        this.sourceCurrency = curCurrencies;
    }

    public CurCurrencies getResultCurrency() {
        return resultCurrency;
    }

    public CurCurrencyDate resultCurrency(CurCurrencies curCurrencies) {
        this.resultCurrency = curCurrencies;
        return this;
    }

    public void setResultCurrency(CurCurrencies curCurrencies) {
        this.resultCurrency = curCurrencies;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CurCurrencyDate)) {
            return false;
        }
        return id != null && id.equals(((CurCurrencyDate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CurCurrencyDate{" +
            "id=" + getId() +
            ", currencyDate='" + getCurrencyDate() + "'" +
            ", sourceUnitValue=" + getSourceUnitValue() +
            ", resultUnitValue=" + getResultUnitValue() +
            ", buyingRate=" + getBuyingRate() +
            ", sellingRate=" + getSellingRate() +
            ", effectiveBuyingRate=" + getEffectiveBuyingRate() +
            ", effectiveSellingRate=" + getEffectiveSellingRate() +
            ", isService='" + isIsService() + "'" +
            "}";
    }
}
