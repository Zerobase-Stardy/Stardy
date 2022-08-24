package com.github.backend.exception.memberCourse.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberCourseErrorCode {
	MEMBER_NOT_EXISTS("존재하지 않는 회원입니다."),
	COURSE_NOT_EXISTS("존재하지 않는 강의입니다."),
	ALREADY_MEMBER_COURSE_EXISTS("이미 수강중인 강의입니다."),
	NOT_ENOUGH_POINT("포인트가 부족합니다."),
	MEMBER_COURSE_NOT_EXISTS("구매 내역에 존재하지 않는 강의입니다.");

	private final String description;
}
