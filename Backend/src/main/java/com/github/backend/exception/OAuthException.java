package com.github.backend.exception;

import com.github.backend.type.OAuthErrorCode;
import com.github.backend.type.MemberCourseErrorCode;
import lombok.Getter;

@Getter
public class OAuthException extends RuntimeException {

	private OAuthErrorCode errorCode;
	private String errorMessage;

	public OAuthException(OAuthErrorCode errorCode) {
		super(errorCode.getDescription());
		this.errorCode = errorCode;
		this.errorMessage = errorCode.getDescription();
	}
}
