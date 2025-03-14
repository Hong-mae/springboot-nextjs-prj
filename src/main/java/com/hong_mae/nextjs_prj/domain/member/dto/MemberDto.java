package com.hong_mae.nextjs_prj.domain.member.dto;

import java.time.LocalDateTime;

import com.hong_mae.nextjs_prj.domain.member.entity.Member;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberDto {
    private long id;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public MemberDto(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
        this.createdAt = member.getCreatedAt();
        this.modifiedAt = member.getModifiedAt();
    }
}
