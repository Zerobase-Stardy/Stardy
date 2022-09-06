package com.github.backend.service.myCourse;

import com.github.backend.dto.myCourse.MyCourseSearchDto;
import com.github.backend.dto.myCourse.MyCourseSearchDto.Info;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MyCourseInfoSearchService {

	Page<Info> searchMyCourses(Pageable pageable,MyCourseSearchDto.Request request);
}
