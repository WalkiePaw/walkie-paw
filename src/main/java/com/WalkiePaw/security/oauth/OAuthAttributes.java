package com.WalkiePaw.security.oauth;

import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.domain.member.entity.Role;
import com.WalkiePaw.domain.member.entity.SocialType;
import com.WalkiePaw.security.oauth.userInfo.GoogleOAuth2UserInfo;
import com.WalkiePaw.security.oauth.userInfo.KakaoOAuth2UserInfo;
import com.WalkiePaw.security.oauth.userInfo.NaverOAuth2UserInfo;
import com.WalkiePaw.security.oauth.userInfo.OAuth2UserInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

/**
 * 각 소셜에서 받아오는 데이터가 다르므로
 * 소셜별로 데이터를 받는 데이터를 분기 처리하는 DTO 클래스
 * 소별 타입별 로그인 유저 정보 UserInfo 안에 사용자 정보는 attribute로 존재함.
 */
@Getter
public class OAuthAttributes {

  private String nameAttributeKey; // OAuth2 로그인 진행 시 키가 되는 필드 값, PK와 같은 의미
  private OAuth2UserInfo oauth2UserInfo; // 소셜 타입별 로그인 유저 정보(닉네임, 이메일, 프로필 사진 등등)

  @Builder
  private OAuthAttributes(String nameAttributeKey, OAuth2UserInfo oauth2UserInfo) {
    this.nameAttributeKey = nameAttributeKey;
    this.oauth2UserInfo = oauth2UserInfo;
  }

  /**
   * SocialType에 맞는 메소드 호출하여 OAuthAttributes 객체 반환
   * 파라미터 : userNameAttributeName -> OAuth2 로그인 시 키(PK)가 되는 값 / attributes : OAuth 서비스의 유저 정보들
   * 소셜별 of 메소드(ofGoogle, ofKaKao, ofNaver)들은 각각 소셜 로그인 API에서 제공하는
   * 회원의 식별값(id), attributes, nameAttributeKey를 저장 후 build
   */
  public static OAuthAttributes of(SocialType socialType,
      String userNameAttributeName, Map<String, Object> attributes) {

    if (socialType == SocialType.NAVER) {
      return ofNaver(userNameAttributeName, attributes);
    }
    if (socialType == SocialType.KAKAO) {
      return ofKakao(userNameAttributeName, attributes);
    }
    return ofGoogle(userNameAttributeName, attributes);
  }

  private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
    return OAuthAttributes.builder()
        .nameAttributeKey(userNameAttributeName)
        .oauth2UserInfo(new KakaoOAuth2UserInfo(attributes))
        .build();
  }

  public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
    return OAuthAttributes.builder()
        .nameAttributeKey(userNameAttributeName)
        .oauth2UserInfo(new GoogleOAuth2UserInfo(attributes))
        .build();
  }

  public static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
    return OAuthAttributes.builder()
        .nameAttributeKey(userNameAttributeName)
        .oauth2UserInfo(new NaverOAuth2UserInfo(attributes))
        .build();
  }

  /**
   * of메소드로 OAuthAttributes 객체가 생성되어, 유저 정보들이 담긴 OAuth2UserInfo가 소셜 타입별로 주입된 상태
   * OAuth2UserInfo에서 socialId(식별값), nickname, imageUrl, email을 가져와서 build
   * role은 GUEST로 설정
   */
  public Member toEntity(SocialType socialType, OAuth2UserInfo oauth2UserInfo) {
    Member member = Member.builder()
        .socialType(socialType)
        .socialId(oauth2UserInfo.getId())
        .email(oauth2UserInfo.getEmail())
        .nickname(oauth2UserInfo.getNickname())
        .name(oauth2UserInfo.getName())
        .photo(oauth2UserInfo.getImageUrl())
        .build();
    member.setRoleToGuest();
    return member;
  }
}