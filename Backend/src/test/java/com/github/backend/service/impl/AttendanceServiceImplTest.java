package com.github.backend.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.github.backend.exception.AttendanceException;
import com.github.backend.persist.entity.Attendance;
import com.github.backend.persist.entity.Member;
import com.github.backend.persist.repository.AttendanceRepository;
import com.github.backend.persist.repository.MemberRepository;
import com.github.backend.type.AttendanceErrorCode;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class AttendanceServiceImplTest {

	@Mock
	AttendanceRepository attendanceRepository;

	@Mock
	MemberRepository memberRepository;


	@InjectMocks
	AttendanceServiceImpl attendanceService;

	@DisplayName("출석체크 성공")
	@Test
	void checkTodayAttendance_SUCCESS() {
		//given
		Member member = Member.builder()
			.email("test@test.com")
			.build();

		given(memberRepository.findByEmail(anyString()))
			.willReturn(Optional.of(member));

		given(attendanceRepository.existsByMemberAndAttendanceDate(any(), any()))
			.willReturn(false);

		given(attendanceRepository.save(any()))
			.willReturn(Attendance.builder()
				.member(member).build());

		ArgumentCaptor<Attendance> captor = ArgumentCaptor.forClass(
			Attendance.class);

		//when
		attendanceService.checkTodayAttendance("test@test.com");

		//then
		verify(attendanceRepository).save(captor.capture());

		assertThat(captor.getValue().getMember().getEmail()).isEqualTo(member.getEmail());
		assertThat(captor.getValue().getMember().getPoint()).isEqualTo(
			50);
	}

	@DisplayName("출석체크 실패 - 회원이 없는 경우")
	@Test
	void checkTodayAttendance_fail_MemberNotExists(){
	    //given
		given(memberRepository.findByEmail(anyString()))
			.willReturn(Optional.empty());
	    //when
		//then
		assertThatThrownBy(() -> attendanceService.checkTodayAttendance("test@test.com"))
			.isInstanceOf(AttendanceException.class)
			.hasMessage(AttendanceErrorCode.MEMBER_NOT_EXISTS.getDescription());
	}

	@DisplayName("출석체크 실패 - 이미 출석을 한 경우")
	@Test
	void checkTodayAttendance_fail_AlreadyCheckAttendance(){
	    //given
		given(memberRepository.findByEmail(any()))
			.willReturn(Optional.of(Member.builder().build()));
		given(attendanceRepository.existsByMemberAndAttendanceDate(any(),any()))
			.willReturn(true);
	    //when
		//then
		assertThatThrownBy(() -> attendanceService.checkTodayAttendance("test@test.com"))
			.isInstanceOf(AttendanceException.class)
			.hasMessage(AttendanceErrorCode.ALREADY_CHECK_ATTENDANCE.getDescription());
	}
}