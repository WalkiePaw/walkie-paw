package com.WalkiePaw.presentation.domain.member.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SocialSignUpRequest {

    private String email;
    private String nickname;
    private String phoneNumber;
    private LocalDate birth;
    private String address;
    private String profile;
    private String photo;

}
