package com.hong_mae.nextjs_prj.domain.member.controller;

import org.springframework.http.ResponseCookie;
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
import com.hong_mae.nextjs_prj.global.ReturnData.ReturnData;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class ApiV1MemberController {
    private final MemberService memberService;

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse resp) {
        System.out.println("TESTESTESTESTEST");

        return "test";
        // username, password => accessToken 발급
        // ReturnData<AuthAndMakeTokensResponse> authData = this.memberService
        // .authAndMakeTokens(loginRequest.getUsername(), loginRequest.getPassword());

        // ResponseCookie cookie = ResponseCookie.from("accessToken",
        // authData.getData().getAccessToken())
        // .path("/")
        // .sameSite("None")
        // .secure(true)
        // .httpOnly(true)
        // .build();

        // resp.addHeader("Set-Cookie", cookie.toString());

        // return ReturnData.of(authData.getResultCode(), authData.getMsg(),
        // new LoginResponse(new MemberDto(authData.getData().getMember())));
    }
}