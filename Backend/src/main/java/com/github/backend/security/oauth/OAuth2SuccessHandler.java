package com.github.backend.security.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.backend.model.Result;
import com.github.backend.model.dto.TokenMemberDto.MemberInfo;
import com.github.backend.model.dto.TokenMemberDto.Tokens;
import com.github.backend.service.impl.TokenService;
import com.nimbusds.common.contenttype.ContentType;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
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

		OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

		MemberInfo memberInfo = (MemberInfo) oAuth2User.getAttributes().get("memberInfo");
		Tokens tokens = tokenService.issueAllToken(memberInfo);

		setResponse(response);

		response.getWriter().write(objectMapper.writeValueAsString(new Result<>(200, true, tokens)));

	}

	private void setResponse(HttpServletResponse response) {
		response.setStatus(200);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
	}

}
