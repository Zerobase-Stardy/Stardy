package com.github.backend.web.myCourse.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.backend.config.SecurityConfig;
import com.github.backend.dto.myCourse.MyCourseSearchDto.Info;
import com.github.backend.security.jwt.JwtAccessDeniedHandler;
import com.github.backend.security.jwt.JwtAuthenticationProvider;
import com.github.backend.security.jwt.JwtEntryPoint;
import com.github.backend.security.oauth.CookieAuthorizationRequestRepository;
import com.github.backend.security.oauth.CustomOAuth2UserService;
import com.github.backend.security.oauth.OAuth2AuthenticationFailureHandler;
import com.github.backend.security.oauth.OAuth2AuthenticationSuccessHandler;
import com.github.backend.service.myCourse.MyCourseBookmarkService;
import com.github.backend.service.myCourse.MyCourseInfoSearchService;
import com.github.backend.testUtils.WithMemberInfo;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = MyCoursePageController.class
	, includeFilters = {
	@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)})
class MyCoursePageControllerTest {

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
	CookieAuthorizationRequestRepository cookieAuthorizationRequestRepository;
	@MockBean
	JwtAccessDeniedHandler jwtAccessDeniedHandler;

	@MockBean
	MyCourseInfoSearchService myCourseInfoSearchService;

	@MockBean
	MyCourseBookmarkService myCourseBookmarkService;


	@DisplayName("내 강의 검색 성공")
	@WithMemberInfo
	@Test
	void searchMyCourse_success() throws Exception {
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

	@DisplayName("내 강의 북마크 성공")
	@WithMemberInfo
	@Test
	void toggleBookmark_success() throws Exception {
	    //given
		given(myCourseBookmarkService.toggleBookmark(any(), any()))
			.willReturn(true);

		//when
		//then
		mockMvc.perform(post("/members/me/courses/1/bookmark"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.status").value(200))
			.andExpect(jsonPath("$.success").value(true))
			.andExpect(jsonPath("$.data.bookmark").value(true));

		verify(myCourseBookmarkService).toggleBookmark(1L, 1L);
	}
}