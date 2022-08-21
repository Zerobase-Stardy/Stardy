package com.github.backend.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OAuthErrorCode {
	NOT_SUPPORTED_PROVIDER("지원하지 않는 제공자입니다.");

	private final String description;
}
