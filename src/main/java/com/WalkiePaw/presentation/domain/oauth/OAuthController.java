package com.WalkiePaw.presentation.domain.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class OAuthController {

    private final KakaoOAuthProvider kakaoOAuthProvider;

    @PostMapping("/api/v1/login/oauth")
    public ResponseEntity<OAuthUserinfoResponse> getUserInfo(@RequestBody CodeRequest code) {
        KakaoUserInfoResponse kakaoUserInfoResponse = kakaoOAuthProvider.getUserInfo(code.getCode()).orElseThrow();
        return ResponseEntity.ok(
                new OAuthUserinfoResponse(
                        kakaoUserInfoResponse.getKakaoAccount().getEmail(),
                        kakaoUserInfoResponse.getKakaoAccount().getName()));
    }

}
