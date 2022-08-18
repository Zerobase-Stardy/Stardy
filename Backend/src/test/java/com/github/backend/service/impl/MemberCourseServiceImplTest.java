package com.github.backend.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.github.backend.exception.MemberCourseException;
import com.github.backend.persist.entity.Course;
import com.github.backend.persist.entity.Member;
import com.github.backend.persist.entity.MemberCourse;
import com.github.backend.persist.repository.CourseRepository;
import com.github.backend.persist.repository.MemberCourseRepository;
import com.github.backend.persist.repository.MemberRepository;
import com.github.backend.type.MemberCourseErrorCode;
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
class MemberCourseServiceImplTest {

	@Mock
	MemberRepository memberRepository;

	@Mock
	CourseRepository courseRepository;

	@Mock
	MemberCourseRepository memberCourseRepository;

	@InjectMocks
	MemberCourseServiceImpl memberCourseService;

	Member member;
	Course course;

	@BeforeEach
	void beforeAll() {
		member = Member.builder()
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
		given(memberRepository.findByEmail(anyString()))
			.willReturn(Optional.of(member));

		given(courseRepository.findById(anyLong()))
			.willReturn(Optional.of(course));

		ArgumentCaptor<MemberCourse> captor = ArgumentCaptor.forClass(
			MemberCourse.class);

		//when
		memberCourseService.takeCourse(member.getEmail(), course.getId());

		//then
		verify(memberCourseRepository).save(captor.capture());
		assertThat(member.getPoint()).isEqualTo(0);
		assertThat(captor.getValue().isBookmark()).isFalse();
	}

	@DisplayName("강의 구매 실패 - 회원이 존재하지 않을 때")
	@Test
	void takeCourse_fail_MemberNotExists() {
		//given
		given(memberRepository.findByEmail(anyString()))
			.willReturn(Optional.empty());
		//when

		//then
		assertThatThrownBy(
			() -> memberCourseService.takeCourse(member.getEmail(), course.getId()))
			.isInstanceOf(MemberCourseException.class)
			.hasMessage(MemberCourseErrorCode.MEMBER_NOT_EXISTS.getDescription());
	}

	@DisplayName("강의 구매 실패 - 강의가 존재하지 않을 때")
	@Test
	void takeCourse_fail_CourseNotExists() {
		//given
		given(memberRepository.findByEmail(anyString()))
			.willReturn(Optional.of(member));

		given(courseRepository.findById(anyLong()))
			.willReturn(Optional.empty());
		//when
		//then
		assertThatThrownBy(
			() -> memberCourseService.takeCourse(member.getEmail(), course.getId()))
			.isInstanceOf(MemberCourseException.class)
			.hasMessage(MemberCourseErrorCode.COURSE_NOT_EXISTS.getDescription());
	}

	@DisplayName("강의 구매 실패 - 이미 구매한 강의인 경우")
	@Test
	void takeCourse_fail_AlreadyMemberCourseExists() {
		//given
		given(memberRepository.findByEmail(anyString()))
			.willReturn(Optional.of(member));

		given(courseRepository.findById(anyLong()))
			.willReturn(Optional.of(course));

		given(memberCourseRepository.existsByMemberAndCourse(any(), any()))
			.willReturn(true);

		//when
		//then
		assertThatThrownBy(
			() -> memberCourseService.takeCourse(member.getEmail(), course.getId()))
			.isInstanceOf(MemberCourseException.class)
			.hasMessage(MemberCourseErrorCode.ALREADY_MEMBER_COURSE_EXISTS.getDescription());
	}

	@DisplayName("강의 구매 실패 - 회원이 존재하지 않을 때")
	@Test
	void takeCourse_fail_NotEnoughPoint() {
		//given
		given(memberRepository.findByEmail(anyString()))
			.willReturn(Optional.of(member));

		given(courseRepository.findById(anyLong()))
			.willReturn(Optional.of(course));

		given(memberCourseRepository.existsByMemberAndCourse(any(), any()))
			.willReturn(false);

		course.setPrice(600L);

		//when
		//then
		assertThatThrownBy(
			() -> memberCourseService.takeCourse(member.getEmail(), course.getId()))
			.isInstanceOf(MemberCourseException.class)
			.hasMessage(MemberCourseErrorCode.NOT_ENOUGH_POINT.getDescription());
	}

	@DisplayName("강의 즐겨찾기 성공")
	@Test
	void toggleBookmark_success() {
		//given
		MemberCourse memberCourse = MemberCourse.builder()
			.id(1L)
			.member(member)
			.bookmark(false)
			.build();

		given(memberCourseRepository.findWithMemberById(anyLong()))
			.willReturn(Optional.of(memberCourse));

		//when
		memberCourseService.toggleBookmark(member.getEmail(), 1L);

		//then
		assertThat(memberCourse.isBookmark()).isTrue();
	}

	@DisplayName("강의 즐겨찾기 실패 - MemberCourse가 존재하지 않을 때")
	@Test
	void toggleBookmark_fail_MemberCourseNotExists() {
		//given
		given(memberCourseRepository.findWithMemberById(anyLong()))
			.willReturn(Optional.empty());

		//when
		//then
		assertThatThrownBy(
			() -> memberCourseService.toggleBookmark(member.getEmail(), 1L))
			.isInstanceOf(MemberCourseException.class)
			.hasMessage(MemberCourseErrorCode.MEMBER_COURSE_NOT_EXISTS.getDescription());
	}

	@DisplayName("강의 즐겨찾기 실패 - MemberCourse의 이메일과 요청자의 이메일이 일치하지 않을 때")
	@Test
	void toggleBookmark_fail_MemberCourseNotExists_EmailIncorrect() {
		//given
		MemberCourse memberCourse = MemberCourse.builder()
			.id(1L)
			.member(member)
			.bookmark(false)
			.build();

		given(memberCourseRepository.findWithMemberById(anyLong()))
			.willReturn(Optional.of(memberCourse));

		//when
		//then
		assertThatThrownBy(
			() -> memberCourseService.toggleBookmark("incorrectEmail", 1L))
			.isInstanceOf(MemberCourseException.class)
			.hasMessage(MemberCourseErrorCode.MEMBER_COURSE_NOT_EXISTS.getDescription());
	}
}