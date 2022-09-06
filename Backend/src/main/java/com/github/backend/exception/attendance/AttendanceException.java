package com.github.backend.exception.attendance;

import com.github.backend.exception.attendance.code.AttendanceErrorCode;
import lombok.Getter;

@Getter
public class AttendanceException extends RuntimeException {

	private AttendanceErrorCode errorCode;
	private String errorMessage;

	public AttendanceException(AttendanceErrorCode errorCode) {
		super(errorCode.getDescription());
		this.errorCode = errorCode;
		this.errorMessage = errorCode.getDescription();
	}
}
