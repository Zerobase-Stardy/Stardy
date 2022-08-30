package com.github.backend.service.course.impl;

import com.github.backend.dto.course.CourseInfoOutputDto;
import com.github.backend.dto.course.SearchCourse;
import com.github.backend.exception.course.CourseException;
import com.github.backend.persist.course.Course;
import com.github.backend.persist.course.repository.CourseRepository;
import com.github.backend.exception.course.code.CourseErrorCode;
import com.github.backend.persist.course.repository.querydsl.CourseSearchRepository;
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


    @Transactional
    public Page<CourseInfoOutputDto.Info> searchCourseList(SearchCourse searchCourse, Pageable pageable){

        return courseSearchRepository.searchByWhere(searchCourse.toCondition(), pageable)
                .map(CourseInfoOutputDto.Info::of);
    }

}
