package com.github.backend.web.myCourse.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.backend.config.SecurityConfig;
import com.github.backend.dto.myCourse.MyCourseSearchDto.Info;
import com.github.backend.security.jwt.JwtAuthenticationProvider;
import com.github.backend.security.jwt.JwtEntryPoint;
import com.github.backend.security.oauth.OAuth2SuccessHandler;
import com.github.backend.service.common.impl.CustomOAuth2UserService;
import com.github.backend.service.myCourse.MyCourseInfoSearchService;
import com.github.backend.testUtils.WithMemberInfo;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = MyCourseSearchController.class
	, includeFilters = {
	@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)})
class MyCourseSearchControllerTest {

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
	MyCourseInfoSearchService myCourseInfoSearchService;


	@WithMemberInfo
	@Test
	void searchMyCourse() throws Exception {
		//given
		ArrayList<Info> infos = new ArrayList<>();

		for (long i = 0; i < 1; i++) {
			Info info = Info.builder()
				.courseId(i)
				.build();

			infos.add(info);
		}
		PageImpl<Info> expectedPage = new PageImpl<>(infos);

		given(myCourseInfoSearchService.searchMyCourses(any(), any()))
			.willReturn(expectedPage);

		//when

		//then
		mockMvc.perform(get("/members/me/courses"))
			.andDo(print())
			.andExpect(jsonPath("$.status").value(200))
			.andExpect(jsonPath("$.success").value(true))
			.andExpect(jsonPath("$.data.content[0].courseId").value(0));


	}
}