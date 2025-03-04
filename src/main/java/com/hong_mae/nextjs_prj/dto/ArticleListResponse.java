package com.hong_mae.nextjs_prj.dto;

import java.util.List;

import com.hong_mae.nextjs_prj.domain.Article;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArticleListResponse {
    private final List<Article> articles;
}
