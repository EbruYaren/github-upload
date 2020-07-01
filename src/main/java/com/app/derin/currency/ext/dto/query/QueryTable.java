package com.app.derin.currency.ext.dto.query;

import java.util.Objects;

public class QueryTable
{
    private String name;

    private String prefix;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueryTable queryFrom = (QueryTable) o;
        return Objects.equals(getName(), queryFrom.getName()) &&
            Objects.equals(getPrefix(), queryFrom.getPrefix());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPrefix());
    }

    @Override
    public String toString() {
        return "QueryFrom{" +
            "name='" + name + '\'' +
            ", prefix='" + prefix + '\'' +
            '}';
    }
}
