package com.github.backend.security.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.backend.dto.common.MemberInfo;
import com.github.backend.dto.common.Result;
import com.github.backend.dto.common.CustomOAuth2User;
import com.github.backend.dto.common.TokenMemberDto.Tokens;
import com.github.backend.service.common.impl.TokenService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

	private final TokenService tokenService;
	private final ObjectMapper objectMapper;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {

		CustomOAuth2User principal = (CustomOAuth2User) authentication.getPrincipal();
		MemberInfo memberInfo = principal.getMemberInfo();

		Tokens tokens = tokenService.issueAllToken(memberInfo);

		setResponse(response);
		response.getWriter().println(objectMapper.writeValueAsString(new Result<>(200, true, tokens)));

	}

	private void setResponse(HttpServletResponse response) {
		response.setStatus(200);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
	}

}
