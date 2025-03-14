package com.hong_mae.nextjs_prj.domain.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hong_mae.nextjs_prj.domain.article.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}
