package com.github.backend.web;

import com.github.backend.model.Result;
import com.github.backend.model.dto.TokenMemberDto.MemberInfo;
import com.github.backend.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AttendanceController {

	private final AttendanceService attendanceService;

	@PostMapping("/member/attendance")
	public ResponseEntity<Result<?>> checkDailyAttendance(@AuthenticationPrincipal MemberInfo memberInfo) {
		attendanceService.checkTodayAttendance(memberInfo.getEmail());
		return ResponseEntity.ok().body(new Result<>(200,true,""));
	}

	
}
