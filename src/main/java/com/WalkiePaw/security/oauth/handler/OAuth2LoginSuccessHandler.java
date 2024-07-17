package com.WalkiePaw.security.oauth.handler;

import com.WalkiePaw.domain.member.Repository.MemberRepository;
import com.WalkiePaw.domain.member.entity.Role;
import com.WalkiePaw.security.jwt.service.JwtService;
import com.WalkiePaw.security.oauth.CustomOAuth2User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

  private final JwtService jwtService;
  private final MemberRepository memberRepository;
  private final ObjectMapper objectMapper;

  private static final String ACCOUNT_STATUS_KEY = "Account Status";
  private static final String TOKEN_KEY = "Token";
  private static final String NAME_KEY = "Name";
  private static final String EMAIL_KEY = "Email";

//  @Override
//  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//    log.info("OAuth2 Login 성공!");
//    try {
//      CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
//      log.info("oAuth2User = {}", oAuth2User);
//      log.info("email = {}", oAuth2User.getEmail());
//      log.info("name = {}", oAuth2User.getName());
//      log.info("oAuth2UserAttributes = {}", oAuth2User.getAttributes().get(""));
//
//      // User의 Role이 GUEST일 경우 처음 요청한 회원이므로 회원가입 페이지로 리다이렉트
//      if(oAuth2User.getRole() == Role.GUEST) {
//        guestLoginSuccess(response, oAuth2User);
//      } else {
//        userLoginSuccess(response, oAuth2User); // 로그인에 성공한 경우 access, refresh 토큰 생성
//      }
//    } catch (Exception e) {
//      throw e;
//    }
//
//  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

    Map<String, Object> data = new HashMap<>();
    data.put("account status", oAuth2User.getRole() == Role.GUEST ? "New Account" : "Existing Account");
    data.put("token", jwtService.createAccessToken(oAuth2User.getEmail(), oAuth2User.getMemberId(), oAuth2User.getNickname(), oAuth2User.getPhotoUrl()));
    data.put("name", oAuth2User.getName());
    data.put("email", oAuth2User.getEmail());

    String jsonData = new ObjectMapper().writeValueAsString(data);
    String encodedData = URLEncoder.encode(jsonData, StandardCharsets.UTF_8.toString());

    String clientUrl = "http://localhost:5173"; // 프론트엔드 URL
    String redirectUrl = clientUrl + "/oauth2/redirect?socialLoginData=" + encodedData;

    response.sendRedirect(redirectUrl);
  }

  private void userLoginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException {
    String accessToken = jwtService.createAccessToken(oAuth2User.getEmail(), oAuth2User.getMemberId(), oAuth2User.getNickname(), oAuth2User.getPhotoUrl());

    Map<String, Object> responseBody = new HashMap<>();
    responseBody.put(TOKEN_KEY, jwtService.createAccessToken(oAuth2User.getEmail(), oAuth2User.getMemberId(), oAuth2User.getNickname(), oAuth2User.getPhotoUrl()));
    responseBody.put(ACCOUNT_STATUS_KEY, "Exist Account");
    responseBody.put(NAME_KEY, null);
    responseBody.put(EMAIL_KEY, null);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("UTF-8");
    response.setStatus(HttpServletResponse.SC_OK);

    objectMapper.writeValue(response.getWriter(), responseBody);

    jwtService.sendAccessToken(response, accessToken);
  }

  private void guestLoginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException {
    Map<String, Object> responseBody = new HashMap<>();
    responseBody.put(TOKEN_KEY, jwtService.createAccessToken(oAuth2User.getEmail(), oAuth2User.getMemberId(), oAuth2User.getNickname(), oAuth2User.getPhotoUrl()));
    responseBody.put(ACCOUNT_STATUS_KEY, "New Account");
    responseBody.put(NAME_KEY, oAuth2User.getName());
    responseBody.put(EMAIL_KEY, oAuth2User.getEmail());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("UTF-8");
    response.setStatus(HttpServletResponse.SC_OK);

    objectMapper.writeValue(response.getWriter(), responseBody);
  }


}