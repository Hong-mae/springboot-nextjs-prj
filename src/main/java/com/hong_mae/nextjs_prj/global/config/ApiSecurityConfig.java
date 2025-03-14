package com.hong_mae.nextjs_prj.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.hong_mae.nextjs_prj.global.config.filter.JwtAuthorizationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ApiSecurityConfig {
    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    @Bean
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/**")
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(HttpMethod.POST, "/api/v1/members/login").permitAll()
                        .requestMatchers("/api/*/articles").permitAll() // 전체 글, 모든 유저 접근
                        .requestMatchers("/api/*/articles/*").permitAll() // 상세 글, 모든 유저 접근
                        .anyRequest().authenticated()) // 나머지는 인증/인가 처리된 사용자만 가능
                .cors(cors -> cors.disable()) // cors 설정, 타 도메인에서 api 호출 가능
                .csrf(csrf -> csrf.disable()) // csrf 끄기
                .httpBasic(httpBasic -> httpBasic.disable()) // httpBasic 로그인 끄기
                .formLogin(formLogin -> formLogin.disable()) // form 로그인 끄기
                .sessionManagement(
                        sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션
                                                                                                                       // 끄기
                .addFilterBefore(
                        jwtAuthorizationFilter, // access token 을 통한 로그인 처리
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
