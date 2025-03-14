package com.hong_mae.nextjs_prj.domain.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hong_mae.nextjs_prj.domain.member.dto.AuthAndMakeTokensResponse;
import com.hong_mae.nextjs_prj.domain.member.dto.LoginRequest;
import com.hong_mae.nextjs_prj.domain.member.dto.LoginResponse;
import com.hong_mae.nextjs_prj.domain.member.dto.MemberDto;
import com.hong_mae.nextjs_prj.domain.member.service.MemberService;
import com.hong_mae.nextjs_prj.global.util.Request;
import com.hong_mae.nextjs_prj.global.util.ReturnData;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class ApiV1MemberController {
    private final MemberService memberService;
    private final Request rq;

    @PostMapping("/login")
    public ReturnData<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        // username, password => accessToken 발급
        ReturnData<AuthAndMakeTokensResponse> authData = this.memberService
                .authAndMakeTokens(loginRequest.getUsername(), loginRequest.getPassword());

        // 쿠카에 access, refresh token 넣기
        rq.setCookie("accessToken", authData.getData().getAccessToken());
        rq.setCookie("refreshToken", authData.getData().getRefreshToken());

        return ReturnData.of(authData.getResultCode(), authData.getMsg(),
                new LoginResponse(new MemberDto(authData.getData().getMember())));
    }

    @GetMapping("/me")
    public String me() {
        return "내 정보";
    }

    @PostMapping("/logout")
    public ReturnData<Void> logout() {
        rq.removeCookie("accessToken");
        rq.removeCookie("refreshToken");

        return ReturnData.of("S-8", "로그아웃 완료");
    }
}