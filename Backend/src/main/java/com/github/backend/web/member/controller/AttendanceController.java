package com.github.backend.web.member.controller;

import com.github.backend.dto.attendance.AttendanceDto;
import com.github.backend.dto.attendance.AttendanceDto.Info;
import com.github.backend.dto.common.MemberInfo;
import com.github.backend.dto.common.Result;
import com.github.backend.service.attendance.AttendanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RestController
public class AttendanceController {

	private final AttendanceService attendanceService;

	@ApiOperation(
		value = "출석 체크하기",
		notes = "출석 체크 시 50 포인트 추가 , 출석체크는 하루에 한번만 가능 , 로그인 필요"
	)

	@PostMapping("/members/me/attendances/daily")
	public ResponseEntity<Result<?>> checkDailyAttendance(@ApiIgnore @AuthenticationPrincipal MemberInfo memberInfo) {
		attendanceService.checkTodayAttendance(memberInfo.getEmail());
		return ResponseEntity.ok().body(new Result<>(200,true,""));
	}

	@ApiOperation(
		value = "출석 조회",
		notes = "원하는 기간만큼의 출석 조회 가능"
	)
	@GetMapping("/members/me/attendances")
	public ResponseEntity<Result<?>> getAttendances(@ApiIgnore @AuthenticationPrincipal MemberInfo memberInfo,
		AttendanceDto.GetRequest request) {

		request.setMemberId(memberInfo.getId());

		List<Info> attendances = attendanceService.getAttendances(request);

		return ResponseEntity.ok().body(new Result<>(200, true,attendances));
	}


}
