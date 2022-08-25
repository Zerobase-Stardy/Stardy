package com.github.backend.exception.myCourse;

import com.github.backend.exception.myCourse.code.MyCourseErrorCode;
import lombok.Getter;

@Getter
public class MyCourseException extends RuntimeException {

	private MyCourseErrorCode errorCode;
	private String errorMessage;

	public MyCourseException(MyCourseErrorCode errorCode) {
		super(errorCode.getDescription());
		this.errorCode = errorCode;
		this.errorMessage = errorCode.getDescription();
	}
}
