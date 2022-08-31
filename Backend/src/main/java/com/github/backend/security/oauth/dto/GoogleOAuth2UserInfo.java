package com.github.backend.security.oauth.dto;

import com.github.backend.persist.common.type.AuthType;
import java.util.Map;
import lombok.Getter;

@Getter
public class GoogleOAuth2UserInfo extends OAuth2UserInfo{

	private String name;
	private String email;
	private AuthType authType;


	public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
		super(attributes);
		this.email = (String)attributes.get("email");
		this.authType = AuthType.GOOGLE;
		this.name = (String) attributes.get("name");
	}


}
