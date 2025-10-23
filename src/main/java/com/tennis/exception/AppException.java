package com.tennis.exception;

import lombok.Getter;

@Getter
public abstract class AppException extends RuntimeException {
    private final ErrorInfo errorInfo;

    public AppException(ErrorInfo errorInfo, Throwable cause) {
        super(cause);
        this.errorInfo = errorInfo;
    }

    public AppException(ErrorInfo errorInfo) {
        this.errorInfo = errorInfo;
    }
}
