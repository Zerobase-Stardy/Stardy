package com.github.backend.persist.post.repository.querydsl;

import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class TitleSearchCondition {
    private Long id;
    private String title;
    private String writer;

    public TitleSearchCondition(Long id, String title, String writer){
        this.title = title;
        this.id = id;
        this.writer = writer;
    }

}
