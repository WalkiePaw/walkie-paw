package com.WalkiePaw.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

@Component
public class JwtDecoder {

    /**
     * JWT 토큰을 디코딩하는 메서드
     */
    public DecodedJWT decode(String token) {
        return JWT.require(Algorithm.HMAC256("secret")) // JWT서명에 들어가는 알고리즘과 비밀키, 추후에 properties로 분리함.
                .build() // 디코더 생성
                .verify(token); // 검증 및 디코딩
        // SignatureVerificationException
        // JWTDecodeException
    }
}
