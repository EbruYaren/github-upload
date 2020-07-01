package com.app.derin.currency.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A CurConfig.
 */
@Entity
@Table(name = "cur_config")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CurConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "currency_import_url")
    private String currencyImportUrl;

    @Column(name = "currency_import_time")
    private String currencyImportTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrencyImportUrl() {
        return currencyImportUrl;
    }

    public CurConfig currencyImportUrl(String currencyImportUrl) {
        this.currencyImportUrl = currencyImportUrl.trim();
        return this;
    }

    public void setCurrencyImportUrl(String currencyImportUrl) {
        this.currencyImportUrl = currencyImportUrl.trim();
    }

    public String getCurrencyImportTime() {
        return currencyImportTime;
    }

    public CurConfig currencyImportTime(String currencyImportTime) {
        this.currencyImportTime = currencyImportTime.trim();
        return this;
    }

    public void setCurrencyImportTime(String currencyImportTime) {
        this.currencyImportTime = currencyImportTime.trim();
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CurConfig)) {
            return false;
        }
        return id != null && id.equals(((CurConfig) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CurConfig{" +
            "id=" + getId() +
            ", currencyImportUrl='" + getCurrencyImportUrl() + "'" +
            ", currencyImportTime='" + getCurrencyImportTime() + "'" +
            "}";
    }
}
