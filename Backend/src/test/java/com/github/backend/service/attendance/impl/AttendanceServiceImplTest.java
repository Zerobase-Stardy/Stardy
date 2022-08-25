package com.github.backend.service.attendance.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.github.backend.dto.attendance.AttendanceDto;
import com.github.backend.dto.attendance.AttendanceDto.GetRequest;
import com.github.backend.exception.attendance.AttendanceException;
import com.github.backend.exception.attendance.code.AttendanceErrorCode;
import com.github.backend.exception.member.MemberException;
import com.github.backend.exception.member.code.MemberErrorCode;
import com.github.backend.persist.attendance.Attendance;
import com.github.backend.persist.attendance.repository.AttendanceRepository;
import com.github.backend.persist.member.Member;
import com.github.backend.persist.member.repository.MemberRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
			.isInstanceOf(MemberException.class)
			.hasMessage(MemberErrorCode.MEMBER_NOT_EXISTS.getDescription());
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

	@DisplayName("출석체크 리스트 조회 - 성공")
	@Test
	void getAttendances_success(){
	    //given
		LocalDate time = LocalDate.of(2022, 8, 15);

		GetRequest request = GetRequest.builder()
			.memberId(1L)
			.startDate(time.withDayOfMonth(1))
			.endDate(time.withDayOfMonth(time.lengthOfMonth()))
			.build();

		ArrayList<Attendance> list = new ArrayList<>();

		for (int i = 0; i < 2; i++) {
			list.add(Attendance.builder()
					.member(Member.builder()
						.id(1L)
						.build())
					.attendanceDate(time.plusDays(i))
				.build());
		}
		given(attendanceRepository.findAllByMember_Id(anyLong(), any(), any()))
			.willReturn(list);

		//when
		List<AttendanceDto.Info> attendances =
			attendanceService.getAttendances(request);

		//then
		assertThat(attendances.size()).isEqualTo(2);
		assertThat(attendances.get(0).getDate()).isEqualTo(time);
	}

}