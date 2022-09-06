package com.github.backend.service.course.impl;

import com.github.backend.dto.course.CourseInfoOutputDto;
import com.github.backend.dto.course.SearchCourse;
import com.github.backend.exception.course.CourseException;
import com.github.backend.exception.myCourse.MyCourseException;
import com.github.backend.exception.myCourse.code.MyCourseErrorCode;
import com.github.backend.persist.course.Course;
import com.github.backend.persist.course.repository.CourseRepository;
import com.github.backend.exception.course.code.CourseErrorCode;
import com.github.backend.persist.course.repository.querydsl.CourseSearchRepository;
import com.github.backend.persist.myCourse.MyCourse;
import com.github.backend.persist.myCourse.repository.MyCourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseSearchRepository courseSearchRepository;
    private final MyCourseRepository myCourseRepository;

    @Transactional(readOnly = true)
    public Page<CourseInfoOutputDto.Info> searchCourseList(SearchCourse searchCourse,
        Pageable pageable) {

        return courseSearchRepository.searchByWhere(searchCourse.toCondition(), pageable)
            .map(CourseInfoOutputDto.Info::of);
    }

    @Transactional(readOnly = true)
    public CourseInfoOutputDto.Info searchMyCourse(Long memberId, Long courseId) {
        MyCourse myCourse = myCourseRepository.findByMember_IdAndCourse_Id(memberId, courseId)
            .orElseThrow(() -> new MyCourseException(MyCourseErrorCode.MY_COURSE_NOT_EXISTS));

        return CourseInfoOutputDto.Info.of(myCourse.getCourse());
    }
}
