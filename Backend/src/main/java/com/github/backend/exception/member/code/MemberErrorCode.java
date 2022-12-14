package com.github.backend.exception.member.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberErrorCode {

    MEMBER_NOT_EXISTS("존재하지 않는 회원입니다."),
    MEMBER_NOT_ENOUGH_POINT("포인트가 부족합니다.");

    private final String description;
}
