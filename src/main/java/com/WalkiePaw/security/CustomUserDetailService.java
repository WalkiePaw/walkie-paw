package com.WalkiePaw.security;

import com.WalkiePaw.domain.member.entity.Member;
import com.WalkiePaw.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        Member member = memberService.findByEmail(username).orElseThrow();
        return UserPrincipal.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .authorities(List.of(new SimpleGrantedAuthority(member.getRole())))
                .build();
    }
}
