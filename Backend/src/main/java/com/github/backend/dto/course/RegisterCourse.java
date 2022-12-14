package com.github.backend.dto.course;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class RegisterCourse {

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

}
