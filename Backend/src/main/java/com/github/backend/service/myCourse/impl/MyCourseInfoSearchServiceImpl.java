package com.github.backend.service.myCourse.impl;

import com.github.backend.dto.myCourse.MyCourseSearchDto.Info;
import com.github.backend.dto.myCourse.MyCourseSearchDto.Request;
import com.github.backend.persist.myCourse.repository.querydsl.MyCourseSearchRepository;
import com.github.backend.service.myCourse.MyCourseInfoSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MyCourseInfoSearchServiceImpl implements MyCourseInfoSearchService {

	private final MyCourseSearchRepository myCourseSearchRepository;

	@Transactional(readOnly = true)
	@Override
	public Page<Info> searchMyCourses(Pageable pageable,Request request) {
		return myCourseSearchRepository.search(pageable, request.toCond());
	}


}
