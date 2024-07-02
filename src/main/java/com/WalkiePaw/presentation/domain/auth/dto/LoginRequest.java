package com.WalkiePaw.presentation.domain.auth.dto;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String name;
    private String password;
}
