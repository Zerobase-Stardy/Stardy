package com.github.backend.model.dto;

import com.github.backend.model.constants.MemberStatus;
import lombok.*;

public class MemberLogout {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response{
        private int status;
        private String email;
        private String nickName;
    }
}
