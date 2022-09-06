package com.github.backend.dto.admin;

import com.github.backend.persist.member.type.Role;
import lombok.*;

import javax.validation.constraints.NotNull;

public class CreateAdmin {

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
