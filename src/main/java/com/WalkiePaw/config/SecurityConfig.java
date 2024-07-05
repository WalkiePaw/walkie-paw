package com.WalkiePaw.config;

import com.WalkiePaw.security.CustomUserDetailService;
import com.WalkiePaw.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailService customUserDetailService;

    @Bean
    public SecurityFilterChain applicationSecurity(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http
                .cors(AbstractHttpConfigurer::disable) // cors 비활성화
                .csrf(AbstractHttpConfigurer::disable) // csrf 비활성화
                .securityMatcher("/**") // 어떤 url에 security를 적용할지,
                .sessionManagement(sessionManagementConfigurer
                        -> sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable) // 로그인폼 비활성화
                .authorizeHttpRequests(registry -> registry // 요청에 대한 권한 설정 메서드
                        .requestMatchers("/secured").authenticated() // '/secured' 는 인증된 사용자에게만 허용
                        .requestMatchers("/api/v1/auth/login").permitAll() // '/api.../login' 는 모든 사용자게에 허용
                        .anyRequest().permitAll() // 모든 요청에 대해 허용
//                        .anyRequest().authenticated() // 다른 나머지 모든 요청에 대한 권한 설정, authenticated()는 인증된 사용자에게만 허용, 로그인해야만 접근 가능
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder())
                .and().build();
    }
}