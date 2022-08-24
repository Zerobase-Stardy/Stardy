package com.github.backend.dto.common;

import static com.github.backend.persist.member.type.MemberStatus.*;
import static com.github.backend.persist.member.type.Role.*;
import static com.github.backend.exception.common.code.OAuthErrorCode.*;

import com.github.backend.exception.common.OAuthException;
import com.github.backend.persist.common.type.AuthType;

import com.github.backend.persist.member.Member;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OAuthAttributes {
	private Map<String, Object> attributes;
	private String nameAttributeKey;
	private AuthType authType;
	private String email;

	public static OAuthAttributes of(String registrationId, String usernameAttributeName,
		Map<String, Object> attributes) {
		switch (registrationId) {
			case "google":
				return ofGoogle(usernameAttributeName, attributes);
			case "kakao":
				return ofKakao("email", attributes);
			default:
				throw new OAuthException(NOT_SUPPORTED_PROVIDER);
		}
	}

	private static OAuthAttributes ofGoogle(String usernameAttributeName,
		Map<String, Object> attributes) {

		return OAuthAttributes.builder()
			.email((String) attributes.get("email"))
			.attributes(attributes)
			.authType(AuthType.GOOGLE)
			.nameAttributeKey(usernameAttributeName)
			.build();
	}

	private static OAuthAttributes ofKakao(String userNameAttributeName,
		Map<String, Object> attributes) {
		Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
		Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

		return OAuthAttributes.builder()
			.email((String) kakaoAccount.get("email"))
			.attributes(attributes)
			.authType(AuthType.KAKAO)
			.nameAttributeKey(userNameAttributeName)
			.build();
	}

	public Member toMember() {
		return Member.builder()
			.email(email)
			.authType(authType)
			.role(ROLE_USER)
			.status(WAIT)
			.point(0)
			.build();

	}



}
