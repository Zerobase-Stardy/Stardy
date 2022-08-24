package com.github.backend.dto.member;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModifyMember {
    private String email;
    private String nickName;
}
