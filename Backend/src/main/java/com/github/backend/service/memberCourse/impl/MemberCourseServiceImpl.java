package com.github.backend.service.memberCourse.impl;

import static com.github.backend.exception.memberCourse.code.MemberCourseErrorCode.ALREADY_MEMBER_COURSE_EXISTS;
import static com.github.backend.exception.memberCourse.code.MemberCourseErrorCode.COURSE_NOT_EXISTS;
import static com.github.backend.exception.memberCourse.code.MemberCourseErrorCode.MEMBER_COURSE_NOT_EXISTS;
import static com.github.backend.exception.memberCourse.code.MemberCourseErrorCode.MEMBER_NOT_EXISTS;
import static com.github.backend.exception.memberCourse.code.MemberCourseErrorCode.NOT_ENOUGH_POINT;

import com.github.backend.exception.memberCourse.MemberCourseException;
import com.github.backend.persist.course.Course;
import com.github.backend.persist.member.Member;
import com.github.backend.persist.memberCourse.MemberCourse;
import com.github.backend.persist.course.repository.CourseRepository;
import com.github.backend.persist.memberCourse.repository.MemberCourseRepository;
import com.github.backend.persist.member.repository.MemberRepository;
import com.github.backend.service.memberCourse.MemberCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberCourseServiceImpl implements MemberCourseService {

	private final CourseRepository courseRepository;
	private final MemberCourseRepository memberCourseRepository;
	private final MemberRepository memberRepository;

	@Transactional
	@Override
	public void takeCourse(String email, Long courseId) {

		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new MemberCourseException(MEMBER_NOT_EXISTS));

		Course course = courseRepository.findById(courseId)
			.orElseThrow(() -> new MemberCourseException(COURSE_NOT_EXISTS));

		if (memberCourseRepository.existsByMemberAndCourse(member, course)) {
			throw new MemberCourseException(ALREADY_MEMBER_COURSE_EXISTS);
		}

		if (member.getPoint() < course.getPrice()) {
			throw new MemberCourseException(NOT_ENOUGH_POINT);
		}

		member.decreasePoint(course.getPrice());

		memberCourseRepository.save(MemberCourse.builder()
			.member(member)
			.course(course)
			.bookmark(false)
			.build());
	}

	@Transactional
	@Override
	public void toggleBookmark(String email, Long memberCourseId) {
		MemberCourse memberCourse = memberCourseRepository.findWithMemberById(memberCourseId)
			.orElseThrow(() -> new MemberCourseException(MEMBER_COURSE_NOT_EXISTS));

		Member member = memberCourse.getMember();

		if (!member.getEmail().equals(email)) {
			throw new MemberCourseException(MEMBER_COURSE_NOT_EXISTS);
		}

		memberCourse.toggleBookmark();
	}
}
