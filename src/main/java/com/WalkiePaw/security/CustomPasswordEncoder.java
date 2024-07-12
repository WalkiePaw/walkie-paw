package com.WalkiePaw.security;

import com.WalkiePaw.domain.member.entity.Member;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class CustomPasswordEncoder extends BCryptPasswordEncoder {

  public void encodePassword(Member member) {
    member.updatePasswd(encode(member.getPassword()));
  }
}