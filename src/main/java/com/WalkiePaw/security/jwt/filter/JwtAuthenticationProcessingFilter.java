package com.WalkiePaw.security.jwt.filter;

import com.WalkiePaw.domain.member.Repository.MemberRepository;
import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.security.UserPrincipal;
import com.WalkiePaw.security.jwt.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Jwt 인증 필터
 * "/login" 이외의 URI 요청이 왔을 때 처리하는 필터
 *
 * 기본적으로 사용자는 요청 헤더에 AccessToken만 담아서 요청
 * AccessToken 만료 시에만 RefreshToken을 요청 헤더에 AccessToken과 함께 요청
 *
 * 1. RefreshToken이 없고, AccessToken이 유효한 경우 -> 인증 성공 처리, RefreshToken을 재발급하지는 않는다.
 * 2. RefreshToken이 없고, AccessToken이 없거나 유효하지 않은 경우 -> 인증 실패 처리, 403 ERROR
 * 3. RefreshToken이 있는 경우 -> DB의 RefreshToken과 비교하여 일치하면 AccessToken 재발급, RefreshToken 재발급(RTR 방식)
 *                              인증 성공 처리는 하지 않고 실패 처리
 *
 */
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {

  private static final String NO_CHECK_URL = "/login"; // "/login"으로 들어오는 요청은 Filter 작동 X
  private static final String RESPONSE_CONTENT_TYPE_JSON = "application/json";
  private static final String INVALID_TOKEN_RESPONSE = "{\"error\": \"Invalid token\"}";
  private static final String MISSING_TOKEN_RESPONSE = "{\"error\": \"Missing token\"}";

  private final JwtService jwtService;
  private final MemberRepository memberRepository;

  private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    log.info("JwtAuthenticationProcessingFilter start");
//    if (request.getRequestURI().equals(NO_CHECK_URL) || request.getRequestURI().equals("/index.html")) {
    if(shouldSkipFilter(request)) {
      filterChain.doFilter(request, response); // SkipFilter에 해당하는 요청이 들어오면, 다음 필터 호출
      log.info("JwtAuthenticationProcessingFilter end");
      return; // return으로 이후 현재 필터 진행 막기 (안해주면 아래로 내려가서 계속 필터 진행시킴)
    }
    checkAccessTokenAndAuthentication(request, response, filterChain);
    log.info("JwtAuthenticationProcessingFilter end");
  }

  /**
   * [액세스 토큰 체크 & 인증 처리 메소드]
   * request에서 extractAccessToken()으로 액세스 토큰 추출 후, isTokenValid()로 유효한 토큰인지 검증
   * 유효한 토큰이면, 액세스 토큰에서 extractEmail로 Email을 추출한 후 findByEmail()로 해당 이메일을 사용하는 유저 객체 반환
   * 그 유저 객체를 saveAuthentication()으로 인증 처리하여
   * 인증 허가 처리된 객체를 SecurityContextHolder에 담기
   * 그 후 다음 인증 필터로 진행
   * 토큰이 유효하지 않거나 토큰이 없다면 요청에 대한 처리를 중단하고,
   * 토큰이 유효하지 않거나 토큰이 없다는 응답을 클라이언트에게 보냄.
   */
  public void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    log.info("checkAccessTokenAndAuthentication() start");
    Optional<String> tokenOptional = jwtService.extractAccessToken(request);

    log.info("tokenOptional.isPresent() : {}", tokenOptional.isPresent());
    if (tokenOptional.isPresent()) {
      log.info("Token Provided");
      String token = tokenOptional.get();
      if (jwtService.isTokenValid(token)) {
        jwtService.extractEmail(token)
            .flatMap(email -> memberRepository.findByEmail(email))
            .ifPresent(this::saveAuthentication);
        filterChain.doFilter(request, response);
      } else {
        log.info("Invalid Token");
        sendInvalidTokenResponse(response);
        log.info("checkAccessTokenAndAuthentication() end");
      }
    } else {
      log.info("Missing Token");
      sendMissingTokenResponse(response);
      log.info("checkAccessTokenAndAuthentication() end");
    }

  }

  /**
   * [인증 허가 메소드]
   * 파라미터의 유저 : 우리가 만든 회원 객체 / 빌더의 유저 : UserDetails의 User 객체
   *
   * new UsernamePasswordAuthenticationToken()로 인증 객체인 Authentication 객체 생성
   * UsernamePasswordAuthenticationToken의 파라미터
   * 1. 위에서 만든 UserDetailsUser 객체 (유저 정보)
   * 2. credential(보통 비밀번호로, 인증 시에는 보통 null로 제거)
   * 3. Collection < ? extends GrantedAuthority>로,
   * UserDetails의 User 객체 안에 Set<GrantedAuthority> authorities이 있어서 getter로 호출한 후에,
   * new NullAuthoritiesMapper()로 GrantedAuthoritiesMapper 객체를 생성하고 mapAuthorities()에 담기
   *
   * SecurityContextHolder.getContext()로 SecurityContext를 꺼낸 후,
   * setAuthentication()을 이용하여 위에서 만든 Authentication 객체에 대한 인증 허가 처리
   */
  public void saveAuthentication(Member member) {
    String password = member.getPassword();
    if (password == null) { // 소셜 로그인 유저의 비밀번호 임의로 설정 하여 소셜 로그인 유저도 인증 되도록 설정
      password = "randomGeneratedPassword";
    }

    UserDetails userDetailsUser = UserPrincipal.builder()
        .email(member.getEmail())
        .memberId(Long.valueOf(member.getId()))
        .password(password)
        .authorities(List.of(new SimpleGrantedAuthority(member.getRole().name())))
        .build();

    Authentication authentication =
        new UsernamePasswordAuthenticationToken(userDetailsUser, null,
            authoritiesMapper.mapAuthorities(userDetailsUser.getAuthorities()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  /**
   * 토큰이 유효하지 않을 때 호출되는 메서드
   */
  private void sendInvalidTokenResponse(HttpServletResponse response) throws IOException {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType(RESPONSE_CONTENT_TYPE_JSON);
    response.getWriter().write(INVALID_TOKEN_RESPONSE);
  }

  /**
   * 토큰이 없을 때 호출되는 메서드
   */
  private void sendMissingTokenResponse(HttpServletResponse response) throws IOException {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType(RESPONSE_CONTENT_TYPE_JSON);
    response.getWriter().write(MISSING_TOKEN_RESPONSE);
  }

  /**
   * 해당하는 요청의 경우 JWT필터를 로직 수행 없이 그냥 통과함.
   * 해당되는 요청의 경우 true, 아니면 false를 리턴함.
   */
  private boolean shouldSkipFilter(HttpServletRequest request) {
    return Arrays.asList("/login/**", "/index.html", "/oauth2/**", "/api/**"
            , ""
            ).stream()
        .anyMatch(pattern -> new AntPathMatcher().match(pattern, request.getRequestURI()));
  }
}