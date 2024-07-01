package com.WalkiePaw.presentation.domain.oauth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class KakaoInfoResponse {
    private final String email;
    private final String name;

}
