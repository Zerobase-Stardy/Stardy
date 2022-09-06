package com.github.backend.exception.admin.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AdminErrorCode {
    EXIST_SAME_ADMIN_ID("동일한 ADMIN ID가 존재합니다."),
    NOT_EXIST_ADMIN_ID("ADMIN ID와 일치하는 사용자 계정이 존재하지 않습니다."),
    PASSWORD_IS_WRONG("패스워드가 틀립니다.");

    private final String description;
}
