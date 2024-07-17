package com.WalkiePaw.global.exception;

import lombok.Getter;

@Getter
public class MailException extends RuntimeException {
    private final int code;
    private final String message;
    public MailException(final ExceptionCode code) {
        this.code = code.getCode();
        this.message = code.getMessage();
    }
}
