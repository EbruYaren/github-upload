package com.app.derin.currency.ext.dto.query;

import java.util.Objects;

public class QueryFields {
    private String name;

    private String type;

    private String prefix;

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
        QueryFields that = (QueryFields) o;
        return getName().equals(that.getName()) &&
            getType().equals(that.getType()) &&
            getPrefix().equals(that.getPrefix());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getType(), getPrefix());
    }

    @Override
    public String toString() {
        return "QueryFeilds{" +
            "name='" + name + '\'' +
            ", type='" + type + '\'' +
            ", prefix='" + prefix + '\'' +
            '}';
    }
}
