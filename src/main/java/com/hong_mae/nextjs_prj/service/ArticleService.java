package com.hong_mae.nextjs_prj.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hong_mae.nextjs_prj.domain.Article;
import com.hong_mae.nextjs_prj.repository.ArticleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public List<Article> getList() {
        return this.articleRepository.findAll();
    }

    public Article getArticle(Long id) {
        Optional<Article> article = this.articleRepository.findById(id);

        if (article.isEmpty())
            return null;

        return article.get();
    }

    public void create(String sub, String con) {
        Article article = Article.builder()
                .subject(sub)
                .content(con)
                .build();

        this.articleRepository.save(article);
    }
}
