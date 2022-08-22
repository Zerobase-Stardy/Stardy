package com.github.backend.service.impl;


import com.github.backend.model.dto.CustomOAuth2User;
import com.github.backend.model.dto.OAuthAttributes;
import com.github.backend.model.dto.TokenMemberDto.MemberInfo;
import com.github.backend.persist.entity.Member;
import com.github.backend.persist.repository.MemberRepository;

import java.util.Collections;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final MemberRepository memberRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

		DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);

		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		String userNameAttributeName = userRequest.getClientRegistration()
				.getProviderDetails().getUserInfoEndpoint()
				.getUserNameAttributeName();

		OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName,
				oAuth2User.getAttributes());

		Optional<Member> optionalMember = memberRepository.findByEmail(attributes.getEmail());
		Member member;

		if (optionalMember.isPresent()) {
			member = optionalMember.get();
		} else {
			member = memberRepository.save(attributes.toMember());
		}

		MemberInfo memberInfo = MemberInfo.builder()
				.id(member.getId())
				.nickname(member.getNickname())
				.role(member.getRole().name())
				.email(member.getEmail())
				.status(member.getStatus().name())
				.build();


		return new CustomOAuth2User(memberInfo,
				Collections.singleton(new SimpleGrantedAuthority(member.getRole().name())),
				attributes.getAttributes(), attributes.getNameAttributeKey());
	}
}
