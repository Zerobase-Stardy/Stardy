package com.github.backend.security.jwt;


import com.github.backend.model.dto.TokenMemberDto.MemberInfo;
import com.github.backend.service.impl.TokenService;
import io.jsonwebtoken.Claims;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;


@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

	private final TokenService tokenService;


	@Override
	public Authentication authenticate(Authentication authentication)
		throws AuthenticationException {

		Claims claims =
			tokenService.parseAccessToken(((JwtAuthenticationToken) authentication).getJwt());

		MemberInfo memberInfo = MemberInfo.of(claims);

		return new JwtAuthenticationToken(memberInfo, "", Arrays.asList(new SimpleGrantedAuthority(memberInfo.getRole())));
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return JwtAuthenticationToken.class.isAssignableFrom(authentication);
	}








}
