package com.github.backend.service.impl;

import static com.github.backend.type.AttendanceErrorCode.*;

import com.github.backend.exception.AttendanceException;
import com.github.backend.persist.entity.Attendance;
import com.github.backend.persist.entity.Member;
import com.github.backend.persist.repository.AttendanceRepository;
import com.github.backend.persist.repository.MemberRepository;
import com.github.backend.service.AttendanceService;
import com.github.backend.type.AttendanceErrorCode;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AttendanceServiceImpl implements AttendanceService {

	private final AttendanceRepository attendanceRepository;
	private final MemberRepository memberRepository;

	private static final long DAILY_ATTENDANCE_POINT = 50;

	@Transactional
	@Override
	public void checkTodayAttendance(String email) {
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new AttendanceException(MEMBER_NOT_EXISTS));

		LocalDate today = LocalDate.now();

		if (attendanceRepository.existsByMemberAndAttendanceDate(member,today)) {
			throw new AttendanceException(ALREADY_CHECK_ATTENDANCE);
		}

		member.increasePoint(DAILY_ATTENDANCE_POINT);

		attendanceRepository.save(Attendance.builder()
			.member(member)
			.attendanceDate(today)
			.build());
	}
}
