package com.WalkiePaw.presentation.domain.mail.dto;

import lombok.Getter;

@Getter
public class EmailAuthRequest {

    private String email;
    private String authNum;
}
