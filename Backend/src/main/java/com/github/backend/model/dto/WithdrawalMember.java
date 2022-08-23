package com.github.backend.model.dto;

import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WithdrawalMember {
        private String email;
        private String nickName;
        private String memberStatus;
}
