package com.github.backend.model.dto;

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
