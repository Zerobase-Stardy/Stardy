package com.github.backend.security.oauth.dto;

import com.github.backend.persist.common.type.AuthType;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class OAuth2UserInfo {
	protected Map<String, Object> attributes;

	public OAuth2UserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public abstract String getName();

	public abstract String getEmail();

	public abstract AuthType getAuthType();

}
