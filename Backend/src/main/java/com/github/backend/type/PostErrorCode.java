package com.github.backend.type;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PostErrorCode {
    MEMBER_NOT_EXISTS("존재하지 않는 회원입니다.");

    private final String description;
}
