package com.WalkiePaw.security.login.handler;

import com.WalkiePaw.security.UserPrincipal;
import com.WalkiePaw.security.jwt.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final JwtService jwtService;

  @Value("${jwt.access.expiration}")
  private String accessTokenExpiration;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException {
    String email = extractUsername(authentication); // 인증 정보에서 Username(email) 추출
    Long userId = extractMemberId(authentication);
    String accessToken = jwtService.createAccessToken(email, userId); // JwtService의 createAccessToken을 사용하여 AccessToken 발급

    jwtService.sendAccessToken(response, accessToken); // 응답 바디에 AccessToken 실어서 응답

    log.info("authentication: {}", authentication);
    log.info("로그인에 성공하였습니다. 이메일 : {}", email);
    log.info("로그인에 성공하였습니다. AccessToken : {}", accessToken);
    log.info("발급된 AccessToken 만료 기간 : {}", accessTokenExpiration);
  }

  private String extractUsername(Authentication authentication) {
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    return userDetails.getUsername();
  }

  private Long extractMemberId(Authentication authentication) {
    UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
    return userPrincipal.getMemberId();
  }
}