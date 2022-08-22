package com.github.backend.service.impl;

import static com.github.backend.model.dto.AttendanceDto.Info;
import static com.github.backend.type.AttendanceErrorCode.ALREADY_CHECK_ATTENDANCE;
import static com.github.backend.type.AttendanceErrorCode.MEMBER_NOT_EXISTS;
import static java.util.stream.Collectors.toList;

import com.github.backend.exception.AttendanceException;
import com.github.backend.model.dto.AttendanceDto.GetRequest;
import com.github.backend.persist.entity.Attendance;
import com.github.backend.persist.entity.Member;
import com.github.backend.persist.repository.AttendanceRepository;
import com.github.backend.persist.repository.MemberRepository;
import com.github.backend.service.AttendanceService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
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

		if (attendanceRepository.existsByMemberAndAttendanceDate(member, today)) {
			throw new AttendanceException(ALREADY_CHECK_ATTENDANCE);
		}

		member.increasePoint(DAILY_ATTENDANCE_POINT);

		attendanceRepository.save(Attendance.builder()
			.member(member)
			.attendanceDate(today)
			.build());
	}

	@Transactional(readOnly = true)
	@Override
	public List<Info> getAttendances(GetRequest request) {
		return attendanceRepository
			.findAllByMember_Id(request.getMemberId(), request.getStartDate(), request.getEndDate())
			.stream()
			.map(Info::of)
			.collect(toList());
	}
}
