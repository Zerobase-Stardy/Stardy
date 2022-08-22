package com.github.backend.web;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.backend.config.SecurityConfig;
import com.github.backend.model.dto.MemberCourseDto.TakeRequest;
import com.github.backend.security.jwt.JwtAuthenticationProvider;
import com.github.backend.security.jwt.JwtEntryPoint;
import com.github.backend.security.oauth.OAuth2SuccessHandler;
import com.github.backend.service.MemberCourseService;
import com.github.backend.service.impl.CustomOAuth2UserService;
import com.github.backend.testUtils.WithMemberInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = MemberCourseController.class
	, includeFilters = {
	@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)})
class MemberCourseControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	JwtAuthenticationProvider jwtAuthenticationProvider;
	@MockBean
	JwtEntryPoint jwtEntryPoint;
	@MockBean
	OAuth2SuccessHandler oAuth2SuccessHandler;
	@MockBean
	CustomOAuth2UserService customOAuth2UserService;

	@MockBean
	MemberCourseService memberCourseService;

	@WithMemberInfo
	@DisplayName("강의 구매 성공")
	@Test
	void takeCourse_success() throws Exception {
		//given
		doNothing().when(memberCourseService).takeCourse(anyLong(), anyLong());

		TakeRequest request = TakeRequest.builder()
			.courseId(1L)
			.build();

		//when
		//then
		mockMvc.perform(post("/member/course")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andDo(print())
			.andExpect(jsonPath("$.success").value(true))
			.andExpect(jsonPath("$.status").value(200));
	}
}