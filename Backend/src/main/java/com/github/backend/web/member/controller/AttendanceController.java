package com.github.backend.web.member.controller;

import com.github.backend.dto.attendance.AttendanceDto;
import com.github.backend.dto.attendance.AttendanceDto.Info;
import com.github.backend.dto.common.MemberInfo;
import com.github.backend.dto.common.Result;
import com.github.backend.service.attendance.AttendanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Tag(name = "My Attendance",description = "내 출석 관련 API")
@RequiredArgsConstructor
@RestController
public class AttendanceController {

	private final AttendanceService attendanceService;

	@Operation(
		summary = "출석 체크", description = "하루에 한번 출석 체크. 출석 체크시 포인트 증가",
		security = {@SecurityRequirement(name = "Authorization")},
		tags = {"My Attendance"}
	)
	@PostMapping("/members/me/attendances/daily")
	public ResponseEntity<Result<?>> checkDailyAttendance(
		@ApiIgnore @AuthenticationPrincipal MemberInfo memberInfo) {
		attendanceService.checkTodayAttendance(memberInfo.getEmail());
		return ResponseEntity.ok().body(new Result<>(200, true, ""));
	}

	@Operation(
		summary = "출석 조회", description = "원하는 기간만큼의 출석 조회 가능" ,
		security = {@SecurityRequirement(name = "Authorization")},
		tags = {"My Attendance"}
	)
	@GetMapping("/members/me/attendances")
	public ResponseEntity<Result<?>> getAttendances(
		@ApiIgnore @AuthenticationPrincipal MemberInfo memberInfo,
		AttendanceDto.GetRequest request) {

		request.setMemberId(memberInfo.getId());

		List<Info> attendances = attendanceService.getAttendances(request);

		return ResponseEntity.ok().body(new Result<>(200, true, attendances));
	}


}
