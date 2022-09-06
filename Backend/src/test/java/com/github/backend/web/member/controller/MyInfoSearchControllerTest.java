package com.github.backend.web.member.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.backend.config.SecurityConfig;
import com.github.backend.dto.member.SearchMember;
import com.github.backend.security.jwt.JwtAccessDeniedHandler;
import com.github.backend.security.jwt.JwtAuthenticationProvider;
import com.github.backend.security.jwt.JwtEntryPoint;
import com.github.backend.service.member.MemberSearchService;
import com.github.backend.testUtils.WithMemberInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = MyInfoSearchController.class
	, includeFilters = {
	@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)})
class MyInfoSearchControllerTest {

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
	JwtAccessDeniedHandler jwtAccessDeniedHandler;

	@MockBean
	MemberSearchService memberSearchService;


	@DisplayName("내 정보 조회 성공")
	@WithMemberInfo
	@Test
	void searchMyInfo_success() throws Exception {
		//given
		SearchMember searchMember = SearchMember.builder()
			.nickname("test")
			.email("test@test.com")
			.point(500L)
			.build();

		given(memberSearchService.searchMember(anyLong()))
			.willReturn(searchMember);

		//when
		//then
		mockMvc.perform(get("/members/me"))
			.andDo(print())
			.andExpect(jsonPath("$.status").value(200))
			.andExpect(jsonPath("$.success").value(true))
			.andExpect(jsonPath("$.data.email").value(searchMember.getEmail()))
			.andExpect(jsonPath("$.data.nickname").value(searchMember.getNickname()))
			.andExpect(jsonPath("$.data.point").value(searchMember.getPoint()));

	}

}