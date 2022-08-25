package com.github.backend.web.attendance;

import com.github.backend.dto.attendance.AttendanceDto;
import com.github.backend.dto.attendance.AttendanceDto.Info;
import com.github.backend.dto.common.MemberInfo;
import com.github.backend.dto.common.Result;
import com.github.backend.service.attendance.AttendanceService;
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

	@PostMapping("/members/me/attendances/daily")
	public ResponseEntity<Result<?>> checkDailyAttendance(@AuthenticationPrincipal MemberInfo memberInfo) {
		attendanceService.checkTodayAttendance(memberInfo.getEmail());
		return ResponseEntity.ok().body(new Result<>(200,true,""));
	}

	@GetMapping("/members/me/attendances")
	public ResponseEntity<Result<?>> getAttendances(@AuthenticationPrincipal MemberInfo memberInfo,
		AttendanceDto.GetRequest request) {

		request.setMemberId(memberInfo.getId());

		List<Info> attendances = attendanceService.getAttendances(request);

		return ResponseEntity.ok().body(new Result<>(200, true,attendances));
	}


}
