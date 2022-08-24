package com.github.backend.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

public class MemberWithdrawalDto {

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class Request {

        @NotBlank
        private String nickname;
    }
}
