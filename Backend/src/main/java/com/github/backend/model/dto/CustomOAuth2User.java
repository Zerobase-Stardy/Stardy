package com.github.backend.model.dto;

import com.github.backend.model.dto.TokenMemberDto.MemberInfo;
import java.util.Collection;
import java.util.Map;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
public class CustomOAuth2User implements OAuth2User {

	private MemberInfo memberInfo;

	private Map<String,Object> attributes;

	private Collection<? extends GrantedAuthority> authorities;

	public CustomOAuth2User(MemberInfo memberInfo,
		Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes,
		String nameAttributeKey) {
		this.memberInfo = memberInfo;
		this.attributes = attributes;
		this.authorities = authorities;

	}

	@Override
	public Map<String, Object> getAttributes() {
		return this.attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getName() {
		return this.memberInfo.getEmail();
	}
}
