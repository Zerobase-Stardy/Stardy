package com.github.backend.security.jwt;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final String BEARER_PREFIX = "Bearer ";

	private final AuthenticationManager authenticationManager;

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String token = getJwtFrom(request);

		if (StringUtils.hasText(token)) {
			try {
				JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(token);
				Authentication authentication = authenticationManager.authenticate(
					jwtAuthenticationToken);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} catch (AuthenticationException e) {
				SecurityContextHolder.clearContext();
				request.setAttribute("exception",e.getMessage());
			}
		}

		filterChain.doFilter(request, response);
	}

	private String getJwtFrom(HttpServletRequest request) {
		// 요청 헤더에서 Bearer Token 가져오기
		String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
			return bearerToken.substring(BEARER_PREFIX.length());
		}

		log.warn("Request Header doesn't include JWT");

		return "";
	}


}
