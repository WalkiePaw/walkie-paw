package com.WalkiePaw.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class JwtIssuer {

    /**
     * JWT 토큰을 발급해주는 메서드
     */
    public String issue(String email, List<String> roles) {
        return JWT.create()
                .withSubject(String.valueOf(email))
                .withExpiresAt(Instant.now().plus(Duration.of(1, ChronoUnit.DAYS))) // 보통 duration 짧게 하는데 튜토리얼이니까 1day
                .withClaim("r", roles) // JWT에 저장할 정보
                .sign(Algorithm.HMAC256("secret")); // JWT서명에 들어가는 알고리즘과 비밀키, 추후에 properties로 분리함.
    }
}
