package com.github.backend.service.myCourse.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.github.backend.exception.course.CourseException;
import com.github.backend.exception.course.code.CourseErrorCode;
import com.github.backend.exception.member.MemberException;
import com.github.backend.exception.member.code.MemberErrorCode;
import com.github.backend.exception.myCourse.MemberCourseException;
import com.github.backend.exception.myCourse.code.MemberCourseErrorCode;
import com.github.backend.persist.course.Course;
import com.github.backend.persist.course.repository.CourseRepository;
import com.github.backend.persist.member.Member;
import com.github.backend.persist.member.repository.MemberRepository;
import com.github.backend.persist.myCourse.MyCourse;
import com.github.backend.persist.myCourse.repository.MemberCourseRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MyCourseUnlockServiceImplTest {

	@Mock
	MemberRepository memberRepository;

	@Mock
	CourseRepository courseRepository;

	@Mock
	MemberCourseRepository memberCourseRepository;

	@InjectMocks
	MyCourseUnlockServiceImpl memberCourseUnlockService;

	Member member;
	Course course;

	@BeforeEach
	void beforeEach() {
		member = Member.builder()
			.id(1L)
			.email("test@test.com")
			.point(500)
			.build();

		course = Course.builder()
			.id(1L)
			.title("test")
			.price(500L)
			.build();
	}

	@DisplayName("강의 구매 성공")
	@Test
	void takeCourse_success() {
		//given
		given(memberRepository.findById(anyLong()))
			.willReturn(Optional.of(member));

		given(courseRepository.findById(anyLong()))
			.willReturn(Optional.of(course));

		ArgumentCaptor<MyCourse> captor = ArgumentCaptor.forClass(
			MyCourse.class);

		//when
		memberCourseUnlockService.unlockCourse(member.getId(), course.getId());

		//then
		verify(memberCourseRepository).save(captor.capture());
		assertThat(member.getPoint()).isEqualTo(0);
		assertThat(captor.getValue().isBookmark()).isFalse();
	}

	@DisplayName("강의 구매 실패 - 회원이 존재하지 않을 때")
	@Test
	void takeCourse_fail_MemberNotExists() {
		//given
		given(memberRepository.findById(anyLong()))
			.willReturn(Optional.empty());
		//when

		//then
		assertThatThrownBy(
			() -> memberCourseUnlockService.unlockCourse(member.getId(), course.getId()))
			.isInstanceOf(MemberException.class)
			.hasMessage(MemberErrorCode.MEMBER_NOT_EXISTS.getDescription());
	}

	@DisplayName("강의 구매 실패 - 강의가 존재하지 않을 때")
	@Test
	void takeCourse_fail_CourseNotExists() {
		//given
		given(memberRepository.findById(anyLong()))
			.willReturn(Optional.of(member));

		given(courseRepository.findById(anyLong()))
			.willReturn(Optional.empty());
		//when
		//then
		assertThatThrownBy(
			() -> memberCourseUnlockService.unlockCourse(member.getId(), course.getId()))
			.isInstanceOf(CourseException.class)
			.hasMessage(CourseErrorCode.NOT_EXIST_COURSE.getDescription());
	}

	@DisplayName("강의 구매 실패 - 이미 구매한 강의인 경우")
	@Test
	void takeCourse_fail_AlreadyMemberCourseExists() {
		//given
		given(memberRepository.findById(anyLong()))
			.willReturn(Optional.of(member));

		given(courseRepository.findById(anyLong()))
			.willReturn(Optional.of(course));

		given(memberCourseRepository.existsByMemberAndCourse(any(), any()))
			.willReturn(true);

		//when
		//then
		assertThatThrownBy(
			() -> memberCourseUnlockService.unlockCourse(member.getId(), course.getId()))
			.isInstanceOf(MemberCourseException.class)
			.hasMessage(MemberCourseErrorCode.ALREADY_MEMBER_COURSE_EXISTS.getDescription());
	}

	@DisplayName("강의 구매 실패 - 포인트가 부족할 때")
	@Test
	void takeCourse_fail_NotEnoughPoint() {
		//given
		given(memberRepository.findById(anyLong()))
			.willReturn(Optional.of(member));

		given(courseRepository.findById(anyLong()))
			.willReturn(Optional.of(course));

		given(memberCourseRepository.existsByMemberAndCourse(any(), any()))
			.willReturn(false);

		course.setPrice(600L);

		//when
		//then
		assertThatThrownBy(
			() -> memberCourseUnlockService.unlockCourse(member.getId(), course.getId()))
			.isInstanceOf(MemberException.class)
			.hasMessage(MemberErrorCode.MEMBER_NOT_ENOUGH_POINT.getDescription());
	}

}