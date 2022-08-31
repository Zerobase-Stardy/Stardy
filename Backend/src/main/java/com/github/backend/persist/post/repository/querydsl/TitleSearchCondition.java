package com.github.backend.persist.post.repository.querydsl;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class TitleSearchCondition {
    private String title;
    private String boardKind;

    public TitleSearchCondition(String title, String boardKind){
        this.title = title;
        this.boardKind = boardKind;

    }

}
