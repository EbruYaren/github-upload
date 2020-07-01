package com.app.derin.currency.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A CurCurrencies.
 */
@Entity
@Table(name = "cur_currencies")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CurCurrencies implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "currency_name")
    private String currencyName;

    @Column(name = "currency_symbol")
    private String currencySymbol;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public CurCurrencies currencyCode(String currencyCode) {
        this.currencyCode = currencyCode.trim();
        return this;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode.trim();
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public CurCurrencies currencyName(String currencyName) {
        this.currencyName = currencyName.trim();
        return this;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName.trim();
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public CurCurrencies currencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol.trim();
        return this;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol.trim();
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CurCurrencies)) {
            return false;
        }
        return id != null && id.equals(((CurCurrencies) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CurCurrencies{" +
            "id=" + getId() +
            ", currencyCode='" + getCurrencyCode() + "'" +
            ", currencyName='" + getCurrencyName() + "'" +
            ", currencySymbol='" + getCurrencySymbol() + "'" +
            "}";
    }
}
