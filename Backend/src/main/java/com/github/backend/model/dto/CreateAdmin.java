package com.github.backend.model.dto;

import com.github.backend.model.constants.Role;
import lombok.*;

import javax.validation.constraints.NotNull;

public class CreateAdmin {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request{
        @NotNull
        private String adminId;

        @NotNull
        private String password;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response{
        private String adminId;
        private String password;
        private Role role;
    }
}
