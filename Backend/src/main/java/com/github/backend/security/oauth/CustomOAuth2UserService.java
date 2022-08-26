package com.github.backend.security.oauth;


import com.github.backend.dto.common.CustomOAuth2User;
import com.github.backend.dto.common.MemberInfo;
import com.github.backend.exception.common.OAuthException;
import com.github.backend.exception.common.code.OAuthErrorCode;
import com.github.backend.persist.common.type.AuthType;
import com.github.backend.persist.member.Member;
import com.github.backend.persist.member.repository.MemberRepository;
import com.github.backend.persist.member.type.MemberStatus;
import com.github.backend.persist.member.type.Role;
import com.github.backend.security.oauth.dto.OAuth2UserInfo;
import com.github.backend.security.oauth.dto.OAuth2UserInfoFactory;
import java.util.Collections;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final MemberRepository memberRepository;

	@Transactional
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

		DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

		OAuth2User oAuth2User = delegate.loadUser(userRequest);

		return process(userRequest, oAuth2User);
	}

	private OAuth2User process(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
		AuthType authType = AuthType.valueOf(
			oAuth2UserRequest.getClientRegistration().getRegistrationId().toUpperCase());

		OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory
			.getOAuth2UserInfo(authType, oAuth2User.getAttributes());

		if(!StringUtils.hasText(oAuth2UserInfo.getEmail())) {
			throw new OAuthException(OAuthErrorCode.EMAIL_NOT_FOUND);
		}

		Optional<Member> memberOptional =
			memberRepository.findByEmail(oAuth2UserInfo.getEmail());

		Member member;

		boolean isFirstLogin;
		if(memberOptional.isPresent()) {
			member = memberOptional.get();
			isFirstLogin = true;

			validateRequestAuthTypeAndMemberAuthType(authType, member);

		} else {
			member = registerNewMember(oAuth2UserRequest, oAuth2UserInfo);
			isFirstLogin = false;
		}

		MemberInfo memberInfo = MemberInfo.of(member);
		memberInfo.setFirstLogin(isFirstLogin);

		return new CustomOAuth2User(memberInfo,
			Collections.singletonList(new SimpleGrantedAuthority(member.getRole().name())),
			oAuth2User.getAttributes());
	}

	private void validateRequestAuthTypeAndMemberAuthType(AuthType authType, Member member) {
		if(!authType.equals(member.getAuthType())) {
			throw new OAuthException(OAuthErrorCode.EMAIL_ALREADY_SIGNED_UP
				.setDescription("해당 이메일은 "
					+ member.getAuthType().getKorName() + "으로 가입되어있습니다. "
					+ member.getAuthType().getKorName()+"으로 다시 로그인 해주세요"));
		}
	}

	private Member registerNewMember(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
		Member member = Member.builder()
			.email(oAuth2UserInfo.getEmail())
			.nickname(oAuth2UserInfo.getName())
			.role(Role.ROLE_USER)
			.status(MemberStatus.PERMITTED)
			.authType(oAuth2UserInfo.getAuthType())
			.point(0)
			.build();

		return memberRepository.save(member);
	}




}
