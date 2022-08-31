package com.github.backend.persist.common.type;


import com.github.backend.exception.common.OAuthException;
import com.github.backend.exception.common.code.OAuthErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AuthType {
	KAKAO("카카오톡"),
	GOOGLE("구글");

	private String korName;


	public static AuthType of(String authType) {
		try {
			return AuthType.valueOf(authType.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new OAuthException(OAuthErrorCode.INVALID_PROVIDER);
		}
	}
}
