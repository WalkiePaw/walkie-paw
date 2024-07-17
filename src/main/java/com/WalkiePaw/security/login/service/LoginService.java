package com.WalkiePaw.security.login.service;

import com.WalkiePaw.domain.member.Repository.MemberRepository;
import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

  private final MemberRepository memberRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("해당 이메일이 존재하지 않습니다."));

    return UserPrincipal.builder()
        .email(member.getEmail())
        .memberId(member.getId())
        .nickname(member.getNickname())
        .photoUrl(member.getPhoto())
        .password(member.getPassword())
        .authorities(List.of(new SimpleGrantedAuthority(member.getRole().name())))
        .build();
  }
}