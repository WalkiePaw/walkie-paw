package com.WalkiePaw.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class BadRequestException extends RuntimeException {
    private final int code;
    private final String message;
    public BadRequestException(final ExceptionCode code) {
        this.code = code.getCode();
        this.message = code.getMessage();
    }
}
