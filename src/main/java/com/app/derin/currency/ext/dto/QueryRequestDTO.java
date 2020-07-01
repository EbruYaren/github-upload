package com.app.derin.currency.ext.dto;

import com.app.derin.currency.ext.dto.query.QueryFields;

import java.util.Objects;
import java.util.Set;

public class QueryRequestDTO {
    Set<QueryFields> fields;

    String strQuery;

    public Set<QueryFields> getFields() {
        return fields;
    }

    public void setFields(Set<QueryFields> fields) {
        this.fields = fields;
    }

    public String getStrQuery() {
        return strQuery;
    }

    public void setStrQuery(String strQuery) {
        this.strQuery = strQuery;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueryRequestDTO that = (QueryRequestDTO) o;
        return Objects.equals(getFields(), that.getFields()) &&
            Objects.equals(getStrQuery(), that.getStrQuery());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFields(), getStrQuery());
    }

    @Override
    public String toString() {
        return "QueryRequestDTO{" +
            "fields=" + fields +
            ", strQuery='" + strQuery + '\'' +
            '}';
    }
}
