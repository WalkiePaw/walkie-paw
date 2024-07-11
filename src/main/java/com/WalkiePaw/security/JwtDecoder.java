package com.WalkiePaw.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JwtDecoder {

    private static final Logger log = LoggerFactory.getLogger(JwtDecoder.class);

    /**
     * JWT 토큰을 디코딩하는 메서드
     */
    public DecodedJWT decode(String token) {
        DecodedJWT secret = JWT.require(Algorithm.HMAC256("secret")) // JWT서명에 들어가는 알고리즘과 비밀키, 추후에 properties로 분리함.
                .build() // 디코더 생성
                .verify(token);// 검증 및 디코딩
// SignatureVerificationException
        // JWTDecodeException
        log.info("decoded JWT : {}", secret.getHeader());
        log.info("decoded JWT : {}", secret.getPayload());
        log.info("decoded JWT : {}", secret.getSignature());
        log.info("decoded JWT : {}", secret.getClaim("id"));

        return secret;
    }
}
