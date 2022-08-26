package com.github.backend.security.oauth.dto;

import com.github.backend.persist.common.type.AuthType;
import java.util.Map;
import lombok.Getter;

@Getter
public class KakaoOAuth2UserInfo extends OAuth2UserInfo{

	private String name;
	private String email;
	private AuthType authType;


	public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
		super(attributes);
		Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
		Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

		this.email = (String)kakaoAccount.get("email");
		this.authType = AuthType.KAKAO;
		this.name = (String) kakaoProfile.get("nickname");
	}


}
