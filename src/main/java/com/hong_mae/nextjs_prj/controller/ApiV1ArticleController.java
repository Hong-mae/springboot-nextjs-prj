package com.hong_mae.nextjs_prj.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hong_mae.nextjs_prj.domain.Article;
import com.hong_mae.nextjs_prj.service.ArticleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/articles")
public class ApiV1ArticleController {
    private final ArticleService articleService;

    @GetMapping()
    public List<Article> getArticles() {
        List<Article> articles = this.articleService.getList();

        // articles.add(new Article((1L)));
        // articles.add(new Article(2L));
        // articles.add(new Article(3L));

        return articles;
    }

    @GetMapping("/{id}")
    public Article getArticle(@PathVariable("id") Long id) {
        Article article = this.articleService.getArticle(id);
        return new Article();
    }

}
