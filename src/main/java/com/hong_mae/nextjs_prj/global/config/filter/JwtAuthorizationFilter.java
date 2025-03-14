package com.hong_mae.nextjs_prj.global.config.filter;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.http.ResponseCookie;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hong_mae.nextjs_prj.domain.member.service.MemberService;
import com.hong_mae.nextjs_prj.global.util.Request;
import com.hong_mae.nextjs_prj.global.util.ReturnData;
import com.hong_mae.nextjs_prj.global.util.SecurityUser;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final MemberService memberService;
    private final Request rq;

    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getRequestURI().equals("/api/v1/members/login") ||
                request.getRequestURI().equals("/api/v1/members/logout")) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = rq.getCookie("accessToken");
        // accessToken 검증, refreshToken 생성
        if (!accessToken.isBlank()) {
            // 토큰 유효기간 검증
            if (!memberService.validateToken(accessToken)) {
                String refreshToken = rq.getCookie("refreshToken");

                ReturnData<String> rData = memberService.refreshAccessToken(refreshToken);

                rq.setCookie("accessToken", rData.getData());
            }
            // 정보 가져오기
            SecurityUser user = memberService.getUserFromAccessToken(accessToken);
            // 로그인 처리
            rq.setLogin(user);
        }

        filterChain.doFilter(request, response);
    }

}
