package com.github.backend.exception.attendance.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AttendanceErrorCode {
	ALREADY_CHECK_ATTENDANCE("이미 출석체크를 완료하였습니다.");
	private final String description;
}
