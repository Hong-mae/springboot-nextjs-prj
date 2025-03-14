package com.hong_mae.nextjs_prj.domain.article.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ArticleWriteRequest {
    @NotBlank
    private String subject;
    @NotBlank
    private String content;
}
