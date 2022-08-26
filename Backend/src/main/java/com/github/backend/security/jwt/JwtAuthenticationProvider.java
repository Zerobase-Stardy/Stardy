package com.github.backend.security.jwt;


import com.github.backend.dto.common.AdminInfo;
import com.github.backend.dto.common.MemberInfo;
import com.github.backend.exception.common.JwtInvalidException;
import com.github.backend.exception.common.code.JwtErrorCode;
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

		if (claims.get(JwtInfo.KEY_ROLES,String.class).equals("ROLE_ADMIN")) {

			AdminInfo info = AdminInfo.of(claims);

			return new JwtAuthenticationToken(AdminInfo.of(claims), "",
				Arrays.asList(new SimpleGrantedAuthority(info.getRole())));
		}

		MemberInfo info = MemberInfo.of(claims);

		switch (info.getStatus()) {
			case "BANNED":
				throw new JwtInvalidException(JwtErrorCode.MEMBER_STATUS_BANNED);
			case "WITHDRAWAL":
				throw new JwtInvalidException(JwtErrorCode.MEMBER_STATUS_WITHDRAWAL);
			case "WAIT":
				throw new JwtInvalidException(JwtErrorCode.MEMBER_STATUS_WAIT);
			default:
				return new JwtAuthenticationToken(info, "",
					Arrays.asList(new SimpleGrantedAuthority(info.getRole())));
		}

	}

	@Override
	public boolean supports(Class<?> authentication) {
		return JwtAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
