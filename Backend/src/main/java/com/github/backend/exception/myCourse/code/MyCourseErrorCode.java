package com.github.backend.exception.myCourse.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MyCourseErrorCode {
	ALREADY_MY_COURSE_EXISTS("이미 수강중인 강의입니다."),
	MY_COURSE_NOT_EXISTS("구매 내역에 존재하지 않는 강의입니다.");

	private final String description;
}
