package com.github.backend.exception.common;

import com.github.backend.exception.common.code.OAuthErrorCode;
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
