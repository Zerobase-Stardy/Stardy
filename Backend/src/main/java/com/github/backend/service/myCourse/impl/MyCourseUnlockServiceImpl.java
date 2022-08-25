package com.github.backend.service.myCourse.impl;


import static com.github.backend.exception.myCourse.code.MyCourseErrorCode.ALREADY_MY_COURSE_EXISTS;

import com.github.backend.exception.course.CourseException;
import com.github.backend.exception.course.code.CourseErrorCode;
import com.github.backend.exception.member.MemberException;
import com.github.backend.exception.member.code.MemberErrorCode;
import com.github.backend.exception.myCourse.MyCourseException;

import com.github.backend.persist.course.Course;
import com.github.backend.persist.course.repository.CourseRepository;
import com.github.backend.persist.member.Member;
import com.github.backend.persist.member.repository.MemberRepository;
import com.github.backend.persist.myCourse.MyCourse;
import com.github.backend.persist.myCourse.repository.MyCourseRepository;
import com.github.backend.service.myCourse.MyCourseUnlockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MyCourseUnlockServiceImpl implements MyCourseUnlockService {

	private final MemberRepository memberRepository;
	private final CourseRepository courseRepository;
	private final MyCourseRepository myCourseRepository;

	@Transactional
	@Override
	public void unlockCourse(Long memberId, Long courseId) {

		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_EXISTS));
		Course course = courseRepository.findById(courseId)
			.orElseThrow(() -> new CourseException(CourseErrorCode.NOT_EXIST_COURSE));

		validateDuplicateMemberCourse(member, course);

		member.decreasePoint(course.getPrice());
		
		myCourseRepository.save(MyCourse.builder()
			.member(member)
			.course(course)
			.bookmark(false)
			.build());
	}

	private void validateDuplicateMemberCourse(Member member, Course course) {
		if (myCourseRepository.existsByMemberAndCourse(member, course)) {
			throw new MyCourseException(ALREADY_MY_COURSE_EXISTS);
		}
	}

}
