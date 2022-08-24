package com.github.backend.dto.gamer;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UpdateGamer {

    @Getter
    @Setter
    @Builder
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

}
