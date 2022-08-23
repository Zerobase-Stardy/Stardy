package com.github.backend.security.jwt;


import com.github.backend.exception.JwtInvalidException;
import com.github.backend.model.dto.TokenMemberDto.MemberInfo;
import com.github.backend.service.impl.TokenService;
import com.github.backend.type.JwtErrorCode;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;


@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

	private final TokenService tokenService;
	private final HttpServletRequest request;

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {

		Claims claims =
				tokenService.parseAccessToken(((JwtAuthenticationToken) authentication).getJwt());

		MemberInfo memberInfo = MemberInfo.of(claims);

		String status = memberInfo.getStatus();

		System.out.println(status);
		System.out.println(request.getRequestURI());


		switch (status) {
			case "BANNED":
				throw new JwtInvalidException(JwtErrorCode.MEMBER_STATUS_BANNED);
			case "WITHDRAWAL":
				throw new JwtInvalidException(JwtErrorCode.MEMBER_STATUS_WITHDRAWAL);
			case "WAIT":
				switch (request.getRequestURI()){
					case "/member/nickname" :
					case "/member/withdrawal" :
					case "/oauth/logout":
						return new JwtAuthenticationToken(memberInfo, "",
								Arrays.asList(new SimpleGrantedAuthority(memberInfo.getRole())));
					default:
						throw new JwtInvalidException(JwtErrorCode.MEMBER_STATUS_WAIT);
				}
			default:
				return new JwtAuthenticationToken(memberInfo, "",
						Arrays.asList(new SimpleGrantedAuthority(memberInfo.getRole())));
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return JwtAuthenticationToken.class.isAssignableFrom(authentication);
	}

}