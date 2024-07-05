package com.WalkiePaw.presentation.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class EmailVerifyResponse {

    private boolean verifyResult;

    public EmailVerifyResponse(final boolean verifyResult) {
        this.verifyResult = verifyResult;
    }
}
