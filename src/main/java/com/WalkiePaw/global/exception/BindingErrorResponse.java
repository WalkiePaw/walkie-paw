package com.WalkiePaw.global.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Map;

@Data
public class BindingErrorResponse {
    private final int code;
    private final String message;
    private final Map<String, String> bindingError;
}
