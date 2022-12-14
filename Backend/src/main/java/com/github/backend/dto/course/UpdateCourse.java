package com.github.backend.dto.course;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class UpdateCourse {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class Request{

        @NotNull
        @Min(1)
        private Long gamerId;

        @NotNull
        private String title;

        @NotNull
        private String videoUrl;

        @NotNull
        private String thumbnailUrl;

        @NotNull
        private String comment;

        @NotNull
        private String level;

        @NotNull
        private String race;

        @NotNull
        private Long price;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response{
        private String gamerName;
        private String title;
        private String videoUrl;
        private String thumbnailUrl;
        private String comment;
        private String level;
        private String race;
        private Long price;
    }
}
