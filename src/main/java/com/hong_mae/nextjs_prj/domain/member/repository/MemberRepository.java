package com.hong_mae.nextjs_prj.domain.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hong_mae.nextjs_prj.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);

    Optional<Member> findByRefreshToken(String refreshToken);
}
