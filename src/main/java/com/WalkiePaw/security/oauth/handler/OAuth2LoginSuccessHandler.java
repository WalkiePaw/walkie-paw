package com.WalkiePaw.security.oauth.handler;

import com.WalkiePaw.domain.member.Repository.MemberRepository;
import com.WalkiePaw.domain.member.entity.Role;
import com.WalkiePaw.security.jwt.service.JwtService;
import com.WalkiePaw.security.oauth.CustomOAuth2User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

  private final JwtService jwtService;
  private final MemberRepository memberRepository;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    log.info("OAuth2 Login 성공!");
    try {
      CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
      log.info("oAuth2User = {}", oAuth2User);
      log.info("email = {}", oAuth2User.getEmail());
      log.info("name = {}", oAuth2User.getName());
      log.info("oAuth2UserAttributes = {}", oAuth2User.getAttributes());

      memberRepository.findByEmail(oAuth2User.getEmail());

      // User의 Role이 GUEST일 경우 처음 요청한 회원이므로 회원가입 페이지로 리다이렉트
      if(oAuth2User.getRole() == Role.GUEST) {
        String accessToken = jwtService.createAccessToken(oAuth2User.getEmail(), oAuth2User.getMemberId());

        jwtService.sendAccessToken(response, accessToken);
//                User findUser = userRepository.findByEmail(oAuth2User.getEmail())
//                                .orElseThrow(() -> new IllegalArgumentException("이메일에 해당하는 유저가 없습니다."));
//                findUser.authorizeUser();
      } else {
        loginSuccess(response, oAuth2User); // 로그인에 성공한 경우 access, refresh 토큰 생성
      }
    } catch (Exception e) {
      throw e;
    }

  }

  private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException {
    String accessToken = jwtService.createAccessToken(oAuth2User.getEmail(), oAuth2User.getMemberId());

    jwtService.sendAccessToken(response, accessToken);
  }
}