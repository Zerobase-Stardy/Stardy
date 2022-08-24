package com.github.backend.dto.admin;

import com.github.backend.persist.member.type.Role;
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
