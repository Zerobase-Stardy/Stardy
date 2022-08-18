package com.github.backend.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GamerErrorCode {
    EXIST_SAME_GAMER_NICKNAME("동일한 GAMER가 이미 존재합니다.");

    private final String description;
}
