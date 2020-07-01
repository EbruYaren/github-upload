package com.app.derin.currency.ext.dto.query;

import java.util.Objects;
import java.util.Set;

public class QueryWhere {

    Set<QueryCondition> and;

    Set<QueryCondition> or;

    public Set<QueryCondition> getAnd() {
        return and;
    }

    public void setAnd(Set<QueryCondition> and) {
        this.and = and;
    }

    public Set<QueryCondition> getOr() {
        return or;
    }

    public void setOr(Set<QueryCondition> or) {
        this.or = or;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueryWhere that = (QueryWhere) o;
        return Objects.equals(getAnd(), that.getAnd()) &&
            Objects.equals(getOr(), that.getOr());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAnd(), getOr());
    }

    @Override
    public String toString() {
        return "QueryWhere{" +
            "and=" + and +
            ", or=" + or +
            '}';
    }
}
