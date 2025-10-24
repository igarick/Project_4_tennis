package com.tennis.exception;

import lombok.Getter;

import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

@Getter
public enum ErrorInfo {
    DATABASE_CONNECTION_ERROR("Database connection error", SC_INTERNAL_SERVER_ERROR),
    RETRIEVING_FROM_DATABASE_FAILED("Failed to retrieve data from the database", SC_INTERNAL_SERVER_ERROR),
    SAVING_TO_DATABASE_FAILED("Failed to save data to the database", SC_INTERNAL_SERVER_ERROR),
    NAME_ERROR("The name must be 2 - 20 English letters");

    private final String message;
    private final int statusCode;

    ErrorInfo(String message) {
        this(message, 0);
    }

    ErrorInfo(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
