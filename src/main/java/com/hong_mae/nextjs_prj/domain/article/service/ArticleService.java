package com.hong_mae.nextjs_prj.domain.article.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hong_mae.nextjs_prj.domain.article.entity.Article;
import com.hong_mae.nextjs_prj.domain.article.repository.ArticleRepository;
import com.hong_mae.nextjs_prj.domain.member.entity.Member;
import com.hong_mae.nextjs_prj.global.ReturnData.ReturnData;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public List<Article> getList() {
        return this.articleRepository.findAll();
    }

    public Optional<Article> getArticle(Long id) {
        return this.articleRepository.findById(id);
    }

    @Transactional
    public ReturnData<Article> create(Member member, String sub, String con) {
        Article article = Article.builder()
                .author(member)
                .subject(sub)
                .content(con)
                .build();

        this.articleRepository.save(article);

        return ReturnData.of("S-2", "게시물이 등록 되었습니다.", article);
    }

    public Optional<Article> findById(Long id) {
        return this.articleRepository.findById(id);
    }

    public ReturnData<Article> modify(Article article, String sub, String con) {
        article.update(sub, con);
        this.articleRepository.save(article);

        return ReturnData.of(
                "S-4",
                "%d번 게시물이 수정 되었습니다.".formatted(article.getId()),
                article);
    }

    public ReturnData<Article> deleteById(Long id) {
        this.articleRepository.deleteById(id);

        return ReturnData.of(
                "S-5",
                "%d번 게시물이 삭제 되었습니다.".formatted(id));
    }
}
