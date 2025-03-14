package com.hong_mae.nextjs_prj.domain.article.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hong_mae.nextjs_prj.domain.article.Article;
import com.hong_mae.nextjs_prj.domain.article.dto.ArticleListResponse;
import com.hong_mae.nextjs_prj.domain.article.dto.ArticleModifyRequest;
import com.hong_mae.nextjs_prj.domain.article.dto.ArticleModifyResponse;
import com.hong_mae.nextjs_prj.domain.article.dto.ArticleRemoveResponse;
import com.hong_mae.nextjs_prj.domain.article.dto.ArticleResponse;
import com.hong_mae.nextjs_prj.domain.article.dto.ArticleWriteRequest;
import com.hong_mae.nextjs_prj.domain.article.dto.ArticleWriteResponse;
import com.hong_mae.nextjs_prj.domain.article.service.ArticleService;
import com.hong_mae.nextjs_prj.global.ReturnData.ReturnData;

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

        List<Long> ids = articles.stream()
                .map(Article::getId)
                .collect(Collectors.toList());

        return ReturnData.of("S-1", "조회 성공", new ArticleListResponse(articles, ids));
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
            return ReturnData.of(writeRD.getResultCode(), writeRD.getMsg());

        return ReturnData.of(
                writeRD.getResultCode(),
                writeRD.getMsg(),
                new ArticleWriteResponse(writeRD.getData()));
    }

    @PatchMapping("/{id}")
    public ReturnData<ArticleModifyResponse> modify(@Valid @RequestBody ArticleModifyRequest amr,
            @PathVariable("id") Long id) {
        Optional<Article> find = this.articleService.findById(id);

        if (find.isEmpty())
            return ReturnData.of(
                    "F-1",
                    "%d번 게시물은 없습니다".formatted(id));

        // 회원 권한 체크 필요
        ReturnData<Article> mod = this.articleService.modify(find.get(), amr.getSubject(), amr.getContent());

        return ReturnData.of(mod.getResultCode(), mod.getMsg(), new ArticleModifyResponse(mod.getData()));
    }

    @DeleteMapping("/{id}")
    public ReturnData<ArticleRemoveResponse> remove(@PathVariable("id") Long id) {
        Optional<Article> find = this.articleService.findById(id);

        if (find.isEmpty())
            return ReturnData.of(
                    "F-1",
                    "%d번 게시물은 없습니다".formatted(id));

        ReturnData<Article> del = this.articleService.deleteById(id);

        return ReturnData.of(del.getResultCode(), del.getMsg(), new ArticleRemoveResponse(find.get()));
    }
}
