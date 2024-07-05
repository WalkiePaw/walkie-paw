package com.WalkiePaw.security;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/")
    public String hello() {
        return "hello";
    }

    @GetMapping("/secured")
    public String secured(@AuthenticationPrincipal UserPrincipal principal) {
        // @AuthenticationPrincipal 은 securityContext에 저장되어있는 Authentication의
        // principal을 꽂아줌.
        // 매 요청마다 jwtFilter에서 요청의 jwtToken을 검증 후,
        // 해당 토큰의 정보를 기반으로 Authentication을 생성하여 securityContext에 저장함.
        // securityContext는 threadLocal에 저장되어 요청마다 독립적임.
        System.out.println("controller secured start");

        if (principal == null) {
            System.out.println("principal is null");
            System.out.println("controller secured end");
            return null;
        }
        System.out.println("controller secured end");
        return "If you see this, then you're logged in" +
                "\n" +
                "email: " + principal.getEmail() +
                "\n" +
                "password: " + principal.getPassword() +
                "\n" +
                "authorities: " + principal.getAuthorities();
    }
}
