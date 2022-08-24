package com.github.backend.dto.gamer;

import lombok.*;

import javax.validation.constraints.NotNull;

public class RegisterGamer {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request{

        @NotNull
        private String name;

        @NotNull
        private String race;

        @NotNull
        private String nickname;

        @NotNull
        private String introduce;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response{
        private String name;
        private String race;
        private String nickname;
        private String introduce;

    }
}
