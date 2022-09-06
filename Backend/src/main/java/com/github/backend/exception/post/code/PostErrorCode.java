package com.github.backend.exception.post.code;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PostErrorCode {
    MEMBER_NOT_EXISTS("존재하지 않는 회원입니다."),
    POST_NOT_EXISTS("게시글이 존재하지 않습니다."),

    POST_NOT_EQ_MEMBER("게시글의 작성자가 아닙니다.");
    private final String description;
}
