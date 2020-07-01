package com.app.derin.currency.ext.dto.query;

import java.util.Objects;
import java.util.Set;

public class QueryCondition {
    String condition;

    String parameter;

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

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueryCondition that = (QueryCondition) o;
        return getCondition().equals(that.getCondition()) &&
            Objects.equals(getParameter(), that.getParameter()) &&
            Objects.equals(getAnd(), that.getAnd()) &&
            Objects.equals(getOr(), that.getOr());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCondition(), getParameter(), getAnd(), getOr());
    }

    @Override
    public String toString() {
        return "QueryCondition{" +
            "condition='" + condition + '\'' +
            ", parameter='" + parameter + '\'' +
            ", and=" + and +
            ", or=" + or +
            '}';
    }
}
