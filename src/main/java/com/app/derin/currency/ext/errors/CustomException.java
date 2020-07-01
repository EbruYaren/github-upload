package com.app.derin.currency.ext.errors;

public class CustomException extends Exception {

    private String errorCode;

    public CustomException(String errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
