package com.github.backend.exception.myCourse;

import com.github.backend.exception.myCourse.code.MemberCourseErrorCode;
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
