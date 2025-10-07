package com.tennis.exception;

import lombok.Getter;

import static jakarta.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

@Getter
public enum ErrorInfo {
    DATABASE_CONNECTION_ERROR("Database connection error", SC_INTERNAL_SERVER_ERROR),


    TEST_EX("test", SC_BAD_REQUEST);

    private final String message;
    private final int statusCode;

    ErrorInfo(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
