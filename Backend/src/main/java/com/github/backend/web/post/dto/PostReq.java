package com.github.backend.web.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;

public class PostReq {

    @Getter
    @Setter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class Request{

        @NotNull
        private String title;

        @NotNull
        private String content;

        @NotNull
        private String boardKind;
    }
}
