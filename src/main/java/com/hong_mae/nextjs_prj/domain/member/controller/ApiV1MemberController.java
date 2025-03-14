package com.hong_mae.nextjs_prj.domain.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hong_mae.nextjs_prj.domain.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class ApiV1MemberController {
    private final MemberService memberService;

    @GetMapping("")
    public String memberTest() {
        return "member test";
    }
}
