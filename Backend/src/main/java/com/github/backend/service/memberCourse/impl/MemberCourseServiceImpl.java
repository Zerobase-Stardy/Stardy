package com.github.backend.service.memberCourse.impl;

import com.github.backend.exception.memberCourse.MemberCourseException;
import com.github.backend.persist.member.Member;
import com.github.backend.persist.memberCourse.MemberCourse;
import com.github.backend.persist.memberCourse.repository.MemberCourseRepository;
import com.github.backend.service.memberCourse.MemberCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.github.backend.exception.memberCourse.code.MemberCourseErrorCode.MEMBER_COURSE_NOT_EXISTS;

@RequiredArgsConstructor
@Service
public class MemberCourseServiceImpl implements MemberCourseService {

	private final MemberCourseRepository memberCourseRepository;

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
