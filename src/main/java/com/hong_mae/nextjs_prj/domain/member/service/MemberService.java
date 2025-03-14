package com.hong_mae.nextjs_prj.domain.member.service;

import org.springframework.stereotype.Service;

import com.hong_mae.nextjs_prj.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
}
