package com.app.derin.currency.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A CurCurrencyMatrix.
 */
@Entity
@Table(name = "cur_currency_matrix")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CurCurrencyMatrix implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

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

    @Column(name = "last_currency_date")
    private LocalDate lastCurrencyDate;

    @ManyToOne
    @JsonIgnoreProperties("curCurrencyMatrices")
    private CurCurrencies sourceCurrency;

    @ManyToOne
    @JsonIgnoreProperties("curCurrencyMatrices")
    private CurCurrencies resultCurrency;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSourceUnitValue() {
        return sourceUnitValue;
    }

    public CurCurrencyMatrix sourceUnitValue(Integer sourceUnitValue) {
        this.sourceUnitValue = sourceUnitValue;
        return this;
    }

    public void setSourceUnitValue(Integer sourceUnitValue) {
        this.sourceUnitValue = sourceUnitValue;
    }

    public Integer getResultUnitValue() {
        return resultUnitValue;
    }

    public CurCurrencyMatrix resultUnitValue(Integer resultUnitValue) {
        this.resultUnitValue = resultUnitValue;
        return this;
    }

    public void setResultUnitValue(Integer resultUnitValue) {
        this.resultUnitValue = resultUnitValue;
    }

    public Double getBuyingRate() {
        return buyingRate;
    }

    public CurCurrencyMatrix buyingRate(Double buyingRate) {
        this.buyingRate = buyingRate;
        return this;
    }

    public void setBuyingRate(Double buyingRate) {
        this.buyingRate = buyingRate;
    }

    public Double getSellingRate() {
        return sellingRate;
    }

    public CurCurrencyMatrix sellingRate(Double sellingRate) {
        this.sellingRate = sellingRate;
        return this;
    }

    public void setSellingRate(Double sellingRate) {
        this.sellingRate = sellingRate;
    }

    public Double getEffectiveBuyingRate() {
        return effectiveBuyingRate;
    }

    public CurCurrencyMatrix effectiveBuyingRate(Double effectiveBuyingRate) {
        this.effectiveBuyingRate = effectiveBuyingRate;
        return this;
    }

    public void setEffectiveBuyingRate(Double effectiveBuyingRate) {
        this.effectiveBuyingRate = effectiveBuyingRate;
    }

    public Double getEffectiveSellingRate() {
        return effectiveSellingRate;
    }

    public CurCurrencyMatrix effectiveSellingRate(Double effectiveSellingRate) {
        this.effectiveSellingRate = effectiveSellingRate;
        return this;
    }

    public void setEffectiveSellingRate(Double effectiveSellingRate) {
        this.effectiveSellingRate = effectiveSellingRate;
    }

    public LocalDate getLastCurrencyDate() {
        return lastCurrencyDate;
    }

    public CurCurrencyMatrix lastCurrencyDate(LocalDate lastCurrencyDate) {
        this.lastCurrencyDate = lastCurrencyDate;
        return this;
    }

    public void setLastCurrencyDate(LocalDate lastCurrencyDate) {
        this.lastCurrencyDate = lastCurrencyDate;
    }

    public CurCurrencies getSourceCurrency() {
        return sourceCurrency;
    }

    public CurCurrencyMatrix sourceCurrency(CurCurrencies curCurrencies) {
        this.sourceCurrency = curCurrencies;
        return this;
    }

    public void setSourceCurrency(CurCurrencies curCurrencies) {
        this.sourceCurrency = curCurrencies;
    }

    public CurCurrencies getResultCurrency() {
        return resultCurrency;
    }

    public CurCurrencyMatrix resultCurrency(CurCurrencies curCurrencies) {
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
        if (!(o instanceof CurCurrencyMatrix)) {
            return false;
        }
        return id != null && id.equals(((CurCurrencyMatrix) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CurCurrencyMatrix{" +
            "id=" + getId() +
            ", sourceUnitValue=" + getSourceUnitValue() +
            ", resultUnitValue=" + getResultUnitValue() +
            ", buyingRate=" + getBuyingRate() +
            ", sellingRate=" + getSellingRate() +
            ", effectiveBuyingRate=" + getEffectiveBuyingRate() +
            ", effectiveSellingRate=" + getEffectiveSellingRate() +
            ", lastCurrencyDate='" + getLastCurrencyDate() + "'" +
            "}";
    }
}
