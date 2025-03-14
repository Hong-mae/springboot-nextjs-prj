package com.hong_mae.nextjs_prj.domain.member.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.hong_mae.nextjs_prj.domain.member.dto.AuthAndMakeTokensResponse;
import com.hong_mae.nextjs_prj.domain.member.entity.Member;
import com.hong_mae.nextjs_prj.domain.member.repository.MemberRepository;
import com.hong_mae.nextjs_prj.global.util.ReturnData;
import com.hong_mae.nextjs_prj.global.util.SecurityUser;
import com.hong_mae.nextjs_prj.global.util.jwt.JwtProvider;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    public Member join(String username, String password, String email) {
        Member member = Member.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();

        this.memberRepository.save(member);

        return member;
    }

    @Transactional
    public ReturnData<AuthAndMakeTokensResponse> authAndMakeTokens(String username, String password) {
        Member member = this.memberRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다."));

        // 회원 데이터, 시간 설정 및 토큰 생성;
        String accessToken = jwtProvider.genToken(member, 60 * 60 * 5);

        return ReturnData.of("S-6", "로그인 성공", new AuthAndMakeTokensResponse(member, accessToken));
    }

    public SecurityUser getUserFromAccessToken(String accessToken) {
        System.out.println(accessToken);
        Map<String, Object> payload = jwtProvider.getClaims(accessToken);

        long id = (int) payload.get("id");
        String username = (String) payload.get("username");
        List<GrantedAuthority> authorities = new ArrayList<>();

        return new SecurityUser(id, username, "", authorities);
    }
}
