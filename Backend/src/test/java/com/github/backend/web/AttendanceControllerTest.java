package com.github.backend.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.backend.config.SecurityConfig;
import com.github.backend.dto.attendance.AttendanceDto.GetRequest;
import com.github.backend.dto.attendance.AttendanceDto.Info;
import com.github.backend.security.jwt.JwtAuthenticationProvider;
import com.github.backend.security.jwt.JwtEntryPoint;
import com.github.backend.security.oauth.OAuth2SuccessHandler;
import com.github.backend.service.attendance.AttendanceService;
import com.github.backend.service.common.impl.CustomOAuth2UserService;
import com.github.backend.testUtils.WithMemberInfo;
import java.time.LocalDate;
import java.util.ArrayList;

import com.github.backend.web.member.controller.AttendanceController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(value = AttendanceController.class
	, includeFilters = {
	@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)})
class AttendanceControllerTest {

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
	AttendanceService attendanceService;

	@WithMemberInfo
	@Test
	void checkDailyAttendance_success() throws Exception {
		//given
		doNothing().when(attendanceService).checkTodayAttendance(anyString());

		//when
		//then
		mockMvc.perform(post("/member/attendance"))
			.andDo(print())
			.andExpect(jsonPath("$.status").value(200))
			.andExpect(jsonPath("$.success").value(true));
	}

	@WithMemberInfo
	@Test
	void getAttendances_success() throws Exception {
		//given
		doNothing().when(attendanceService).checkTodayAttendance(anyString());

		LocalDate time = LocalDate.of(2022, 8, 15);

		GetRequest request = GetRequest.builder()
			.startDate(time.withDayOfMonth(1))
			.endDate(time.withDayOfMonth(time.lengthOfMonth()))
			.build();

		ArrayList<Info> list = new ArrayList<>();

		for (int i = 0; i < 2; i++) {
			list.add(Info.builder()
				.memberId(0L)
				.date(time.plusDays(i))
				.build());
		}

		given(attendanceService.getAttendances(any()))
			.willReturn(list);

		//when
		//then
		mockMvc.perform(get("/member/attendance")
				.content(objectMapper.writeValueAsString(request)))
			.andDo(print())
			.andExpect(jsonPath("$.status").value(200))
			.andExpect(jsonPath("$.success").value(true))
			.andExpect(jsonPath("$.data[0].memberId").value(0))
			.andExpect(jsonPath("$.data[1].memberId").value(0))
			.andExpect(jsonPath("$.data[0].date").value(time.plusDays(0).toString()))
			.andExpect(jsonPath("$.data[1].date").value(time.plusDays(1).toString()));
	}


}