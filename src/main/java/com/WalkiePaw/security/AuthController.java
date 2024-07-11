package com.WalkiePaw.security;

import com.WalkiePaw.security.dto.LoginRequest;
import com.WalkiePaw.security.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest request) {
        System.out.println("controller login start");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        ); // request의 email과 password를 authenticationManger에게 넘겨줘서 검증 후 authentication을 리턴함.
        // authenticationManger에서는 customUserDetailService를 사용하여 사용자를 찾아 검증하도록 되어있음.
        // customUserDetailService의 loadUserByUsername 사용함.
        // loadUserByUsername은 UserDetailsService에 존재, 구현하여 사용.
        // manager에는 passwordEncoder로 BCryptEncoder가 사용됨. 비밀번호를 안전하게 저장하고 검증하는데 사용.
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        List<String> roles = principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        String token = jwtIssuer.issue(principal.getEmail(), principal.getMemberId(), roles);// 토큰을 발급해줌.
        System.out.println("controller login end");
        return LoginResponse.builder()
                .token(token)
                .build();
    }
}
