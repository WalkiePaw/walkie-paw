package com.WalkiePaw.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ExceptionResponse {
    private final int code;
    private final String message;
}
