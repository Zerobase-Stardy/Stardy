package com.github.backend.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.backend.dto.common.ErrorResult;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {

	private final ObjectMapper objectMapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException, ServletException {
		setResponse(response);

		String code = (String)request.getAttribute("errorCode");
		String message = (String)request.getAttribute("errorMessage");

		ErrorResult result = ErrorResult.builder()
			.errorCode(code)
			.errorDescription(message)
			.build();

		response.getWriter().write(objectMapper.writeValueAsString(result));
	}

	private void setResponse(HttpServletResponse response) {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
	}
}
