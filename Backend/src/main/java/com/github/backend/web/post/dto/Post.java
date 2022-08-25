package com.github.backend.web.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

public class Post {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class Request{
        @NotNull
        private String email;
        @NotNull
        private String title;
        @NotNull
        private String content;
        @NotNull
        private String boardKind;




    }
}
