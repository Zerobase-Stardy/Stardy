package com.github.backend.security.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.backend.dto.common.MemberInfo;
import com.github.backend.dto.common.Result;
import com.github.backend.dto.common.CustomOAuth2User;
import com.github.backend.dto.common.Tokens;
import com.github.backend.service.common.impl.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final TokenService tokenService;
	private final ObjectMapper objectMapper;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {

		CustomOAuth2User principal = (CustomOAuth2User) authentication.getPrincipal();
		MemberInfo memberInfo = principal.getMemberInfo();

		Tokens tokens = tokenService.issueAllToken(memberInfo);

		String targetUrl = UriComponentsBuilder.fromUriString("http://54.219.21.102/")
				.build().toUriString();
		setResponse(response, tokens);
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}

	private void setResponse(HttpServletResponse response, Tokens tokens) {
		response.setStatus(200);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.addHeader("Auth", tokens.getAccessToken());
		response.addHeader("Refresh", tokens.getRefreshToken());
	}

}
