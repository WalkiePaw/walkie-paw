package com.WalkiePaw.presentation.domain.mail.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EmailAuthResponse {
    private Integer memberId;
    private String result;
}
