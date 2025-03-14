package com.hong_mae.nextjs_prj.global.config.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getRequestURI().equals("/api/v1/members/login") ||
                request.getRequestURI().equals("/api/v1/members/logout")) {
            filterChain.doFilter(request, response);
        }

        String accessToken = "";
        // accessToken 검증, refreshToken 생성
        if (!accessToken.isBlank()) {

        }

        filterChain.doFilter(request, response);
    }
}
