package com.hong_mae.nextjs_prj.global.config.filter;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.http.ResponseCookie;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hong_mae.nextjs_prj.domain.member.service.MemberService;
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
    private final HttpServletRequest req;
    private final MemberService memberService;
    private final HttpServletResponse resp;

    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getRequestURI().equals("/api/v1/members/login") ||
                request.getRequestURI().equals("/api/v1/members/logout")) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = _getCookie("accessToken");
        // accessToken 검증, refreshToken 생성
        if (!accessToken.isBlank()) {
            // 토큰 유효기간 검증
            if (!memberService.validateToken(accessToken)) {
                String refreshToken = _getCookie("refreshToken");

                ReturnData<String> rData = memberService.refreshAccessToken(refreshToken);

                _addHeader("accessToken", rData.getData());
            }
            // 정보 가져오기
            SecurityUser user = memberService.getUserFromAccessToken(accessToken);
            // 인가 처리
            SecurityContextHolder.getContext().setAuthentication(user.genAuthentication());
        }

        filterChain.doFilter(request, response);
    }

    private String _getCookie(String name) {
        Cookie[] cookies = req.getCookies();

        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(name))
                .findFirst()
                .map(Cookie::getValue)
                .orElse("");
    }

    private void _addHeader(String name, String token) {
        ResponseCookie cookie = ResponseCookie.from(name,
                token)
                .path("/")
                .sameSite("None")
                .secure(true)
                .httpOnly(true)
                .build();

        resp.addHeader("Set-Cookie", cookie.toString());
    }
}
