package com.github.backend.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

public class LoginAdmin {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class Request{
        @NotNull
        private String adminId;

        @NotNull
        private String password;

    }
}
