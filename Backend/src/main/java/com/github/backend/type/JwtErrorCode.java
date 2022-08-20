package com.github.backend.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtErrorCode {
	INVALID_JWT_SIGNATURE("사용할 수 없는 토큰입니다."),
	EXPIRED_JWT("토큰의 유효기간이 만료되었습니다.");

	private final String description;
}
