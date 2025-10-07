package com.tennis.exception;

import lombok.Getter;

@Getter
public class ConnectionException extends AppException {
    public ConnectionException(ErrorInfo errorInfo, Throwable cause) {
        super(errorInfo, cause);
    }
}
