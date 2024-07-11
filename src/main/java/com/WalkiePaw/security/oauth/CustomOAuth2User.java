package com.WalkiePaw.security.oauth;

import com.WalkiePaw.domain.member.entity.Role;
import java.util.Collection;
import java.util.Map;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

/**
 * DefaultOAuth2User를 상속하고, email과 role 필드를 추가로 가진다.
 * 추가적으로 memberId도 가짐.
 */
@Getter
public class CustomOAuth2User extends DefaultOAuth2User {

  private String email;
  private Long memberId;
  private Role role;

  /**
   * Constructs a {@code DefaultOAuth2User} using the provided parameters.
   *
   * @param authorities      the authorities granted to the user
   * @param attributes       the attributes about the user
   * @param nameAttributeKey the key used to access the user's "name" from
   *                         {@link #getAttributes()}
   */
  public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities,
      Map<String, Object> attributes, String nameAttributeKey,
      String email, Long memberId, Role role) {
    super(authorities, attributes, nameAttributeKey);
    this.email = email;
    this.memberId = memberId;
    this.role = role;
  }
}