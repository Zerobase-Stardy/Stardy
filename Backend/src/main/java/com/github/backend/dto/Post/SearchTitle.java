package com.github.backend.dto.Post;

import com.github.backend.persist.post.repository.querydsl.TitleSearchCondition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SearchTitle {

    private String title;
    private String boardKind;



    public TitleSearchCondition toCondition() {
        return TitleSearchCondition.builder()
                .boardKind(getBoardKind())
                .title(getTitle())
                .build();
    }
}

