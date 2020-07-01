package com.app.derin.currency.ext.dto;


import com.app.derin.currency.ext.derinfw.dto.CurCurrenciesDTOExt;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class ResultDTO<T> {
    public boolean success;
    public T data;
    public String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultDTO(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public ResultDTO(){

    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultDTO<?> resultDTO = (ResultDTO<?>) o;
        return isSuccess() == resultDTO.isSuccess() &&
            getData().equals(resultDTO.getData()) &&
            getMessage().equals(resultDTO.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(isSuccess(), getData(), getMessage());
    }
}

