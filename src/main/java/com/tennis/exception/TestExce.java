package com.tennis.exception;

public class TestExce extends AppException {
    public TestExce(ErrorInfo errorInfo, Throwable cause) {
        super(errorInfo, cause);
    }
}
