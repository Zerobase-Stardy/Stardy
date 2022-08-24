package com.github.backend.service.memberCourse.impl;

import static com.github.backend.exception.memberCourse.code.MemberCourseErrorCode.ALREADY_MEMBER_COURSE_EXISTS;

import com.github.backend.exception.course.CourseException;
import com.github.backend.exception.course.code.CourseErrorCode;
import com.github.backend.exception.member.MemberException;
import com.github.backend.exception.member.code.MemberErrorCode;
import com.github.backend.exception.memberCourse.MemberCourseException;
import com.github.backend.persist.course.Course;
import com.github.backend.persist.course.repository.CourseRepository;
import com.github.backend.persist.member.Member;
import com.github.backend.persist.member.repository.MemberRepository;
import com.github.backend.persist.memberCourse.MemberCourse;
import com.github.backend.persist.memberCourse.repository.MemberCourseRepository;
import com.github.backend.service.memberCourse.MemberCourseUnlockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberCourseUnlockServiceImpl implements MemberCourseUnlockService {

	private final MemberRepository memberRepository;
	private final CourseRepository courseRepository;
	private final MemberCourseRepository memberCourseRepository;


	@Transactional
	@Override
	public void unlockCourse(Long memberId, Long courseId) {

		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_EXISTS));
		Course course = courseRepository.findById(courseId)
			.orElseThrow(() -> new CourseException(CourseErrorCode.NOT_EXIST_COURSE));

		validateDuplicateMemberCourse(member, course);

		member.decreasePoint(course.getPrice());
		
		memberCourseRepository.save(MemberCourse.builder()
			.member(member)
			.course(course)
			.bookmark(false)
			.build());
	}

	private void validateDuplicateMemberCourse(Member member, Course course) {
		if (memberCourseRepository.existsByMemberAndCourse(member, course)) {
			throw new MemberCourseException(ALREADY_MEMBER_COURSE_EXISTS);
		}
	}


}
