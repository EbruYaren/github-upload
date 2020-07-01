package com.app.derin.currency.ext.dto.query;

import java.util.Objects;

public class QueryGroupBy{
    private String prefix;

    private String name;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueryGroupBy that = (QueryGroupBy) o;
        return Objects.equals(getPrefix(), that.getPrefix()) &&
            Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPrefix(), getName());
    }

    @Override
    public String toString() {
        return "QueryGroupBy{" +
            "prefix='" + prefix + '\'' +
            ", name='" + name + '\'' +
            '}';
    }
}
