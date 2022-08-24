package com.github.backend.exception.gamer;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GamerErrorCode {
    EXIST_SAME_GAMER_NICKNAME("동일한 GAMER가 이미 존재합니다."),
    NOT_EXIST_GAMER("해당하는 GAMER가 존재하지 않습니다.");

    private final String description;
}
