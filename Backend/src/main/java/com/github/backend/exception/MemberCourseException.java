package com.github.backend.exception;

import com.github.backend.type.MemberCourseErrorCode;
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
