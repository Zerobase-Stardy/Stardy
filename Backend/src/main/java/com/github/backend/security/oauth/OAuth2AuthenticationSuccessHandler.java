package com.github.backend.security.oauth;

import com.github.backend.dto.common.CustomOAuth2User;
import com.github.backend.dto.common.MemberInfo;
import com.github.backend.security.jwt.Tokens;
import com.github.backend.security.jwt.TokenService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Value("${app.oauth2.authorizedRedirectUri}")
	private String redirectUri;
	private final TokenService tokenService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {

		MemberInfo memberInfo = getMemberInfo(authentication);
		Tokens tokens = tokenService.issueAllToken(memberInfo);

		setTokenIntoHeader(response,tokens);

		getRedirectStrategy().sendRedirect(request, response, redirectUri);
	}

	private MemberInfo getMemberInfo(Authentication authentication) {
		return ((CustomOAuth2User) authentication.getPrincipal()).getMemberInfo();
	}

	private void setTokenIntoHeader(HttpServletResponse response,Tokens tokens) {
		response.setHeader("ACCESS",tokens.getAccessToken());
		response.setHeader("REFRESH",tokens.getRefreshToken());
		response.setHeader("REFRESH_EXPIRED_MIN",String.valueOf(tokens.getRefreshTokenExpiredMin()));
	}


}
