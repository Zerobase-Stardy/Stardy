package com.github.backend.dto.post;

import com.github.backend.persist.post.repository.querydsl.condition.TitleSearchCondition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SearchTitle {

    private Long id;
    private String title;


    public TitleSearchCondition toCondition() {
        return TitleSearchCondition.builder()
                .id(getId())
                .title(getTitle())
                .build();
    }
}

