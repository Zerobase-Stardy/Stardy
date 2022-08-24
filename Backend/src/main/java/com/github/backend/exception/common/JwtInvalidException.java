package com.github.backend.exception.common;

import com.github.backend.exception.common.code.JwtErrorCode;
import org.springframework.security.core.AuthenticationException;

public class JwtInvalidException extends AuthenticationException {

	private JwtErrorCode errorCode;
	private String errorMessage;

	public JwtInvalidException(JwtErrorCode errorCode){
		super(errorCode.getDescription());
		this.errorCode = errorCode;
		this.errorMessage = errorCode.getDescription();
	}

	public JwtInvalidException(JwtErrorCode errorCode,Throwable cause) {
		super(errorCode.getDescription(),cause);
		this.errorCode = errorCode;
		this.errorMessage = errorCode.getDescription();
	}
}
