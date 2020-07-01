package com.app.derin.currency.ext.derinfw.dto;

import com.app.derin.currency.service.dto.CurCurrenciesDTO;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

public class CurCurrenciesDTOExt extends CurCurrenciesDTO {

    @NotNull
    LocalDate startDate;

    @NotNull
    LocalDate endDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CurCurrenciesDTOExt)) return false;
        if (!super.equals(o)) return false;
        CurCurrenciesDTOExt that = (CurCurrenciesDTOExt) o;
        return Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), startDate, endDate);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "CurCurrenciesDTOExt{" +
            "startDate=" + startDate +
            "endDate=" + endDate +
            '}';
    }
}

