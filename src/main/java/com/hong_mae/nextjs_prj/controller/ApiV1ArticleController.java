package com.hong_mae.nextjs_prj.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hong_mae.nextjs_prj.domain.Article;
import com.hong_mae.nextjs_prj.dto.ArticleListResponse;
import com.hong_mae.nextjs_prj.dto.ArticleResponse;
import com.hong_mae.nextjs_prj.dto.ArticleWriteRequest;
import com.hong_mae.nextjs_prj.dto.ArticleWriteResponse;
import com.hong_mae.nextjs_prj.global.ReturnData.ReturnData;
import com.hong_mae.nextjs_prj.service.ArticleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/articles")
public class ApiV1ArticleController {
    private final ArticleService articleService;

    @GetMapping()
    public ReturnData<ArticleListResponse> getArticles() {
        List<Article> articles = this.articleService.getList();

        return ReturnData.of("S-1", "조회 성공", new ArticleListResponse(articles));
    }

    @GetMapping("/{id}")
    public ReturnData<ArticleResponse> getArticle(@PathVariable("id") Long id) {
        return this.articleService.getArticle(id).map((article) -> ReturnData.of(
                "S-1",
                "조회 성공",
                new ArticleResponse(article))).orElseGet(() -> ReturnData.of(
                        "F-1",
                        "%d 번 게시물은 존재하지 않습니다.".formatted(id),
                        null));
    }

    @PostMapping()
    public ReturnData<ArticleWriteResponse> write(@Valid @RequestBody ArticleWriteRequest awr) {
        ReturnData<Article> writeRD = this.articleService.create(awr.getSubject(), awr.getContent());

        if (writeRD.isFailer())
            return (ReturnData) writeRD;

        return ReturnData.of(
                writeRD.getResultCode(),
                writeRD.getMsg(),
                new ArticleWriteResponse(writeRD.getData()));
    }
}
