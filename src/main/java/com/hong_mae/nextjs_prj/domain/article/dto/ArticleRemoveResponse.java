package com.hong_mae.nextjs_prj.domain.article.dto;

import com.hong_mae.nextjs_prj.domain.article.entity.Article;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArticleRemoveResponse {
    private final Article article;
}
