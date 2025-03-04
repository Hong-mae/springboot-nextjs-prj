package com.hong_mae.nextjs_prj.dto;

import com.hong_mae.nextjs_prj.domain.Article;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArticleModifyResponse {
    private final Article article;
}
