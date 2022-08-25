package com.github.backend.service.myCourse.impl;

import static com.github.backend.exception.myCourse.code.MemberCourseErrorCode.MEMBER_COURSE_NOT_EXISTS;

import com.github.backend.exception.myCourse.MemberCourseException;
import com.github.backend.persist.member.Member;
import com.github.backend.persist.myCourse.MyCourse;
import com.github.backend.persist.myCourse.repository.MemberCourseRepository;
import com.github.backend.service.myCourse.MyCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MyCourseServiceImpl implements MyCourseService {

	private final MemberCourseRepository memberCourseRepository;

	@Transactional
	@Override
	public void toggleBookmark(String email, Long memberCourseId) {
		MyCourse myCourse = memberCourseRepository.findWithMemberById(memberCourseId)
			.orElseThrow(() -> new MemberCourseException(MEMBER_COURSE_NOT_EXISTS));

		Member member = myCourse.getMember();

		if (!member.getEmail().equals(email)) {
			throw new MemberCourseException(MEMBER_COURSE_NOT_EXISTS);
		}

		myCourse.toggleBookmark();
	}

}
