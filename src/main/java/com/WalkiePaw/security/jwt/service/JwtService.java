package com.WalkiePaw.security.jwt.service;

import com.WalkiePaw.domain.member.Repository.MemberRepository;
import com.WalkiePaw.domain.member.entity.Role;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter
@Slf4j
public class JwtService {

  public static final String ROLE_CLAIM = "role";
  @Value("${jwt.secretKey}")
  private String secretKey;

  @Value("${jwt.access.expiration}")
  private Long accessTokenExpirationPeriod;

  @Value("${jwt.access.header}")
  private String accessHeader;

  public static final String PHOTO_URL = "photoUrl";

  /**
   * JWT의 Subject와 Claim으로 email 사용 -> 클레임의 name을 "email"으로 설정
   * JWT의 헤더에 들어오는 값 : 'Authorization(Key) = Bearer {토큰} (Value)' 형식
   */
  private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
  private static final String EMAIL_CLAIM = "email";
  private static final String ID_CLAIM = "id";
  private static final String NICKNAME_CLAIM = "nickname";
  private static final String BEARER = "Bearer ";

  private final MemberRepository memberRepository;
  private final ObjectMapper objectMapper;

  /**
   * AccessToken 생성 메소드
   */
  public String createAccessToken(String email, Integer memberId, String nickname, String photoUrl, String role) {
    Date now = new Date();
    log.info("nickname = {}", nickname);
    return JWT.create() // JWT 토큰을 생성하는 빌더 반환
        .withSubject(ACCESS_TOKEN_SUBJECT) // JWT의 Subject 지정 -> AccessToken이므로 AccessToken
        .withExpiresAt(new Date(now.getTime() + accessTokenExpirationPeriod)) // 토큰 만료 시간 설정

        //클레임으로는 저희는 email 하나만 사용합니다.
        //추가적으로 식별자나, 이름 등의 정보를 더 추가하셔도 됩니다.
        //추가하실 경우 .withClaim(클래임 이름, 클래임 값) 으로 설정해주시면 됩니다
        .withClaim(ROLE_CLAIM, role)
        .withClaim(EMAIL_CLAIM, email)
        .withClaim(ID_CLAIM, memberId)
        .withClaim(NICKNAME_CLAIM, nickname)
        .withClaim(PHOTO_URL, photoUrl)
        .sign(Algorithm.HMAC512(secretKey)); // HMAC512 알고리즘 사용, application-jwt.yml에서 지정한 secret 키로 암호화
  }


  /**
   * AccessToken 바디에 실어서 보내기
   */
  public void sendAccessToken(HttpServletResponse response, String accessToken) throws IOException {
    Map<String, Object> responseBody = new HashMap<>();
    responseBody.put("token", accessToken);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("UTF-8");
    response.setStatus(HttpServletResponse.SC_OK);
    log.info("재발급된 Access Token : {}", accessToken);

    objectMapper.writeValue(response.getWriter(), responseBody);
  }

  /**
   * 헤더에서 AccessToken 추출
   * 토큰 형식 : Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기 위해서
   * 헤더를 가져온 후 "Bearer"를 삭제(""로 replace)
   */
  public Optional<String> extractAccessToken(HttpServletRequest request) {
    return Optional.ofNullable(request.getHeader(accessHeader))
        .filter(refreshToken -> refreshToken.startsWith(BEARER))
        .map(refreshToken -> refreshToken.replace(BEARER, ""));
  }

  /**
   * AccessToken에서 Email 추출
   * 추출 전에 JWT.require()로 검증기 생성
   * verify로 AceessToken 검증 후
   * 유효하다면 getClaim()으로 이메일 추출
   * 유효하지 않다면 빈 Optional 객체 반환
   */
  public Optional<String> extractEmail(String accessToken) {
    try {
      // 토큰 유효성 검사하는 데에 사용할 알고리즘이 있는 JWT verifier builder 반환
      return Optional.ofNullable(JWT.require(Algorithm.HMAC512(secretKey))
          .build() // 반환된 빌더로 JWT verifier 생성
          .verify(accessToken) // accessToken을 검증하고 유효하지 않다면 예외 발생
          .getClaim(EMAIL_CLAIM) // claim(Emial) 가져오기
          .asString());
    } catch (Exception e) {
      log.error("액세스 토큰이 유효하지 않습니다.");
      return Optional.empty();
    }
  }

  /**
   * AccessToken 헤더 설정
   */
  public void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
    response.setHeader(accessHeader, accessToken);
  }

  public boolean isTokenValid(String token) {
    try {
      JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token);
      return true;
    } catch (Exception e) {
      log.error("유효하지 않은 토큰입니다. {}", e.getMessage());
      return false;
    }
  }
}