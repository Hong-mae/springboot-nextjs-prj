package com.hong_mae.nextjs_prj.domain.member.dto;

import com.hong_mae.nextjs_prj.domain.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthAndMakeTokensResponse {
    private Member member;
    private String accessToken;
    private String refreshToken;
}
