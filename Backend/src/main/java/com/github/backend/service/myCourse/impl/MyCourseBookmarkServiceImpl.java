package com.github.backend.service.myCourse.impl;

import com.github.backend.exception.myCourse.MyCourseException;
import com.github.backend.exception.myCourse.code.MyCourseErrorCode;
import com.github.backend.persist.myCourse.MyCourse;
import com.github.backend.persist.myCourse.repository.MyCourseRepository;
import com.github.backend.service.myCourse.MyCourseBookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MyCourseBookmarkServiceImpl implements MyCourseBookmarkService {

	private final MyCourseRepository myCourseRepository;

	@Transactional
	@Override
	public boolean toggleBookmark(Long memberId, Long courseId) {
		MyCourse myCourse = myCourseRepository.findByMember_IdAndCourse_Id(memberId, courseId)
			.orElseThrow(() -> new MyCourseException(MyCourseErrorCode.MY_COURSE_NOT_EXISTS));

		return myCourse.toggleBookmark();
	}

}
