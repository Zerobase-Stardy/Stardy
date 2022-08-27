package com.github.backend.persist.member.querydsl.condition;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberSearchCondition {
    String email;
    String nickname;
    Long point;

    public MemberSearchCondition(String email, String nickname, Long point){
        this.email = email;
        this.nickname = nickname;
        this.point = point;
    }
}
