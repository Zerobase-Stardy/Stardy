package com.github.backend.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AdminErrorCode {
    EXIST_SAME_ADMIN_ID("동일한 ADMIN ID가 존재합니다.");

    private final String description;
}
