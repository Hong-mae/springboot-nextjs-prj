package com.hong_mae.nextjs_prj.domain.article;

import com.hong_mae.nextjs_prj.global.BaseEntity.BaseEntity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Article extends BaseEntity {
    private String subject;
    private String content;

    public void update(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }
}
