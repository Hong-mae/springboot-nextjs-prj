package com.hong_mae.nextjs_prj.domain.article.dto;

import com.hong_mae.nextjs_prj.domain.article.Article;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArticleResponse {
    private final Article article;
}
