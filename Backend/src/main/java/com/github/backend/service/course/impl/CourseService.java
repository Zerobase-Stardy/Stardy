package com.github.backend.service.course.impl;

import com.github.backend.exception.course.CourseException;
import com.github.backend.persist.course.Course;
import com.github.backend.persist.course.repository.CourseRepository;
import com.github.backend.exception.course.code.CourseErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    @Transactional
    public Course getCourseInfo(Long courseId){

        return courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseException(CourseErrorCode.NOT_EXIST_COURSE));
    }

}
