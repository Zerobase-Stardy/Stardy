package com.github.backend.dto.member;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberLogout {
    private String email;
    private String nickName;
}
