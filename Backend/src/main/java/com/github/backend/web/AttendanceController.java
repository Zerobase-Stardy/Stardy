package com.github.backend.web;

import com.github.backend.model.Result;
import com.github.backend.model.dto.AttendanceDto;
import com.github.backend.model.dto.AttendanceDto.Info;
import com.github.backend.model.dto.TokenMemberDto.MemberInfo;
import com.github.backend.service.AttendanceService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
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

	@GetMapping("/member/attendance")
	public ResponseEntity<Result<?>> getAttendances(@AuthenticationPrincipal MemberInfo memberInfo,
		AttendanceDto.GetRequest request) {

		request.setMemberId(memberInfo.getId());

		List<Info> attendances = attendanceService.getAttendances(request);

		return ResponseEntity.ok().body(new Result<>(200, true,attendances));
	}


}
