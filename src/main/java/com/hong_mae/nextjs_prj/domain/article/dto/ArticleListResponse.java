package com.hong_mae.nextjs_prj.domain.article.dto;

import java.util.List;

import com.hong_mae.nextjs_prj.domain.article.Article;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArticleListResponse {
    private final List<Article> articles;
    private final List<Long> ids;
}
