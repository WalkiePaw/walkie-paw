package com.WalkiePaw.presentation.domain.member.dto;

import lombok.Getter;

@Getter
public class FindPasswdRequest {
    private String name;
    private String email;
    private String phoneNumber;
}
