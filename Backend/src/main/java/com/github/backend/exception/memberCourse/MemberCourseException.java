package com.github.backend.exception.memberCourse;

import com.github.backend.exception.memberCourse.code.MemberCourseErrorCode;
import lombok.Getter;

@Getter
public class MemberCourseException extends RuntimeException {

	private MemberCourseErrorCode errorCode;
	private String errorMessage;

	public MemberCourseException(MemberCourseErrorCode errorCode) {
		super(errorCode.getDescription());
		this.errorCode = errorCode;
		this.errorMessage = errorCode.getDescription();
	}
}
