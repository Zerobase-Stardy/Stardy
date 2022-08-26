package com.github.backend.web.myCourse.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.backend.config.SecurityConfig;
import com.github.backend.security.jwt.JwtAccessDeniedHandler;
import com.github.backend.security.jwt.JwtAuthenticationProvider;
import com.github.backend.security.jwt.JwtEntryPoint;
import com.github.backend.security.oauth.CustomOAuth2UserService;
import com.github.backend.security.oauth.OAuth2AuthenticationFailureHandler;
import com.github.backend.security.oauth.OAuth2AuthenticationSuccessHandler;
import com.github.backend.service.myCourse.MyCourseUnlockService;
import com.github.backend.testUtils.WithMemberInfo;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = CourseUnlockController.class
	, includeFilters = {
	@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)})
class CourseUnlockControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	JwtAuthenticationProvider jwtAuthenticationProvider;
	@MockBean
	JwtEntryPoint jwtEntryPoint;
	@MockBean
	AuthenticationConfiguration authenticationConfiguration;
	@MockBean
	OAuth2AuthenticationSuccessHandler oAuth2SuccessHandler;
	@MockBean
	OAuth2AuthenticationFailureHandler oAuth2FailureHandler;
	@MockBean
	CustomOAuth2UserService oAuth2UserService;
	@MockBean
	JwtAccessDeniedHandler jwtAccessDeniedHandler;

	@MockBean
	MyCourseUnlockService myCourseUnlockService;


	@WithMemberInfo
	@Test
	void unlockCourse() throws Exception {
		doNothing().when(myCourseUnlockService).unlockCourse(anyLong(),anyLong());

		ArgumentCaptor<Long> memberIdCaptor = ArgumentCaptor.forClass(Long.class);
		ArgumentCaptor<Long> courseIdCaptor = ArgumentCaptor.forClass(Long.class);

		//when
		//then
		mockMvc.perform(post("/courses/1/unlock"))
			.andDo(print())
			.andExpect(jsonPath("$.status").value(200))
			.andExpect(jsonPath("$.success").value(true));

		verify(myCourseUnlockService).unlockCourse(memberIdCaptor.capture(),
			courseIdCaptor.capture());

		assertThat(memberIdCaptor.getValue()).isEqualTo(1L);
		assertThat(courseIdCaptor.getValue()).isEqualTo(1L);
	}
}