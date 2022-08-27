package com.github.backend.dto.member;

import com.github.backend.persist.member.Member;
import lombok.Builder;
import lombok.Data;

public class MemberSearchOutputDto {

    @Builder
    @Data
    public static class Info {
        private Long id;
        private String email;
        private String nickname;
        private String status;
        private Long point;
        private String authType;
        private String role;

        public static MemberSearchOutputDto.Info of(Member member){
            return Info.builder()
                    .id(member.getId())
                    .email(member.getEmail())
                    .nickname(member.getNickname())
                    .status(member.getStatus().name())
                    .point(member.getPoint())
                    .authType(member.getAuthType().name())
                    .role(member.getRole().name())
                    .build();
        }
    }
}
