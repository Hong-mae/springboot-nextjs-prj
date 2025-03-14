package com.hong_mae.nextjs_prj.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hong_mae.nextjs_prj.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
