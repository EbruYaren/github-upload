package com.app.derin.currency.ext.dto.query;

import java.util.Objects;

public class QueryHaving{
    private String condition;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueryHaving that = (QueryHaving) o;
        return Objects.equals(getCondition(), that.getCondition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCondition());
    }

    @Override
    public String toString() {
        return "QueryHaving{" +
            "condition='" + condition + '\'' +
            '}';
    }
}
