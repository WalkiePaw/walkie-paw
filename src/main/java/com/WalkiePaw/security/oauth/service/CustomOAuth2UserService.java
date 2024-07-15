package com.WalkiePaw.security.oauth.service;

import com.WalkiePaw.domain.member.Repository.MemberRepository;
import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.domain.member.entity.SocialType;
import com.WalkiePaw.security.oauth.CustomOAuth2User;
import com.WalkiePaw.security.oauth.OAuthAttributes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

  private final MemberRepository memberRepository;

  private static final String NAVER = "naver";
  private static final String KAKAO = "kakao";

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    log.info("CustomOAuth2UserService.loadUser() 실행 - OAuth2 로그인 요청 진입");

    /**
     * DefaultOAuth2UserService 객체를 생성하여, loadUser(userRequest)를 통해 DefaultOAuth2User 객체를 생성 후 반환
     * DefaultOAuth2UserService의 loadUser()는 소셜 로그인 API의 사용자 정보 제공 URI로 요청을 보내서
     * 사용자 정보를 얻은 후, 이를 통해 DefaultOAuth2User 객체를 생성 후 반환한다.
     * 결과적으로, OAuth2User는 OAuth 서비스에서 가져온 유저 정보를 담고 있는 유저
     *
     * 각 서비스의 UserInfo의 attribute가 꽂혀있는듯?
     * OAuth2UserRequest에는 clientRegistration, AccessToken, 등이 들어있음.
     * clientRegistration에는 클라이언트id, 클라이언트시크릿, 인증서버 url, 스코프 등 소셜 로그인 api에 요청 할 때 필요한 정보가 들어있음.
     */
    OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
    OAuth2User oAuth2User = delegate.loadUser(userRequest);

    /**
     * userRequest에서 registrationId 추출 후 registrationId으로 SocialType 저장
     * http://localhost:8080/oauth2/authorization/kakao에서 kakao가 registrationId
     * userNameAttributeName은 이후에 nameAttributeKey로 설정된다.
     */
    String registrationId = userRequest.getClientRegistration().getRegistrationId();
    SocialType socialType = getSocialType(registrationId);
    String userNameAttributeName = userRequest.getClientRegistration()
        .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName(); // OAuth2 로그인 시 키(PK)가 되는 값
    Map<String, Object> attributes = oAuth2User.getAttributes(); // 소셜 로그인에서 API가 제공하는 userInfo의 Json 값(유저 정보들)

    // socialType에 따라 유저 정보를 통해 OAuthAttributes 객체 생성
    OAuthAttributes extractAttributes = OAuthAttributes.of(socialType, userNameAttributeName, attributes);

    Member createdMember = getMember(extractAttributes, socialType); // getUser() 메소드로 User 객체 생성 후 반환

    // DefaultOAuth2User를 구현한 CustomOAuth2User 객체를 생성해서 반환
    return new CustomOAuth2User(
        Collections.singleton(new SimpleGrantedAuthority(createdMember.getRole().getKey())),
        attributes,
        extractAttributes.getNameAttributeKey(),
        createdMember.getEmail(),
        createdMember.getId(),
        createdMember.getNickname(),
        createdMember.getName(),
        createdMember.getPhoto(),
        createdMember.getRole()
    );
  }

  private SocialType getSocialType(String registrationId) {
    if(NAVER.equals(registrationId)) {
      return SocialType.NAVER;
    }
    if(KAKAO.equals(registrationId)) {
      return SocialType.KAKAO;
    }
    return SocialType.GOOGLE;
  }

  /**
   * SocialType과 attributes에 들어있는 소셜 로그인의 식별값 id를 통해 회원을 찾아 반환하는 메소드
   * 만약 찾은 회원이 있다면, 그대로 반환하고 없다면 saveUser()를 호출하여 회원을 저장한다.
   */
  private Member getMember(OAuthAttributes attributes, SocialType socialType) {
    Member findMember = memberRepository.findBySocialTypeAndSocialId(socialType,
        attributes.getOauth2UserInfo().getId()).orElse(null);

    if(findMember == null) {
      return saveMember(attributes, socialType);
    }
    return findMember;
  }

  /**
   * OAuthAttributes의 toEntity() 메소드를 통해 빌더로 User 객체 생성 후 반환
   * 생성된 User 객체를 DB에 저장 : socialType, socialId, email, role 값만 있는 상태
   */
  private Member saveMember(OAuthAttributes attributes, SocialType socialType) {
    Member createdMember = attributes.toEntity(socialType, attributes.getOauth2UserInfo());
    return memberRepository.save(createdMember);
  }
}