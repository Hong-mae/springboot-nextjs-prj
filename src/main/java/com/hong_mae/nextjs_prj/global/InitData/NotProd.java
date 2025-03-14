package com.hong_mae.nextjs_prj.global.InitData;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hong_mae.nextjs_prj.domain.article.service.ArticleService;
import com.hong_mae.nextjs_prj.domain.member.entity.Member;
import com.hong_mae.nextjs_prj.domain.member.service.MemberService;

@Configuration
@Profile({ "dev", "test" })
public class NotProd {
    @Bean
    CommandLineRunner initData(ArticleService articleService, MemberService memberService, PasswordEncoder pEncoder) {
        String pw = pEncoder.encode("test");

        return args -> {
            // 회원 3명 추가
            Member admin = memberService.join("admin", pw, "admin@test.com");
            Member user1 = memberService.join("user1", pw, "user1@test.com");
            Member user2 = memberService.join("user2", pw, "user2@test.com");
            // 가입 회원 작성자 처리
            articleService.create(user1, "제목 1", "내용 1");
            articleService.create(user2, "제목 2", "내용 2");
            articleService.create(user1, "제목 3", "내용 3");
            articleService.create(user2, "제목 4", "내용 4");
            articleService.create(admin, "제목 5", "내용 5");
        };
    }
}
