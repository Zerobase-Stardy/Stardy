package com.github.backend.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CourseErrorCode {
    NOT_EXIST_GAMER("해당하는 게이머가 존재하지 않습니다."),
    EXIST_SAME_TITLE("중복된 강의 이름이 존재합니다."),
    NOT_EXIST_COURSE("해당하는 강의가 존재하지 않습니다.");


    private final String description;
}
