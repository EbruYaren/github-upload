package com.app.derin.currency.ext.dto.query;

import java.util.Objects;

public class QueryOrderBy extends QueryGroupBy{
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        QueryOrderBy that = (QueryOrderBy) o;
        return Objects.equals(getType(), that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getType());
    }

    @Override
    public String toString() {
        return "QueryOrderBy{" +
            "type='" + type + '\'' +
            '}';
    }
}
