package com.github.backend.dto.member;

import lombok.*;

import javax.validation.constraints.NotNull;

public class UpdateMemberNickname {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request{

        @NotNull
        private String nickname;
    }
}
