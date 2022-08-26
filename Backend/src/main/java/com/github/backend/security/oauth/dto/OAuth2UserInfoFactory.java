package com.github.backend.security.oauth.dto;

import com.github.backend.persist.common.type.AuthType;
import java.util.Map;

public class OAuth2UserInfoFactory {
	public static OAuth2UserInfo getOAuth2UserInfo(AuthType authType, Map<String, Object> attributes) {
		switch (authType) {
			case KAKAO: return new KakaoOAuth2UserInfo(attributes);
			default: throw new IllegalArgumentException("Invalid Provider Type.");
		}
	}

}
